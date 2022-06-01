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
        Image image = null;
        try {
            image =  ImageIO.read(file).getScaledInstance(200,200,Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }


    public void LogoEditor(JPanel frame){
        Font Large  = new Font("Larger",Font.PLAIN, 16 );

        JFrame logoMenu = new JFrame();
        logoMenu.setSize(400, 300);
        logoMenu.setVisible(true);
        logoMenu.setLayout(new BorderLayout());

        JLabel Pos = new JLabel("Positions for Logo (Top-Left)");
        Pos.setBounds(25,10,300,30);

        JTextField WidthBox = new JTextField();
        JLabel WidthLabel = new JLabel("Insert Y Pos for Logo:");
        WidthBox.setFont(Large);
        WidthLabel.setFont(Large);
        WidthBox.setBounds(180,45,40,25);
        WidthLabel.setBounds(20,40, 400,30);

        JTextField HeightBox = new JTextField();
        JLabel HeightLabel = new JLabel("Insert X Pos for Logo:");
        HeightBox.setFont(Large);
        HeightLabel.setFont(Large);
        HeightBox.setBounds(180,75,40,25);
        HeightLabel.setBounds(20,70, 400,30);




        JButton pnlExportGridButton = new JButton("Place Image");
        pnlExportGridButton.setBounds(125,200,150,50);
        pnlExportGridButton.addActionListener(e ->{
                    LogoPlacer logo = new LogoPlacer(Integer.parseInt(HeightBox.getText()),Integer.parseInt(WidthBox.getText()));
                    frame.add(logo,BorderLayout.CENTER);
                    frame.updateUI();
                    logoMenu.dispose();
                    }
                );

        logoMenu.add(Pos);
        logoMenu.add(HeightBox);
        logoMenu.add(HeightLabel);
        logoMenu.add(WidthBox);
        logoMenu.add(WidthLabel);
        logoMenu.setLayout(null);
        logoMenu.add(pnlExportGridButton);

    }
}
