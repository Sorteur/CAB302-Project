package UserInterface;
import DataClasses.Cell;
import Engine.MazeManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class ImageGUI {

    private static final ImageGUI instance = new ImageGUI();
    private Image Logo;
    private Image StartImage;
    private Image EndImage;

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

        JButton pnlExportGridButton = new JButton("Place Image");
        pnlExportGridButton.setFont(Large);
        c.gridx = 2;
        c.gridwidth = 2;
        c.gridy = 3;
        c.gridheight = 2;
        c.weighty = 1;
        //c.weightx = 1;
        //c.fill  = GridBagConstraints.HORIZONTAL;

        pnlExportGridButton.addActionListener(a ->{
            try{
                int X = Integer.parseInt(XPosBox.getText())-1;
                int Y = Integer.parseInt(YPosBox.getText())-1;
                Cell Origin = MazeManager.Instance().GetMaze().Search(X,Y);
                MazeManager.Instance().GetMaze().setLogoX(Origin.getPosX() + 1);
                MazeManager.Instance().GetMaze().setLogoY(Origin.getPosY() + 1);
                int Width = pnlMaze.sizeScale*Integer.parseInt(WidthBox.getText());
                int Height = pnlMaze.sizeScale*Integer.parseInt(HeightBox.getText());
                Image ScaledImage = Logo.getScaledInstance(Width-2,Height-2,Image.SCALE_SMOOTH);

                for (Cell cell:MazeManager.Instance().GetMaze().getGrid()) {
                    if (cell.getGridX() >= X & cell.getGridX() < X+Integer.parseInt(WidthBox.getText())){
                        if (cell.getGridY() >= Y & cell.getGridY() < Y+Integer.parseInt(HeightBox.getText())){
                            cell.setNwall(true);
                            cell.setSwall(true);
                            cell.setEwall(true);
                            cell.setWwall(true);
                        }
                    }
                }
                MazeManager.Instance().GetMaze().setLogo(ScaledImage);
                pnlMaze.add(new ImagePlacer(),BorderLayout.CENTER);
                pnlMaze.updateUI();
                logoMenu.dispose();

            } catch(NumberFormatException e) {
                JOptionPane.showMessageDialog(logoMenu,"Inputs must be whole numbers.","Input error",JOptionPane.ERROR_MESSAGE);
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(logoMenu,"Error, Either an image hasn't been selected, A maze hasn't been loaded, or Positions are out of bounds! ","Input error",JOptionPane.ERROR_MESSAGE);
            }

        });
        logoMenu.add(pnlExportGridButton,c);
    }

    public void ImgSrtEnd (){
        Font Large  = new Font("Larger",Font.PLAIN, 24 );

        JFrame imgMenu = new JFrame();
        imgMenu.setSize(550, 300);
        imgMenu.setVisible(true);
        imgMenu.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel PreviewStart = new JLabel();
        imgMenu.add(PreviewStart,c);



        JButton ImagePickerStart = new JButton("Open an Image");
        ImagePickerStart.addActionListener(e -> {
            StartImage = ImageSelector();
            PreviewStart.setIcon(new ImageIcon(Logo.getScaledInstance(100,100,Image.SCALE_SMOOTH)));
        });
        imgMenu.add(ImagePickerStart,c);



        JButton SelectionConfirmation = new JButton("Confirm Selection");
        SelectionConfirmation.setFont(Large);
        SelectionConfirmation.addActionListener(e -> {
            MazeManager.Instance().GetMaze().setStart(StartImage);
        });
        imgMenu.add(SelectionConfirmation,c);


    }





}
