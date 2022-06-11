package DataClasses;
import java.awt.*;

public class MazeImageResource
{
    private final Image Image;
    private final int PositionX;
    private final int PositionY;

    /**
     * A wrapper containing the components to place an Image on a Maze
     * Includes Image, X position, Y position.
     * @param  image An Image to be placed
     * @param  positionX An int as the X coordinate on the Maze
     * @param  positionY An int as the Y coordinate on the MAze
     * @see Maze
     */
    public MazeImageResource(Image image, int positionX, int positionY){
        Image = image;
        PositionX = positionX;
        PositionY = positionY;
    }
    /**
     * Get an Image in the wrapper
     * @return Image an Image
     */
    public Image GetImage(){ return Image; }
    /**
     * Get the Images X position in the Maze
     * @return int value for X position of Image
     * @see Maze
     */
    public int GetPositionX(){ return PositionX; }
    /**
     * Get the Images Y position in the Maze
     * @return int value for Y position of Image
     * @see Maze
     */
    public int GetPositionY() { return PositionY; }
}
