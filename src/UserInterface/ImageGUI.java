package UserInterface;
import DataClasses.Cell;
import DataClasses.Maze;
import DataClasses.WallType;
import Engine.MazeManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImageGUI {

    private static final ImageGUI instance = new ImageGUI();
    private Image Logo;
    private Image StartImage;
    private Image EndImage;

    //used to determine if to create Logo/StartEnd or update them.
    private int i;
    private int j;

    public static ImageGUI Instance(){
        return instance;
    }
    ImageGUI() {
    }

    public Image ImageSelector() {
        JFileChooser K = new JFileChooser();
        K.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "png"));
        K.setAcceptAllFileFilterUsed(false);
        File file = null;
        if (K.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
             file = K.getSelectedFile();
        }
        //return file;

        Image image = null;
        try {
            image =  ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }


    public void LogoEditor(MazePanel pnlMaze){
        //Image Logo;

        Font Large  = new Font("Larger",Font.PLAIN, 24 );

        JFrame logoMenu = new JFrame();
        logoMenu.setSize(550, 300);
        logoMenu.setVisible(true);
        logoMenu.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weighty = 0.1;
        c.insets = new Insets(5,5,0,5);
        c.anchor = GridBagConstraints.LINE_START;
        c.weightx = 0.1;

        JLabel Pos = new JLabel("Cell Position for Logo");
        c.gridx = 0;
        c.gridy = 0;
        logoMenu.add(Pos,c);

        JLabel XPosLabel = new JLabel("X Pos for Logo:");
        XPosLabel.setFont(Large);
        c.gridx = 0;
        c.gridy = 1;
        logoMenu.add(XPosLabel,c);

        JTextField XPosBox = new JTextField();
        XPosBox.setFont(Large);
        c.gridx = 1;
        c.gridy = 1;
        logoMenu.add(XPosBox,c);
        XPosBox.setPreferredSize(new Dimension(60,30));

        JLabel YPosLabel = new JLabel("Y Pos for Logo:");
        YPosLabel.setFont(Large);
        c.gridx = 0;
        c.gridy = 2;
        logoMenu.add(YPosLabel,c);

        JTextField YPosBox = new JTextField();
        YPosBox.setFont(Large);
        c.gridx = 1;
        c.gridy = 2;
        logoMenu.add(YPosBox,c);
        YPosBox.setPreferredSize(new Dimension(60,30));

        JLabel Size = new JLabel("Size of Logo:");
        c.gridx = 2;
        c.gridy = 0;
        logoMenu.add(Size,c);

        JLabel WidthLabel = new JLabel("Width of Logo:");
        WidthLabel.setFont(Large);
        c.gridx = 2;
        c.gridy = 1;
        logoMenu.add(WidthLabel,c);

        JTextField WidthBox = new JTextField();
        WidthBox.setFont(Large);
        c.gridx = 3;
        c.gridy = 1;
        logoMenu.add(WidthBox,c);
        WidthBox.setPreferredSize(new Dimension(60,30));

        JLabel HeightLabel = new JLabel("Height of Logo:");
        HeightLabel.setFont(Large);
        c.gridx = 2;
        c.gridy = 2;
        logoMenu.add(HeightLabel,c);

        JTextField HeightBox = new JTextField();
        HeightBox.setFont(Large);
        c.gridx = 3;
        c.gridy = 2;
        logoMenu.add(HeightBox,c);
        HeightBox.setPreferredSize(new Dimension(60,30));

        JLabel Preview = new JLabel();
        c.gridx = 0;
        c.gridwidth = 2;
        c.gridy = 3;
        c.anchor = GridBagConstraints.CENTER;
        logoMenu.add(Preview,c);

        JButton ImagePicker = new JButton("Open an Image");
        ImagePicker.addActionListener(e -> {
            Logo = ImageSelector();
            Preview.setIcon(new ImageIcon(Logo.getScaledInstance(100,100,Image.SCALE_SMOOTH)));
        });
        c.gridx = 0;
        c.gridwidth = 2;
        c.gridy = 4;
        logoMenu.add(ImagePicker,c);

        JButton placeImage = new JButton("Place Image");
        placeImage.setFont(Large);
        c.gridx = 2;
        c.gridwidth = 2;
        c.gridy = 3;
        c.gridheight = 2;
        c.weighty = 1;
        placeImage.addActionListener(a ->{
            try{
                int X = Integer.parseInt(XPosBox.getText())-1;
                int Y = Integer.parseInt(YPosBox.getText())-1;
                int Width = pnlMaze.sizeScale*Integer.parseInt(WidthBox.getText());
                int Height = pnlMaze.sizeScale*Integer.parseInt(HeightBox.getText());

                Cell Origin = MazeManager.Instance().GetMaze().Search(X,Y);
                MazeManager.Instance().GetMaze().setLogoX(Origin.GetPosX() + 1);
                MazeManager.Instance().GetMaze().setLogoY(Origin.GetPosY() + 1);
                Image ScaledImage = Logo.getScaledInstance(Width-2,Height-2,Image.SCALE_SMOOTH);

                //Make sure Logo is un-reachable
                for (Cell cell:MazeManager.Instance().GetMaze().getGrid()) {
                    if (cell.GetGridX() >= X & cell.GetGridX() < X+Integer.parseInt(WidthBox.getText())){
                        if (cell.GetGridY() >= Y & cell.GetGridY() < Y+Integer.parseInt(HeightBox.getText())){
                            cell.SetNorthernwall(WallType.Wall);
                            cell.SetSouthernwall(WallType.Wall);
                            cell.SetEasternwall(WallType.Wall);
                            cell.SetWesternwall(WallType.Wall);
                        }
                    }
                }

                //Used to make sure only one LogoPlacer is made, update instead if it exists
                MazeManager.Instance().GetMaze().setLogo(ScaledImage);
                if (i == 0){
                    pnlMaze.add(new LogoPlacer(),BorderLayout.CENTER);
                    i++;
                } else {
                    pnlMaze.repaint();
                }
                pnlMaze.updateUI();
                logoMenu.dispose();

            } catch(NumberFormatException e) {
                JOptionPane.showMessageDialog(logoMenu,"Inputs must be whole numbers.","Input error",JOptionPane.ERROR_MESSAGE);
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(logoMenu,"Error, Either an image hasn't been selected, A maze hasn't been loaded, or Positions are out of bounds! ","Input error",JOptionPane.ERROR_MESSAGE);
            }

        });
        logoMenu.add(placeImage,c);
    }

    public void ImgSrtEnd (MazePanel pnlMaze){
        Font Large  = new Font("Larger",Font.PLAIN, 24 );

        JFrame imgMenu = new JFrame();
        imgMenu.setSize(550, 300);
        imgMenu.setVisible(true);
        imgMenu.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();


        JTextField WidthBox = new JTextField();
        WidthBox.setFont(Large);
        WidthBox.setPreferredSize(new Dimension(60,30));
        imgMenu.add(WidthBox,c);


        JTextField HeightBox = new JTextField();
        HeightBox.setFont(Large);
        HeightBox.setPreferredSize(new Dimension(60,30));
        imgMenu.add(HeightBox,c);




        //Start Image
        JLabel PreviewStart = new JLabel();
        imgMenu.add(PreviewStart,c);

        JButton ImagePickerStart = new JButton("Open an Image");
        ImagePickerStart.addActionListener(e -> {
            StartImage = ImageSelector();
            PreviewStart.setIcon(new ImageIcon(StartImage.getScaledInstance(100,100,Image.SCALE_SMOOTH)));
        });
        imgMenu.add(ImagePickerStart,c);

        //End Image
        JLabel PreviewEnd = new JLabel();
        imgMenu.add(PreviewEnd,c);

        JButton ImagePickerEnd = new JButton("Open an Image");
        ImagePickerEnd.addActionListener(e -> {
            EndImage = ImageSelector();
            PreviewEnd.setIcon(new ImageIcon(EndImage.getScaledInstance(100,100,Image.SCALE_SMOOTH)));
        });
        imgMenu.add(ImagePickerEnd,c);


        //Selection Confirmation Button
        JButton SelectionConfirmation = new JButton("Confirm Selection");
        SelectionConfirmation.setFont(Large);
        SelectionConfirmation.addActionListener(e -> {
            Maze maze = MazeManager.Instance().GetMaze();
            int Width = (pnlMaze.sizeScale*Integer.parseInt(WidthBox.getText()))-2;
            int Height = (pnlMaze.sizeScale*Integer.parseInt(HeightBox.getText()))-2;

            maze.setEndLogoX(maze.Search(maze.getLength()-Integer.parseInt(WidthBox.getText()),maze.getHeight()-Integer.parseInt(HeightBox.getText())).GetPosX());
            maze.setEndLogoY(maze.Search(maze.getLength()-Integer.parseInt(WidthBox.getText()),maze.getHeight()-Integer.parseInt(HeightBox.getText())).GetPosY());
            
            //Used to make sure only one SrtEndPlacer is made, update instead if it exists
            MazeManager.Instance().GetMaze().setStart(StartImage.getScaledInstance(Width,Height,Image.SCALE_SMOOTH));
            MazeManager.Instance().GetMaze().setEnd(EndImage.getScaledInstance(Width,Height,Image.SCALE_SMOOTH));
            if (j == 0){
                pnlMaze.add(new SrtEndPlacer());
                j++;
            } else {
                pnlMaze.repaint();
            }
            pnlMaze.updateUI();
            imgMenu.dispose();
        });
        imgMenu.add(SelectionConfirmation,c);
    }
}
