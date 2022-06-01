package UserInterface;

import javax.swing.*;
import java.awt.*;

public class LogoPlacer extends JPanel {

    Image logo;
    int X;
    int Y;

    public LogoPlacer(int x, int y) {
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
