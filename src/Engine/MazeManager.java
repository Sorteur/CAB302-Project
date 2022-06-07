package Engine;
import DataClasses.Cell;
import DataClasses.Maze;
import DataClasses.WallType;
import DataModules.DBConnection;
import DataModules.MazeModule;
import UserInterface.ImageGUI;
import UserInterface.LogoPlacer;
import UserInterface.MazePanel;
import UserInterface.SrtEndPlacer;

import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;

public class MazeManager {
    private static MazeManager instance = new MazeManager();
    public static MazeManager Instance(){
        return instance;
    }
    private MazeManager (){
    }

    private Maze Maze;
    public MazePanel pnlMaze = new MazePanel();

    public Maze GetMaze() {
        return Maze;
    }

    public Maze CreateMaze (int Length, int Height) {
        Maze = new Maze(Length, Height);
        return Maze;
    }

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

    public String[][] LoadMazeDescriptions() {

        MazeModule mazeModule = new MazeModule(DBConnection.Instance());

        Hashtable descriptions;
        try
        {
            int index = 0;
            descriptions = mazeModule.GetMazeDescriptions();

            Set<Integer> id = descriptions.keySet();
            String[] IdArray = new String[id.size()];
            for(Integer Id : id)
            {
                IdArray[index++] = Id.toString();
            }

            index = 0;
            Enumeration<String> description = descriptions.elements();
            String[] descriptionArray = new String[id.size()];
            while(description.hasMoreElements()){
                descriptionArray[index++] = description.nextElement();
            }


            String[][] output = new String[id.size()][2];

            for(int i = 0; i < id.size(); i++)
            {
                output[i][0] = IdArray[i].toString();
            }

            for(int i = 0; i < id.size(); i++)
            {
                output[i][1] = descriptionArray[i];
            }

            return output;

        }
        catch (SQLException sqlException){
            System.out.println(sqlException);
        }

        return null;

    }

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

    public void CreateLogo(Image Logo, MazePanel pnlMaze, int X, int Y, int WidthBoxInt, int HeightBoxInt,int i) {
        int Width = pnlMaze.sizeScale* WidthBoxInt;
        int Height = pnlMaze.sizeScale* HeightBoxInt;

        Image ScaledImage = Logo.getScaledInstance(Width-2,Height-2,Image.SCALE_SMOOTH);
        Maze.ConstructLogo(ScaledImage, X, Y);
        Maze.getLogo().SetGridScale(Width,Height);
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
        MazeManager.Instance().GetMaze().setLogoImage(ScaledImage);
        if (i == 0){
            LogoPlacer A = new LogoPlacer();
            pnlMaze.add(A,BorderLayout.CENTER);
        } else {
            pnlMaze.repaint();
        }
        pnlMaze.updateUI();
    }

    public void StartEndCreator(MazePanel pnlMaze, int X, int Y, int Width, int Height, Image EndImage, Image StartImage,int j) {
        Maze.ConstructExitImage(EndImage.getScaledInstance(Width, Height,Image.SCALE_SMOOTH), Maze.getLength()- X, Maze.getHeight()- Y);
        Maze.getExitImage().SetGridScale(X, Y);
        Maze.ConstructEntryImage(StartImage.getScaledInstance(Width, Height,Image.SCALE_SMOOTH), 0, 0);
        Maze.getEntryImage().SetGridScale(X, Y);
        //Used to make sure only one SrtEndPlacer is made, update instead if it exists
        if (j == 0){
            pnlMaze.add(new SrtEndPlacer());
        } else {
            pnlMaze.repaint();
        }
        pnlMaze.updateUI();
    }

    public void AutoLogoCreator(MazePanel pnlMaze, int MazeLength, int MazeHeight, boolean Random, boolean ImageStartEnd, int BoxX, int BoxY, Image Logo) {

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
            ImageGUI.Instance().ImgSrtEnd(pnlMaze);
        }

        Maze.ConstructLogo(Logo.getScaledInstance(Width,Height,Image.SCALE_SMOOTH), StartCell.GetGridX(), StartCell.GetGridY());
        pnlMaze.add(new LogoPlacer());
        pnlMaze.GridSet();
        pnlMaze.updateUI();
    }


}
