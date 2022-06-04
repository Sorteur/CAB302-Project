import DataClasses.Maze;
import DataModules.DBConnection;
import DataModules.MazeModule;
import UserInterface.GUI2;
import javax.swing.*;
import java.sql.SQLException;

public class Main {
    public static void main (String[] args){

        MazeModule test = new MazeModule(DBConnection.Instance());
        try {test.SaveMaze(new Maze(10,10));}
        catch (SQLException sqle)
        {System.out.println(sqle);}

        SwingUtilities.invokeLater(new GUI2());
    }
}
