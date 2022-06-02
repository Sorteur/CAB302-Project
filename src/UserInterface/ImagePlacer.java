package UserInterface;

import javax.swing.*;
import java.awt.*;

public class ImagePlacer extends JPanel {

    Image logo;
    int X;
    int Y;

    public ImagePlacer(int x, int y) {
        X = x;
        Y = y;
        logo = ImageGUI.Instance().ImageSelector();
        this.setOpaque(false);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(logo,X,Y,this);
    }
}
