package UserInterface;

import DataClasses.Cell;
import DataClasses.Maze;
import DataClasses.WallType;
import Engine.MazeGenerator;
import Engine.MazeManager;
import Engine.MazeSolver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI2 implements ActionListener, Runnable, ComponentListener {
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;

    public GUI2() throws HeadlessException {
    }

    private MazePanel pnlMaze;

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
        pnlMaze = new MazePanel();
        pnlMaze.setLayout(new BorderLayout());
        Main.add(pnlMaze);


        //Setting up side panels for Buttons
        JPanel pnlLeft = new JPanel();
        pnlLeft.setBackground(Color.LIGHT_GRAY);
        Main.add(pnlLeft, BorderLayout.WEST);
        pnlLeft.setPreferredSize(new Dimension(200, 1));

        Dimension lftBtnSize = new Dimension(150,25);

        JButton pnlGenerateGridButton = new JButton("New Maze");
        pnlGenerateGridButton.setPreferredSize(lftBtnSize);
        pnlGenerateGridButton.addActionListener(e-> newMaze());
        pnlLeft.add(pnlGenerateGridButton);

        JButton pnlSaveGridButton = new JButton("Save Maze");
        pnlSaveGridButton.setPreferredSize(lftBtnSize);
        pnlSaveGridButton.addActionListener(e-> {

            if (MazeManager.Instance().GetMaze() == null) {
                JOptionPane.showMessageDialog(pnlLeft, "Must Create Or Load in A Maze to Save!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else SaveMazeDialog.Instance().GUI();
        });
        pnlLeft.add(pnlSaveGridButton);

        JButton pnlLoadGridButton = new JButton("Load Maze");
        pnlLoadGridButton.setPreferredSize(lftBtnSize);
        pnlLoadGridButton.addActionListener(e-> {
            LoadMazeDialog.Instance().GUI(pnlMaze);
            MazeManager.Instance().StartEndDraw(pnlMaze);
            MazeManager.Instance().LogoDraw(pnlMaze);
            pnlMaze.updateUI();
        });
        pnlLeft.add(pnlLoadGridButton);
        pnlMaze.add(new LogoPlacer());
        pnlMaze.repaint();
        pnlMaze.updateUI();
        pnlMaze.add(new SrtEndPlacer());
        pnlMaze.repaint();
        pnlMaze.updateUI();

        JButton pnlExportGridButton = new JButton("Export as Image");
        pnlExportGridButton.setPreferredSize(lftBtnSize);
        pnlExportGridButton.addActionListener(e -> pnlMaze.Export());
        pnlLeft.add(pnlExportGridButton);

        JButton pnlAddLogoButton = new JButton("Logo Placer");
        pnlAddLogoButton.setPreferredSize(lftBtnSize);
        pnlAddLogoButton.addActionListener(e -> ImageGUI.Instance().LogoEditor(pnlMaze));
        pnlLeft.add(pnlAddLogoButton);

        JButton pnlSolveMazeButton = new JButton("Solve");
        pnlSolveMazeButton.setPreferredSize(lftBtnSize);
        pnlSolveMazeButton.addActionListener(e -> {
            if (MazeManager.Instance().GetMaze() == null) {JOptionPane.showMessageDialog(pnlLeft, "Must Create or Load in a Maze to Solve!", "Error", JOptionPane.ERROR_MESSAGE);}
            else {
                try {


                    if (MazeManager.Instance().GetMaze().GetSolved())
                    {
                        MazeManager.Instance().GetMaze().setSolved(false);
                        pnlMaze.GridSet();
                    }
                    else
                    {
                        MazeManager.Instance().GetMaze().setSolved(false);
                        MazeSolver.Instance().SolveMaze();
                        solvePopout();
                        pnlMaze.GridSet();
                    }

                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(pnlLeft, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        pnlLeft.add(pnlSolveMazeButton);

    }

    private void solvePopout() {
        int DeadEnd = 0;
        int SolutionCells = 1;
        for (Cell cells : MazeManager.Instance().GetMaze().getGrid()){
            if (cells.Searched){
                SolutionCells++;
            }
            if (cells.IsNorthernwall() == WallType.Empty){
                if (cells.IsSouthernwall() == WallType.Wall && cells.IsEasternwall() == WallType.Wall && cells.IsWesternwall() == WallType.Wall){
                    DeadEnd++;
                }
            } else if (cells.IsSouthernwall() == WallType.Empty){
                if (cells.IsNorthernwall() == WallType.Wall && cells.IsEasternwall() == WallType.Wall && cells.IsWesternwall() == WallType.Wall){
                    DeadEnd++;
                }
            } else if (cells.IsEasternwall() == WallType.Empty){
                if (cells.IsNorthernwall() == WallType.Wall && cells.IsSouthernwall() == WallType.Wall && cells.IsWesternwall() == WallType.Wall){
                    DeadEnd++;
                }
            } else {
                if (cells.IsNorthernwall() == WallType.Wall && cells.IsEasternwall() == WallType.Wall && cells.IsEasternwall() == WallType.Wall){
                    DeadEnd++;
                }
            }
        }
        int PercentDeadEnd = (DeadEnd*100)/ MazeManager.Instance().GetMaze().getGrid().size();
        int PercentCellsSolution = (SolutionCells*100)/ MazeManager.Instance().GetMaze().getGrid().size();
        Font Large  = new Font("Larger",Font.PLAIN, 24 );

        JFrame PopOut = new JFrame();
        PopOut.setSize(600, 200);
        PopOut.setVisible(true);
        PopOut.setLocationRelativeTo(null);
        PopOut.setLayout(new GridBagLayout());

        GridBagConstraints c  =  new GridBagConstraints();
        c.insets = new Insets(5,5,0,5);
        c.anchor = GridBagConstraints.LINE_START;

        JLabel PercentDeadEndLabel = new JLabel("Percentage of cells that are dead ends:");
        PercentDeadEndLabel.setFont(Large);
        c.gridx = 0;
        c.gridy = 0;
        PopOut.add(PercentDeadEndLabel,c);

        JLabel DeadendNumberLabel = new JLabel(String.valueOf(PercentDeadEnd)+"%");
        DeadendNumberLabel.setFont(Large);
        c.gridx = 1;
        c.gridy = 0;
        PopOut.add(DeadendNumberLabel,c);

        JLabel PercentCellsSolutionLabel = new JLabel("Percentage of cells that are apart of the solution:");
        PercentCellsSolutionLabel.setFont(Large);
        c.gridx = 0;
        c.gridy = 1;
        PopOut.add(PercentCellsSolutionLabel,c);

        JLabel CellsSolutionNumberLabel = new JLabel(String.valueOf(PercentCellsSolution)+"%");
        CellsSolutionNumberLabel.setFont(Large);
        c.gridx = 1;
        c.gridy = 1;
        PopOut.add(CellsSolutionNumberLabel,c);
    }


    private void newMaze(){
        Font Large  = new Font("Larger",Font.PLAIN, 24 );

        JFrame PopOut = new JFrame();
        PopOut.setSize(350, 200);
        PopOut.setVisible(true);
        PopOut.setLocationRelativeTo(null);
        PopOut.setLayout(new GridBagLayout());

        GridBagConstraints c  =  new GridBagConstraints();
        c.weighty = 0.1;
        c.insets = new Insets(5,5,0,5);
        c.anchor = GridBagConstraints.LINE_START;

        JLabel WidthLabel = new JLabel("Width of new maze:");
        WidthLabel.setFont(Large);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        PopOut.add(WidthLabel,c);

        JTextField WidthBox = new JTextField();
        WidthBox.setFont(Large);
        WidthBox.setPreferredSize(new Dimension(60,30));
        c.gridx = 2;
        c.gridy = 0;
        c.gridwidth = 1;
        c.insets = new Insets(0,5,0,5);
        PopOut.add(WidthBox,c);

        JLabel HeightLabel = new JLabel("Height of new maze:");
        HeightLabel.setFont(Large);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        PopOut.add(HeightLabel,c);

        JTextField HeightBox = new JTextField();
        HeightBox.setFont(Large);
        HeightBox.setPreferredSize(new Dimension(60,30));
        c.gridx = 2;
        c.gridy = 1;
        c.gridwidth = 1;
        c.weighty = 0;
        PopOut.add(HeightBox,c);

        JRadioButton RandomButton = new JRadioButton("Random maze start");
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.insets = new Insets(0,0,5,5);
        PopOut.add(RandomButton,c);

        JRadioButton ImageStartEnd = new JRadioButton("Image start/end points");
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        PopOut.add(ImageStartEnd,c);

        JRadioButton BuildAroundLogo = new JRadioButton("Auto logo placement");
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        PopOut.add(BuildAroundLogo,c);

        JButton Confirm = new JButton("Confirm");
        Confirm.setFont(Large);
        c.anchor = GridBagConstraints.LINE_END;
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 1;
        c.gridwidth = 2;
        c.gridheight = 3;
        c.gridy = 2;
        Confirm.addActionListener(a -> {
            try {
                int Width = Integer.parseInt(WidthBox.getText());
                int Height = Integer.parseInt(HeightBox.getText());

                //TODO REFACTOR
                //MazeManager.Instance().CreateMaze(Width,Height);
                if (RandomButton.isSelected()) {
                    if (BuildAroundLogo.isSelected()){
                        ImageGUI.Instance().AutoLogo(pnlMaze,Width,Height,true,ImageStartEnd.isSelected());
                    } else {
                        MazeManager.Instance().CreateMaze(Width,Height);
                        MazeGenerator.Instance().GenerateMaze(MazeManager.Instance().GetMaze());
                        if (ImageStartEnd.isSelected()){
                            MazeManager.Instance().GetMaze().setImgSrtEnd(true);
                            ImageGUI.Instance().ImgSrtEnd(pnlMaze);
                        }
                        pnlMaze.GridSet();
                    }
                }
                else {
                    if (BuildAroundLogo.isSelected()){
                        ImageGUI.Instance().AutoLogo(pnlMaze,Width,Height,false, ImageStartEnd.isSelected());
                    }else {
                        MazeManager.Instance().CreateMaze(Width, Height);

                        if (ImageStartEnd.isSelected()){
                            MazeManager.Instance().GetMaze().setImgSrtEnd(true);
                            ImageGUI.Instance().ImgSrtEnd(pnlMaze);
                        }
                        pnlMaze.GridSet();
                    }
                }
                PopOut.dispose();
            } catch(NumberFormatException e) {
                JOptionPane.showMessageDialog(PopOut,"Dimensions of maze must be whole numbers.","Input error",JOptionPane.ERROR_MESSAGE);
            }
        });
        PopOut.add(Confirm,c);
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