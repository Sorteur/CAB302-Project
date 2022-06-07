package UserInterface;

import Engine.MazeManager;
import javax.swing.*;
import java.awt.*;

public class SrtEndPlacer extends JPanel{
    public SrtEndPlacer() {
        this.setOpaque(false);
    }
    public void paintComponent(Graphics g){

        if(MazeManager.Instance().GetMaze().getExitImage() != null){

            int Endx = MazeManager.Instance().GetMaze().Search(MazeManager.Instance().GetMaze().getExitImage().GetPositionX(),MazeManager.Instance().GetMaze().getExitImage().GetPositionY()).GetPosX();
            int Endy = MazeManager.Instance().GetMaze().Search(MazeManager.Instance().GetMaze().getExitImage().GetPositionX(),MazeManager.Instance().GetMaze().getExitImage().GetPositionY()).GetPosY();


            super.paintComponent(g);
            g.drawImage(MazeManager.Instance().GetMaze().getEntryImage().GetImage(),
                    MazeManager.Instance().GetMaze().Search(0,0).GetPosX()+1,
                    MazeManager.Instance().GetMaze().Search(0,0).GetPosY()+1,
                    this);

            g.drawImage(MazeManager.Instance().GetMaze().getExitImage().GetImage(),
                    Endx+1,
                    Endy+1,
                    this);
        }
    }
}
