package UserInterface;

import Engine.MazeManager;
import javax.swing.*;
import java.awt.*;

public class LogoPlacer extends JPanel {
    public LogoPlacer() {
        this.setOpaque(false);
    }
    public void paintComponent(Graphics g){
        if(MazeManager.Instance().GetMaze() != null) {
            if (MazeManager.Instance().GetMaze().getLogo() != null) {
                super.paintComponent(g);
                int x = MazeManager.Instance().GetMaze().Search(MazeManager.Instance().GetMaze().getLogo().GetPositionX(), MazeManager.Instance().GetMaze().getLogo().GetPositionY()).GetPosX();
                int y = MazeManager.Instance().GetMaze().Search(MazeManager.Instance().GetMaze().getLogo().GetPositionX(), MazeManager.Instance().GetMaze().getLogo().GetPositionY()).GetPosY();

                g.drawImage(MazeManager.Instance().GetMaze().getLogo().GetImage(), x + 1, y + 1, this);
            }
        }
    }
}
