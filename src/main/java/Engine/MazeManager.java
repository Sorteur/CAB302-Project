package Engine;
import DataClasses.*;
import DataModules.DBConnection;
import DataModules.MazeModule;
import UserInterface.ImageGUI;
import UserInterface.LogoPlacer;
import UserInterface.MazePanel;
import UserInterface.SrtEndPlacer;

import java.awt.*;
import java.sql.SQLException;

/**
 * The class that stores the current maze and has mathods that affects the current maze.
 * @see Maze
 */
public class MazeManager {
    private static MazeManager instance = new MazeManager();
    private boolean once = false;

    public static MazeManager Instance(){
        return instance;
    }
    private MazeManager (){

    }
    private Maze Maze;


    /**
     * Gets current maze
     * @return Maze - Current maze
     */
    public Maze GetMaze() {
        return Maze;
    }
    /**
     * Given two integers representing the length and height of the maze in Cells,
     * will create a maze with the length and height as given and populate with Cells.
     *
     * @param Length Length of the desired maze.
     * @param Height Height of the desired maze.
     *
     * @return Maze - Maze that was created
     * @see Cell
     */
    public Maze CreateMaze (int Length, int Height) {
        Maze = new Maze(Length, Height);
        return Maze;
    }

    /**
     * Gets the maze with specified id and sets it as the current maze.
     * @param id of maze to load.
     */
    public void LoadMazeFromId(int id)
    {
        MazeModule mazeModule = new MazeModule(DBConnection.Instance());
        try
        {
            Maze = mazeModule.GetMazeFromID(id);
        }
        catch (SQLException sqlException)
        {
            System.out.println(sqlException);
        }

    }

    /**
     * Gets all Maze descriptions from database for display on Load-GUI.
     * @return Two-dimensional array of maze information to be displayed.
     */
    public String[][] LoadMazeDescriptions() {
        MazeModule mazeModule = new MazeModule(DBConnection.Instance());
        MazeDescriptions descriptions;
        try
        {
            descriptions = mazeModule.GetMazeDescriptions();
            String[][] output = descriptions.ToStringArray();
            return output;
        }
        catch (SQLException sqlException){
            System.out.println(sqlException);
        }
        return null;
    }

    /**
     * Saves the current maze in the database.
     */
    public void SaveMaze () {
        MazeModule mazeModule = new MazeModule(DBConnection.Instance());
        try
        {
            mazeModule.SaveMaze(Maze);
        }
        catch (SQLException sqlException)
        {
            System.out.println(sqlException);
        }
    }

    /**
     * Creates and places a logo at specified co-ordinates
     * @param Logo Image to be placed
     * @param pnlMaze Panel to place image on
     * @param X X position on grid to place image at
     * @param Y Y position on grid to place image at
     * @param WidthBoxInt The width to set the image to
     * @param HeightBoxInt The height to set the image to
     */
    public void CreateLogo(Image Logo, MazePanel pnlMaze, int X, int Y, int WidthBoxInt, int HeightBoxInt) {
        int Width = pnlMaze.sizeScale* WidthBoxInt;
        int Height = pnlMaze.sizeScale* HeightBoxInt;
        Image ScaledImage = Logo.getScaledInstance(Width-2,Height-2,Image.SCALE_SMOOTH);
        Maze.SetLogo(new MazeImageResource(ScaledImage,X,Y));
        //Make sure Logo is un-reachable
        for (Cell cell:Maze.getGrid()) {
            if (cell.GetGridX() >= X & cell.GetGridX() < X + WidthBoxInt){
                if (cell.GetGridY() >= Y & cell.GetGridY() < Y + HeightBoxInt){
                    cell.SetNorthernwall(WallType.Wall);
                    cell.SetSouthernwall(WallType.Wall);
                    cell.SetEasternwall(WallType.Wall);
                    cell.SetWesternwall(WallType.Wall);
                }
            }
        }
        LogoDraw(pnlMaze);
    }

    /**
     * Place new SrtEndPlacer on specified JPanel
     * @param pnlMaze Panel to have SrtEndPlacer on
     */
    public void StartEndDraw(MazePanel pnlMaze){
        pnlMaze.add(new SrtEndPlacer());
        pnlMaze.repaint();
        pnlMaze.updateUI();
    }

    /**
     * Place new LogoPlacer on specified JPanel
     * @param pnlMaze Panel to have LogoPlacer on
     */
    public void LogoDraw(MazePanel pnlMaze){
        pnlMaze.add(new LogoPlacer());
        pnlMaze.repaint();
        pnlMaze.updateUI();
    }

    /**
     * Creates and places start and end images at specified co-ordinates
     * @param pnlMaze Panel to place images on
     * @param X X-Position for end Image
     * @param Y Y-Position for end Image
     * @param Width Width of both images
     * @param Height Height of both images
     * @param EndImage End image to be placed
     * @param StartImage Start image to be placed
     */
    public void StartEndCreator(MazePanel pnlMaze, int X, int Y, int Width, int Height, Image EndImage, Image StartImage) {
        Maze.SetExitImage(new MazeImageResource(EndImage.getScaledInstance(Width, Height,Image.SCALE_SMOOTH), Maze.getLength()- X, Maze.getHeight()- Y));
        Maze.SetEntryImage(new MazeImageResource(StartImage.getScaledInstance(Width, Height,Image.SCALE_SMOOTH), 0, 0));
        StartEndDraw(pnlMaze);
    }

    /**
     * Creates a maze then places a logo at random co-ordinates
     * @param pnlMaze Panel to place logo onto
     * @param MazeLength Length of maze to be created
     * @param MazeHeight Height of maze to be created
     * @param Random Whether a random maze will be generated or not
     * @param ImageStartEnd Whether the maze will have start/end images
     * @param BoxX Width box integer
     * @param BoxY Height box integer
     * @param Logo Logo to be randomly placed
     */
    public void AutoLogoCreator(MazePanel pnlMaze,int MazeLength,int MazeHeight, boolean Random, boolean ImageStartEnd, int BoxX, int BoxY, Image Logo) {
        CreateMaze(MazeLength,MazeHeight);
        pnlMaze.GridSet();
        int Width = (pnlMaze.sizeScale* BoxX)-2;
        int Height = (pnlMaze.sizeScale* BoxY)-2;
        Cell StartCell = MazeGenerator.Instance().RandomCellLogo(Maze, BoxX, BoxY);
        for (Cell cell:Maze.getGrid()) {
            if (cell.GetGridX() >= StartCell.GetGridX() & cell.GetGridX() < StartCell.GetGridX()+ BoxX){
                if (cell.GetGridY() >= StartCell.GetGridY() & cell.GetGridY() < StartCell.GetGridY()+ BoxY){
                    cell.IsVisiting();
                }
            }
        }
        if (Random) {MazeGenerator.Instance().GenerateMaze(Maze);}
        if (ImageStartEnd){
            Maze.setImgSrtEnd(true);
            StartEndDraw(pnlMaze);
            ImageGUI.Instance().ImgSrtEnd(pnlMaze);
        }
        Maze.SetLogo(new MazeImageResource(Logo.getScaledInstance(Width,Height,Image.SCALE_SMOOTH), StartCell.GetGridX(), StartCell.GetGridY()));
        LogoDraw(pnlMaze);
        pnlMaze.GridSet();
        pnlMaze.updateUI();
    }
}
