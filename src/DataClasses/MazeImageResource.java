package DataClasses;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Blob;

public class MazeImageResource
{
    private Image Image;
    private int PositionX;
    private int PositionY;
    private int GridScaleX;
    private int GridScaleY;

    MazeImageResource(int positionX, int positionY){
        PositionX = positionX;
        PositionY = positionY;
    }

    MazeImageResource(Image image, int positionX, int positionY){
        Image = image;
        PositionX = positionX;
        PositionY = positionY;
    }


    public void SetImage(Image image)
    {
        Image = image;
    }

    public Image GetImage(){
        return Image;
    }

    public void SetPosition(int positionX, int positionY)
    {
        PositionX = positionX;
        PositionY = positionY;
    }

    public int GetPositionX()
    {
        return PositionX;

    }

    public int GetPositionY()
    {
        return PositionY;
    }

    public void SetGridScale(int gridScaleX, int gridScaleY)
    {
        GridScaleX = gridScaleX;
        GridScaleY = gridScaleY;
    }

    public int GetGridScaleX()
    {
        return GridScaleX;
    }

    public int GetGridScaleY()
    {
        return GridScaleY;
    }

    public ByteArrayInputStream GetImageAsBlob()
    {// with help of this as reference https://stackoverflow.com/questions/20961065/converting-image-in-memory-to-a-blob
        BufferedImage bufferedImage = new BufferedImage(Image.getWidth(null), Image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.drawImage(Image, 0, 0, null);
        graphics2D.dispose();

        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        } catch (Exception exception){

        }
        finally {
            try{
                byteArrayOutputStream.close();
            } catch (Exception exception) {

            }
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        return byteArrayInputStream;
    }

}
