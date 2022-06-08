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
    private Image Image;
    private int Id = 0;
    private int ImageTypeId;
    private int PositionX;
    private int PositionY;


    public MazeImageResource(Image image, int positionX, int positionY){
        Image = image;
        PositionX = positionX;
        PositionY = positionY;
    }


    public Image GetImage(){
        return Image;
    }


    public int GetPositionX()
    {
        return PositionX;

    }

    public int GetPositionY()
    {
        return PositionY;
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

    public static Image GetBlobAsImage (Blob blob) throws SQLException {
        // with help of this as reference https://stackoverflow.com/questions/22923518/how-can-i-convert-a-bufferedimage-to-an-image
        // and this https://stackoverflow.com/questions/50427495/java-blob-to-image-file

        Image image = null;
        try {
            InputStream inputStream = blob.getBinaryStream(1, blob.length());
            image = ImageIO.read(inputStream);

        }catch (IOException ioException){

        }
        return image;
    }

}
