package DataClasses;

import java.awt.*;

public class MazeImageResource
{
    private Image Image;
    private int PositionX;
    private int PositionY;

    MazeImageResource(int positionX, int positionY){
        PositionX = positionX;
        PositionY = positionY;
    }

    MazeImageResource(Image image, int positionX, int positionY){
        Image = image;
        PositionX = positionX;
        PositionY = positionY;
    }


    public void SetPosition(int positionX, int positionY)
    {
        PositionX = positionX;
        PositionY = positionY;
    }

    public int GetPositionX(){
        return PositionX;
    }

    public int GetPositionY(){
        return PositionY;
    }

    public void SetImage(Image image)
    {
        Image = image;
    }

    public Image GetImage(){
        return Image;
    }

}
