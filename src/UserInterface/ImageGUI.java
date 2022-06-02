package UserInterface;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImageGUI {

    private static ImageGUI instance = new ImageGUI();
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
            image =  ImageIO.read(file).getScaledInstance(200,200,Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }


    public void LogoEditor(JPanel frame){
        Font Large  = new Font("Larger",Font.PLAIN, 24 );

        JFrame logoMenu = new JFrame();
        logoMenu.setSize(400, 300);
        logoMenu.setVisible(true);
        logoMenu.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weighty = 0.1;
        c.insets = new Insets(5,5,0,5);
        c.anchor = GridBagConstraints.LINE_START;


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

        JLabel WidthLabel = new JLabel("Width of Image:");
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

        JLabel HeightLabel = new JLabel("Height of Image:");
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



        JButton pnlExportGridButton = new JButton("Place Image");
        c.gridx = 2;
        c.gridwidth = 2;
        c.gridy = 3;
        logoMenu.add(pnlExportGridButton,c);
        pnlExportGridButton.addActionListener(e ->{
                    ImagePlacer logo = new ImagePlacer(Integer.parseInt(XPosBox.getText()),Integer.parseInt(YPosBox.getText()));
                    frame.add(logo,BorderLayout.CENTER);
                    frame.updateUI();
                    logoMenu.dispose();
                    }
                );


    }
}
