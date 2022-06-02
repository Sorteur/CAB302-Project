package UserInterface;

import DataClasses.Maze;
import Engine.MazeGenerator;
import Engine.MazeManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GUI2 implements ActionListener, Runnable, ComponentListener {
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;

    public GUI2() throws HeadlessException {
    }

    private DrawGrid pnlMaze;

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
        pnlMaze =  new DrawGrid();
        pnlMaze.setLayout(new BorderLayout());
        Main.add(pnlMaze);


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

        JButton pnlExportGridButton = new JButton("Export as Image");
        pnlExportGridButton.setPreferredSize(lftBtnSize);
        pnlExportGridButton.addActionListener(e -> pnlMaze.Export());
        pnlLeft.add(pnlExportGridButton);

        JButton pnlAddLogoButton = new JButton("Image Editor");
        pnlAddLogoButton.setPreferredSize(lftBtnSize);
        pnlAddLogoButton.addActionListener(e -> ImageGUI.Instance().LogoEditor(pnlMaze)
        );

        pnlLeft.add(pnlAddLogoButton);

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
                if (RandomButton.isSelected()) {
                    MazeGenerator.Instance().GenerateMaze(MazeManager.Instance().CreateMaze(Width, Height));
                } else {
                    MazeManager.Instance().CreateMaze(Width, Height);
                }
                pnlMaze.GridSet();
                PopOut.dispose();
            } catch(NumberFormatException e) {
                JOptionPane.showMessageDialog(PopOut,"Dimensions of maze must be whole numbers.","Input error",JOptionPane.ERROR_MESSAGE);
            }
        });
        PopOut.add(Confirm,c);
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