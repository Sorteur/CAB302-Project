package UnitTests;
import DataClasses.Cell;
import DataClasses.Maze;
import DataClasses.WallType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MazeTest {
    //Declaring maze
    Maze TestMaze;

    // Test 1: Construct maze Object
    @BeforeEach
    @Test
    void ConstructMaze() {
        TestMaze = new Maze(6, 4);
    }

    //Test 2: Test to see if length is entered properly
    @Test
    void MazeLengthTest(){
        assertEquals(6,TestMaze.getLength());
    }

    //Test 3: Test to see if height is entered properly
    @Test
    void MazeHeightTest(){
        assertEquals(4,TestMaze.getHeight());
    }

    //Test 4: Test to see if the correct number of cells are generated
    @Test
    void MazeCellCheckTest(){
        assertEquals(24,TestMaze.getGrid().size());
    }

    //Test 5: Test to see if the Search method is correct
    @Test
    void SearchTest(){
        //Setting up searched cell
        Cell searched = TestMaze.Search(3,2);
        //Test if x lines up
        assertEquals(3,searched.GetGridX());
        //Test if y lines up
        assertEquals(2,searched.GetGridY());
    }

    //Test 6: Test if search method returns null if search parameters are out of bounds
    @Test
    void SearchFailTest(){
        assertNull(TestMaze.Search(10,10));
    }

    //Test 7: Test to see if cell 3,1 is a viable location for an Auto-logo
    @Test
    void ViableLogoTestTrue(){
        assertTrue(TestMaze.ViableImageLogo(1, 1, TestMaze.Search(3, 1)));
    }

    //Test 8: Test to see if cell 4,3 is not viable location for an Auto-logo
    @Test
    void ViableLogoTestFalse(){
        assertFalse(TestMaze.ViableImageLogo(1, 1, TestMaze.Search(4, 3)));
    }

    //Test 9: Test to see if WallSwitcher Works
    @Test
    void WallSwitcherTest(){
        Cell testCell = TestMaze.getCell(4);
        assertEquals(WallType.Empty,TestMaze.SwitchWallType(testCell.IsNorthernwall()));
    }

    //Test 10: Test to see if Check(North/South/East/West)Cell works
    @Test
   void CheckNeighbourCellsTest(){
        //Using cell at 0,0 as test, north and west should be null, south and east should be cell at 0,1 and 1,0 respectively
        assertNull(TestMaze.checkNorthCell(0));
        assertNull(TestMaze.checkWestCell(0));
        assertEquals(TestMaze.Search(0,1),TestMaze.checkSouthCell(0));
        assertEquals(TestMaze.Search(1,0),TestMaze.checkEastCell(0));

    }

    //Test 11: Test to see if PathFind(North/South/East/West)Cell works
    @Test
    void CheckNeighbourPathFindTest(){
        TestMaze.SetCell(0,new Cell(0,0,0,WallType.Wall,WallType.Empty,WallType.Wall,WallType.Wall));
        assertNull(TestMaze.PathFindNorthCell(0));
        assertNull(TestMaze.PathFindWestCell(0));
        assertNull(TestMaze.PathFindEastCell(0));
        assertEquals(TestMaze.Search(0,1),TestMaze.PathFindSouthCell(0));
    }
}


/*
    //Test :
    @Test
    void name(){

    }
 */