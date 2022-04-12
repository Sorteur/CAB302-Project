import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class GUI2 implements ActionListener, Runnable, ComponentListener {
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;
    public GUI2(Maze maze) throws HeadlessException {
    currentmaze = maze;
    }

    Maze currentmaze;

    private void createGUI() {
        JFrame frame = new JFrame();
        frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLayout(new BorderLayout());

        JMenuBar mnuBar = new JMenuBar();
        frame.add(mnuBar, BorderLayout.NORTH);
        mnuBar.setPreferredSize(new Dimension(1, 20));

        pnlMaze = createPanel(Color.WHITE);
        pnlMaze.addComponentListener(this);

        pnlMaze.setLayout(new BorderLayout());
        frame.add(pnlMaze, BorderLayout.CENTER);

        pnlLeft = createPanel(Color.LIGHT_GRAY);
        frame.add(pnlLeft, BorderLayout.WEST);
        pnlLeft.setPreferredSize(new Dimension(100, 1));

        //frame.pack();

        DrawGrid MazeGrid = new DrawGrid(pnlMaze,5,currentmaze.Length,currentmaze.Height);
        //System.out.println(pnlMaze.getSize());
        //System.out.println(pnlMaze.getY());

    }

    private JPanel pnlMaze;
    private JPanel pnlLeft;


    private JPanel createPanel(Color c) {
        JPanel A = new JPanel();
        A.setBackground(c);
        return A;
    }










    @Override
    public void run(){
        createGUI();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void componentResized(ComponentEvent e) {
        System.out.println(pnlMaze.getSize());
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}


