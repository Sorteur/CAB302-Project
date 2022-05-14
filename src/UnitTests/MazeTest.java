package UnitTests;

import DataClasses.Cell;
import DataClasses.Maze;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MazeTest {


    Maze maze = new Maze(10, 10);


    @BeforeEach
    @Test
    public void getCell() {

        Cell cell = maze.getCell(10);

    }


}
