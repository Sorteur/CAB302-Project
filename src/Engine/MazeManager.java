package Engine;
import DataClasses.Maze;
import DataModules.DBConnection;
import DataModules.MazeModule;
import UserInterface.MazePanel;

import java.sql.SQLException;
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

    public Maze CreateMazeAutoLogo (int Length, int Height) {


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

    public void SaveMaze (Maze maze) {

    }

    public void ExportMaze (Maze maze){

    }





}
