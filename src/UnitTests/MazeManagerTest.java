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
        assertNotNull(MazeManager.Instance().GetMaze(),"Couldn't find current maze");
    }

    @Test @Disabled
    void SaveLoadMazeTest() {
        MazeManager.Instance().SaveMaze(MazeManager.Instance().GetMaze());
        MazeManager.Instance().LoadMaze(1);
    }

}
