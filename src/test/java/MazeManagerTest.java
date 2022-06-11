import DataClasses.Maze;
import Engine.MazeManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import javax.swing.*;

public class MazeManagerTest {
    JPanel TestPanel;
    //Test 1: Test to see if maze is created
    @BeforeEach @Test
     void CreateMazeTest(){
        MazeManager.Instance().CreateMaze(10,10);
        TestPanel = new JPanel();
    }

    //Test 2: Verify maze creation and test if GetMaze() returns maze
    @Test
     void GetMazeTest(){
        assertNotNull(MazeManager.Instance().GetMaze(),"Couldn't find current maze");
    }

    //Test 3:
    @Test
    void AutoLogoCreatorTest(){
        //MazeManager.Instance().AutoLogoCreator(TestPanel,10,10);
    }

}
