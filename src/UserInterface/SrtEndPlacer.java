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
        g.drawImage(MazeManager.Instance().GetMaze().getStart(),
                MazeManager.Instance().GetMaze().Search(0,0).GetPosX()+1,
                MazeManager.Instance().GetMaze().Search(0,0).GetPosY()+1,
                this);

        g.drawImage(MazeManager.Instance().GetMaze().getEnd(),
                MazeManager.Instance().GetMaze().getEndLogoX()+1,
                MazeManager.Instance().GetMaze().getEndLogoY()+1,
                this);
    }
}
