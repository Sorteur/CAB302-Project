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

    public Maze CreateMaze (int Length, int Height) {
        Maze = new Maze(Length, Height);

        return Maze;
    }

    public void LoadMazeFromId(int id) throws SQLException
    {
        MazeModule mazeModule = new MazeModule(new DBConnection());
        try
        {
            Maze = mazeModule.GetMazeFromID(id);
        }
        catch (SQLException sqlException)
        {
            System.out.println(sqlException);
        }

    }

    public String[][] LoadMazeDescriptions() throws SQLException {

        MazeModule mazeModule = new MazeModule(new DBConnection());

        MazeDescriptions descriptions;


        descriptions = mazeModule.GetMazeDescriptions();
        String[][] output = descriptions.ToStringArray();

        return output;



    }

    public void SaveMaze () throws SQLException {
        MazeModule mazeModule = new MazeModule(new DBConnection());
        try
        {
            mazeModule.SaveMaze(Maze);
        }
        catch (SQLException sqlException)
        {
            System.out.println(sqlException);
        }
        finally {

        }
    }

    public void CreateLogo(Image Logo, MazePanel pnlMaze, int X, int Y, int WidthBoxInt, int HeightBoxInt,int i) {
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
        //Used to make sure only one LogoPlacer is made, update instead if it exists
        //MazeManager.Instance().GetMaze().setLogoImage(ScaledImage);

        LogoDraw(pnlMaze);
    }


    public void StartEndDraw(MazePanel pnlMaze){
        pnlMaze.add(new SrtEndPlacer());
        pnlMaze.repaint();
        pnlMaze.updateUI();
    }
    public void LogoDraw(MazePanel pnlMaze){
        pnlMaze.add(new LogoPlacer());
        pnlMaze.repaint();
        pnlMaze.updateUI();
    }

    public void StartEndCreator(MazePanel pnlMaze, int X, int Y, int Width, int Height, Image EndImage, Image StartImage,int j) {
        Maze.SetExitImage(new MazeImageResource(EndImage.getScaledInstance(Width, Height,Image.SCALE_SMOOTH), Maze.getLength()- X, Maze.getHeight()- Y));
        Maze.SetEntryImage(new MazeImageResource(StartImage.getScaledInstance(Width, Height,Image.SCALE_SMOOTH), 0, 0));
        StartEndDraw(pnlMaze);

        //Used to make sure only one SrtEndPlacer is made, update instead if it exists
    }

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
