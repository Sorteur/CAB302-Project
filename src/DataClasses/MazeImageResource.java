package DataClasses;
import java.awt.*;

public class MazeImageResource
{
    private final Image Image;
    private final int PositionX;
    private final int PositionY;

    public MazeImageResource(Image image, int positionX, int positionY){
        Image = image;
        PositionX = positionX;
        PositionY = positionY;
    }

    public Image GetImage(){ return Image; }
    public int GetPositionX(){ return PositionX; }
    public int GetPositionY() { return PositionY; }
}
