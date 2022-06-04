package UserInterface;

import Engine.MazeManager;
import javax.swing.*;
import java.awt.*;

public class LogoPlacer extends JPanel {
    public LogoPlacer() {
        this.setOpaque(false);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(MazeManager.Instance().GetMaze().getLogo(),MazeManager.Instance().GetMaze().getLogoX(),MazeManager.Instance().GetMaze().getLogoY(),this);
    }
}
