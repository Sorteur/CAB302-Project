package UnitTests;
import DataClasses.Cell;
import DataClasses.Maze;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MazeTest {
    //Declaring maze
    Maze TestMaze;

    // Test 1: Construct maze Object
    @BeforeEach
    @Test
    public void ConstructMaze(){
        TestMaze = new Maze(6, 4, "Test");
    }

    //Test 2: Test to see if length is entered properly
    @Test
    public void MazeLengthTest(){
        assertEquals(6,TestMaze.getLength());
    }

    //Test 3: Test to see if height is entered properly
    @Test
    public void MazeHeightTest(){
        assertEquals(4,TestMaze.getHeight());
    }

    //Test 4: Test to see if the correct number of cells are generated
    @Test
    public void MazeCellCheckTest(){
        assertEquals(24,TestMaze.getGrid().size());
    }

    //Test 5: Test to see if the Search method is correct
    @Test
    public void SearchTest(){
        //Setting up searched cell
        Cell searched = TestMaze.Search(3,2);
        //Test if x lines up
        assertEquals(3,searched.getGridX());
        //Test if y lines up
        assertEquals(2,searched.getGridY());
    }

    //Test 6: Test if search method returns null *and message* if search parameters are out of bounds
    @Test
    public void SearchFailTest(){
        assertNull(TestMaze.Search(10,10));
    }

//TODO Add more unit tests of maze constructor, especially about failing to create maze





}


/*
    //Test :
    @Test
    public void name(){
    }
 */