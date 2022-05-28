package UserInterface;
import javax.swing.*;
import java.awt.*;

public class ImageGUI {

    private static ImageGUI instance = new ImageGUI();
    public static ImageGUI Instance(){
        return instance;
    }
    ImageGUI() {
    }

    public void ImageEditor(){
        JFrame logoMenu = new JFrame();
        logoMenu.setSize(400, 300);
        logoMenu.setVisible(true);
        logoMenu.setLayout(new BorderLayout());

    }

}

