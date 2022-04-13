import javax.swing.*;

public class Main {
    public static void main (String[] args){

        Maze currentMaze = new Maze(2,2);
        SwingUtilities.invokeLater(new GUI2(currentMaze));


        //currentMaze.GridInfo();
    }
}
