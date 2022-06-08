package UserInterface;
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
                int WidthBoxInt = Integer.parseInt(WidthBox.getText());
                int HeightBoxInt = Integer.parseInt(HeightBox.getText());
                MazeManager.Instance().CreateLogo(Logo,pnlMaze, X, Y, WidthBoxInt, HeightBoxInt,i);
                i++;
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
        imgMenu.setSize(600, 275);
        imgMenu.setVisible(true);
        imgMenu.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weighty = 0.1;
        c.insets = new Insets(0,5,0,5);
        c.anchor = GridBagConstraints.CENTER;

        JLabel WidthLabel = new JLabel("Width of Images");
        WidthLabel.setFont(Large);
        c.gridx = 0;
        c.gridy = 0;
        imgMenu.add(WidthLabel,c);

        JTextField WidthBox = new JTextField();
        WidthBox.setFont(Large);
        WidthBox.setPreferredSize(new Dimension(60,30));
        c.gridx = 1;
        c.gridy = 0;
        imgMenu.add(WidthBox,c);

        JLabel HeightLabel = new JLabel("Height of Images");
        HeightLabel.setFont(Large);
        c.gridx = 0;
        c.gridy = 1;
        imgMenu.add(HeightLabel,c);

        JTextField HeightBox = new JTextField();
        HeightBox.setFont(Large);
        HeightBox.setPreferredSize(new Dimension(60,30));
        c.gridx = 1;
        c.gridy = 1;
        imgMenu.add(HeightBox,c);

        //Start Image
        JLabel PreviewStart = new JLabel();
        c.gridx = 2;
        c.gridy = 0;
        c.gridheight = 2;
        imgMenu.add(PreviewStart,c);

        JButton ImagePickerStart = new JButton("Select start image");
        ImagePickerStart.addActionListener(e -> {
            StartImage = ImageSelector();
            PreviewStart.setIcon(new ImageIcon(StartImage.getScaledInstance(100,100,Image.SCALE_SMOOTH)));
        });
        c.gridx = 2;
        c.gridy = 2;
        imgMenu.add(ImagePickerStart,c);

        //End Image
        JLabel PreviewEnd = new JLabel();
        c.gridx = 3;
        c.gridy = 0;
        c.gridheight = 2;
        imgMenu.add(PreviewEnd,c);

        JButton ImagePickerEnd = new JButton("Select end image");
        ImagePickerEnd.addActionListener(e -> {
            EndImage = ImageSelector();
            PreviewEnd.setIcon(new ImageIcon(EndImage.getScaledInstance(100,100,Image.SCALE_SMOOTH)));
        });
        c.gridx = 3;
        c.gridy = 2;
        imgMenu.add(ImagePickerEnd,c);


        //Selection Confirmation Button
        JButton SelectionConfirmation = new JButton("Confirm Selection");
        SelectionConfirmation.setFont(Large);
        c.gridx = 0;
        c.gridwidth = 2;
        c.gridy = 2;
        c.anchor = GridBagConstraints.CENTER;
        SelectionConfirmation.addActionListener(a -> {
            try {
                int X = Integer.parseInt(WidthBox.getText());
                int Y = Integer.parseInt(HeightBox.getText());
                int Width = (pnlMaze.sizeScale*X)-2;
                int Height = (pnlMaze.sizeScale*Y)-2;
                MazeManager.Instance().StartEndCreator(pnlMaze, X, Y, Width, Height,EndImage,StartImage,j);
                imgMenu.dispose();
                j++;
            } catch (NumberFormatException e){
                JOptionPane.showMessageDialog(imgMenu,"Inputs must be whole numbers.","Input error", JOptionPane.ERROR_MESSAGE);
            } catch (NullPointerException e){
                JOptionPane.showMessageDialog(imgMenu,"Error, Either the images haven't been selected or Positions are out of bounds! ","Input error", JOptionPane.ERROR_MESSAGE);
            }

        });
        imgMenu.add(SelectionConfirmation,c);
    }


    public void AutoLogo (MazePanel pnlMaze,int MazeLength,int MazeHeight,boolean Random, boolean ImageStartEnd){
        Font Large  = new Font("Larger",Font.PLAIN, 24 );

        JFrame AutoMenu = new JFrame();
        AutoMenu.setSize(600, 275);
        AutoMenu.setVisible(true);
        AutoMenu.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weighty = 0.1;
        c.insets = new Insets(0,5,0,5);
        c.anchor = GridBagConstraints.CENTER;

        JLabel WidthLabel = new JLabel("Width of logo");
        WidthLabel.setFont(Large);
        c.gridx = 0;
        c.gridy = 0;
        AutoMenu.add(WidthLabel,c);

        JTextField WidthBox = new JTextField();
        WidthBox.setFont(Large);
        WidthBox.setPreferredSize(new Dimension(60,30));
        c.gridx = 1;
        c.gridy = 0;
        AutoMenu.add(WidthBox,c);

        JLabel HeightLabel = new JLabel("Height of logo");
        HeightLabel.setFont(Large);
        c.gridx = 0;
        c.gridy = 1;
        AutoMenu.add(HeightLabel,c);

        JTextField HeightBox = new JTextField();
        HeightBox.setFont(Large);
        HeightBox.setPreferredSize(new Dimension(60,30));
        c.gridx = 1;
        c.gridy = 1;
        AutoMenu.add(HeightBox,c);

        //Start Image
        JLabel PreviewStart = new JLabel();
        c.gridx = 2;
        c.gridy = 0;
        c.gridheight = 2;
        AutoMenu.add(PreviewStart,c);

        JButton ImagePickerStart = new JButton("Select logo image");
        ImagePickerStart.addActionListener(e -> {
            Logo = ImageSelector();
            PreviewStart.setIcon(new ImageIcon(Logo.getScaledInstance(100,100,Image.SCALE_SMOOTH)));
        });
        c.gridx = 2;
        c.gridy = 2;
        AutoMenu.add(ImagePickerStart,c);

        //Selection Confirmation Button
        JButton SelectionConfirmation = new JButton("Confirm Selection");
        SelectionConfirmation.setFont(Large);
        c.gridx = 0;
        c.gridwidth = 2;
        c.gridy = 2;
        c.anchor = GridBagConstraints.CENTER;
        SelectionConfirmation.addActionListener(a -> {
            try {
                int BoxX = Integer.parseInt(WidthBox.getText());
                int BoxY = Integer.parseInt(HeightBox.getText());

                MazeManager.Instance().AutoLogoCreator(pnlMaze,MazeLength,MazeHeight, Random, ImageStartEnd, BoxX, BoxY, Logo);
                AutoMenu.dispose();
            } catch (NumberFormatException e){
                JOptionPane.showMessageDialog(AutoMenu,"Inputs must be whole numbers.","Input error", JOptionPane.ERROR_MESSAGE);
            } catch (NullPointerException e){
                JOptionPane.showMessageDialog(AutoMenu,"Error, Either the image haven't been selected or Positions are out of bounds! ","Input error", JOptionPane.ERROR_MESSAGE);
            }

        });
        AutoMenu.add(SelectionConfirmation,c);
    }

}
