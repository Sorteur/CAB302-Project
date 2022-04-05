import javax.swing.*;
import java.awt.*;

public class GUI {




    public static void RunGUI(Maze maze){

        DrawGrid m = new DrawGrid(1,maze.Length,maze.Height);
        JFrame Frame = new JFrame();







        Frame.add(m);
        Frame.setSize(600,600);
        Frame.setVisible(true);
        Frame.setLayout(null);
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }

    //public static void



}
