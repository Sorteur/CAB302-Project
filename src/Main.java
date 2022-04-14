import javax.swing.*;

public class Main {
    public static void main (String[] args){

        Maze currentMaze = new Maze(3,3);
        SwingUtilities.invokeLater(new GUI2(currentMaze));


        //currentMaze.GridInfo();
    }
}
