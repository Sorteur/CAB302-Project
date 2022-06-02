import DataClasses.Maze;
import DataModules.DBConnection;
import DataModules.MazeModule;
import UserInterface.GUI2;
import javax.swing.*;
import java.sql.SQLException;

public class Main {
    public static void main (String[] args){

        SwingUtilities.invokeLater(new GUI2());
    }
}
