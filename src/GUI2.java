import javax.swing.*;
import javax.swing.event.MouseInputListener;
//import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GUI2 implements ActionListener, Runnable, ComponentListener {
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;

    public GUI2(Maze maze) throws HeadlessException {
        currentMaze = maze;
    }

    Maze currentMaze;

    private void createGUI() {

        //Setting up Frame
        JFrame Main = new JFrame();
        Main.setExtendedState(Frame.MAXIMIZED_BOTH);
        Main.setSize(WIDTH, HEIGHT);
        Main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Main.setVisible(true);
        Main.setLayout(new BorderLayout());


        //Setting up Menu Bar
        JMenuBar mnuBar = new JMenuBar();
        Main.add(mnuBar, BorderLayout.NORTH);
        mnuBar.setPreferredSize(new Dimension(1, 20));


        //Setting up central Panel for Maze
        pnlMaze = createPanel(Color.WHITE);
        Main.add(pnlMaze, BorderLayout.CENTER);
        pnlMaze.addComponentListener(this);
        pnlMaze.setLayout(new BorderLayout());
        pnlMaze.setPreferredSize(new Dimension(1, 1));

        DrawGrid MazeGrid = new DrawGrid(pnlMaze, 1, currentMaze);

        //Setting up side panels for Buttons
        pnlLeft = createPanel(Color.LIGHT_GRAY);
        Main.add(pnlLeft, BorderLayout.WEST);
        pnlLeft.setPreferredSize(new Dimension(200, 1));

        //Toggle Slider for editing


        //Button for grid disappear
        pnlButton = new JButton("Make Grid Disappear");
        pnlLeft.add(pnlButton);
        pnlButton.addActionListener(e -> MazeGrid.clearGrid(MazeGrid.getGraphics()));


    }

    private JButton pnlButton;
    private JPanel pnlMaze;
    private JPanel pnlLeft;


    private JPanel createPanel(Color c) {
        JPanel A = new JPanel();
        A.setBackground(c);
        return A;
    }

    @Override
    public void run() {
        createGUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src.getClass() == JButton.class) {
            System.out.println("YES");
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
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



