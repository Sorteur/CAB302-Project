package DataClasses;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

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
