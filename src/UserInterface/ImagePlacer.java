package UserInterface;

import Engine.MazeManager;

import javax.swing.*;
import java.awt.*;

public class ImagePlacer extends JPanel {
    public ImagePlacer() {
        this.setOpaque(false);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(MazeManager.Instance().GetMaze().getLogo(),MazeManager.Instance().GetMaze().getLogoX(),MazeManager.Instance().GetMaze().getLogoY(),this);
    }
}
