import javax.swing.*;

public class Main {
    public static void main (String[] args){

        Maze testmaze = new Maze(100,70);
        SwingUtilities.invokeLater(new GUI2(testmaze));

        //testmaze.GridInfo();
        //testmaze.Search(1,16);
    }
}
