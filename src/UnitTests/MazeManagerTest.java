package UnitTests;

import Engine.MazeManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class MazeManagerTest {

    @BeforeEach @Test
     void CreateMazeTest(){
        MazeManager.Instance().CreateMaze(10,10);
    }

    @Test
     void LoadMazeTest(){
        assertNotNull(MazeManager.Instance().LoadMaze(),"Couldn't find current maze");
    }

    @Test @Disabled
    void SaveLoadMazeTest() {
        MazeManager.Instance().SaveMaze(MazeManager.Instance().LoadMaze());
        MazeManager.Instance().GetMaze(1);
    }

}
