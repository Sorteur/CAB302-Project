import DataClasses.Cell;
import DataClasses.Maze;
import DataClasses.WallType;
import Engine.MazeGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



public class MazeGeneratorTest {
    @BeforeEach
    //Note the Constructor of Generate Maze is empty and I see no point testing it
    @Test
    void GenerateAmaze(){
        Maze maze = MazeGenerator.Instance().GenerateMaze(new Maze(10, 10));
        Cell cell = maze.getCell(0);

        int count = 0;

        // A normal maze will have cell with full walls. A generated cell will have some walls be empty
        if (cell.IsWesternwall() == WallType.Empty)
        {
            count = 1;
        }
        if (cell.IsEasternwall() == WallType.Empty)
        {
            count = 1;
        }
        if (cell.IsSouthernwall() == WallType.Empty)
        {
            count = 1;
        }
        if (cell.IsNorthernwall() == WallType.Empty)
        {
            count = 1;
        }

        assertEquals(1, count);

    }
    @Test
    void GenerateAmazeMin(){
        Maze maze = MazeGenerator.Instance().GenerateMaze(new Maze(6, 4));
        Cell cell = maze.getCell(0);

        int count = 0;

        // A normal maze will have cell with full walls. A generated cell will have some walls be empty
        if (cell.IsWesternwall() == WallType.Empty)
        {
            count = 1;
        }
        if (cell.IsEasternwall() == WallType.Empty)
        {
            count = 1;
        }
        if (cell.IsSouthernwall() == WallType.Empty)
        {
            count = 1;
        }
        if (cell.IsNorthernwall() == WallType.Empty)
        {
            count = 1;
        }

        assertEquals(1, count);

    }
    @Test
    void GenerateAmazeMax(){
        Maze maze = MazeGenerator.Instance().GenerateMaze(new Maze(100, 100));
        Cell cell = maze.getCell(0);

        int count = 0;

        // A normal maze will have cell with full walls. A generated cell will have some walls be empty
        if (cell.IsWesternwall() == WallType.Empty)
        {
            count = 1;
        }
        if (cell.IsEasternwall() == WallType.Empty)
        {
            count = 1;
        }
        if (cell.IsSouthernwall() == WallType.Empty)
        {
            count = 1;
        }
        if (cell.IsNorthernwall() == WallType.Empty)
        {
            count = 1;
        }

        assertEquals(1, count);

    }

    @Test
    void GenerateRandomLogoCell(){

        Cell cell = MazeGenerator.Instance().RandomCellLogo(new Maze(10, 10), 2, 2);// note x and y range are the size of the Logo

        int count = 0;

        if (cell.Index < 100) // means that the cell was within maze
        {
            count++;
        }

        assertEquals(1, count);
    }
}
