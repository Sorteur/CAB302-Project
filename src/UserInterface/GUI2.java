package UserInterface;

import Engine.MazeGenerator;
import Engine.MazeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI2 implements ActionListener, Runnable, ComponentListener {
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;

    public GUI2() throws HeadlessException {
    }

    private JPanel pnlMaze;
    private DrawGrid grid;

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


        //Setting up and Displaying Panel to display Grid
        pnlMaze = createPanel(Color.WHITE);
        Main.add(pnlMaze, BorderLayout.CENTER);
        pnlMaze.addComponentListener(this);
        pnlMaze.setLayout(new BorderLayout());
        pnlMaze.setPreferredSize(new Dimension(1, 1));
        grid = new DrawGrid(pnlMaze);
        pnlMaze.add(grid, BorderLayout.CENTER);


        //Setting up side panels for Buttons
        JPanel pnlLeft = createPanel(Color.LIGHT_GRAY);
        Main.add(pnlLeft, BorderLayout.WEST);
        pnlLeft.setPreferredSize(new Dimension(200, 1));

        Dimension lftBtnSize = new Dimension(150,25);

        JButton pnlGenerateGridButton = new JButton("New Maze");
        pnlGenerateGridButton.setPreferredSize(lftBtnSize);
        pnlGenerateGridButton.addActionListener(e-> newMaze());
        pnlLeft.add(pnlGenerateGridButton);

        JButton pnlSaveGridButton = new JButton("Save Maze");
        pnlSaveGridButton.setPreferredSize(lftBtnSize);
        pnlLeft.add(pnlSaveGridButton);

        JButton pnlLoadGridButton = new JButton("Load Maze");
        pnlLoadGridButton.setPreferredSize(lftBtnSize);
        pnlLeft.add(pnlLoadGridButton);

        JButton pnlPrintGridButton = new JButton("Print");
        pnlPrintGridButton.setPreferredSize(lftBtnSize);
        pnlLeft.add(pnlPrintGridButton);

        JButton pnlResizeGridButton = new JButton("Resize Maze");
        pnlResizeGridButton.setPreferredSize(lftBtnSize);
        pnlLeft.add(pnlResizeGridButton);

        JButton pnlPlaceStartCellButton = new JButton("Place Starting Point");
        pnlPlaceStartCellButton.setPreferredSize(lftBtnSize);
        pnlLeft.add(pnlPlaceStartCellButton);

        JButton pnlPlaceEndCellButton = new JButton("Place End Point");
        pnlPlaceEndCellButton.setPreferredSize(lftBtnSize);
        pnlLeft.add(pnlPlaceEndCellButton);

        JButton pnlSolveMazeButton = new JButton("Solve");
        pnlSolveMazeButton.setPreferredSize(lftBtnSize);
        pnlLeft.add(pnlSolveMazeButton);


    }

    private void newMaze(){
        Font Large  = new Font("Larger",Font.PLAIN, 24 );

        JFrame PopOut = new JFrame();
        PopOut.setSize(400, 300);
        PopOut.setVisible(true);
        PopOut.setLayout(new BorderLayout());

        JTextField WidthBox = new JTextField();
        JLabel WidthLabel = new JLabel("Insert width of new Maze:");
        WidthBox.setFont(Large);
        WidthLabel.setFont(Large);
        WidthBox.setBounds(305,30,60,30);
        WidthLabel.setBounds(20,25, 400,30);

        JTextField HeightBox = new JTextField();
        JLabel HeightLabel = new JLabel("Insert height of new Maze:");
        HeightBox.setFont(Large);
        HeightLabel.setFont(Large);
        HeightBox.setBounds(305,80,60,30);
        HeightLabel.setBounds(20,75, 400,30);
        JButton Confirm = new JButton("Confirm");
        Confirm.setFont(Large);
        Confirm.setBounds(125,175,150,50);
        Confirm.addActionListener(e -> {
                int Width = Integer.parseInt(WidthBox.getText());
                int Height = Integer.parseInt(HeightBox.getText());
                grid.GridSet(MazeGenerator.Instance().GenerateMaze(MazeManager.Instance().CreateMaze(Width,Height)));
                pnlMaze.updateUI();
                PopOut.dispose();
        });

        PopOut.add(WidthBox);
        PopOut.add(HeightBox);
        PopOut.add(WidthLabel);
        PopOut.add(HeightLabel);
        PopOut.add(Confirm);
        PopOut.setLayout(null);
    }

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