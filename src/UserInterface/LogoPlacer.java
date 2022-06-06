package UserInterface;

import Engine.MazeManager;
import javax.swing.*;
import java.awt.*;

public class LogoPlacer extends JPanel {
    public LogoPlacer() {
        this.setOpaque(false);
    }
    public void paintComponent(Graphics g){
        if (MazeManager.Instance().GetMaze().getLogo() != null){
            super.paintComponent(g);
            g.drawImage(MazeManager.Instance().GetMaze().getLogo().GetImage(),MazeManager.Instance().GetMaze().getLogo().GetPositionX(),MazeManager.Instance().GetMaze().getLogo().GetPositionY(),this);
        }
    }
}
