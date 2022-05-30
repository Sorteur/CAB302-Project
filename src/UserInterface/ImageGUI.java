package UserInterface;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;

public class ImageGUI {

    private static ImageGUI instance = new ImageGUI();
    public static ImageGUI Instance(){
        return instance;
    }
    ImageGUI() {
    }

    public File ImageSelector(JFrame frame) {


        JFileChooser K = new JFileChooser();

        K.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "png"));


        K.setAcceptAllFileFilterUsed(false);

        File image = null;
        if (K.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            image = K.getSelectedFile();
            System.out.println("Opened!");
        }
        return image;
    }


    public void PlaceImage(int x, int y,JFrame frame){
        File B = ImageSelector(frame);
    }

    public void ImageEditor(){
        JFrame logoMenu = new JFrame();
        logoMenu.setSize(400, 300);
        logoMenu.setVisible(true);
        logoMenu.setLayout(new BorderLayout());

    }



}

