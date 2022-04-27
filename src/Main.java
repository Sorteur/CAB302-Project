import DataClasses.Maze;
import UserInterface.GUI2;
import javax.swing.*;

public class Main {
    public static void main (String[] args){

        Maze currentMaze = new Maze(6 ,4);
        SwingUtilities.invokeLater(new GUI2(currentMaze));

    }
}
