import DataClasses.Maze;
import Engine.MazeManager;
import UserInterface.GUI2;
import javax.swing.*;

public class Main {
    public static void main (String[] args){

        Maze currentMaze = new Maze(6 ,4);
        SwingUtilities.invokeLater(new GUI2(currentMaze));
        MazeManager.Instance().CreateMaze(4,5);

    }
}
