package UserInterface;

import Engine.MazeManager;
import javax.swing.*;
import java.awt.*;

public class SrtEndPlacer extends JPanel{
    public SrtEndPlacer() {
        this.setOpaque(false);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(MazeManager.Instance().GetMaze().getEntryImage().GetImage(),
                MazeManager.Instance().GetMaze().Search(0,0).GetPosX()+1,
                MazeManager.Instance().GetMaze().Search(0,0).GetPosY()+1,
                this);

        g.drawImage(MazeManager.Instance().GetMaze().getExitImage().GetImage(),
                MazeManager.Instance().GetMaze().getExitImage().GetPositionX()+1,
                MazeManager.Instance().GetMaze().getExitImage().GetPositionY()+1,
                this);
    }
}
