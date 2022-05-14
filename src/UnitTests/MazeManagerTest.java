package UnitTests;

import Engine.MazeManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class MazeManagerTest {

    @Test
    public void CreateMazeTest() throws Exception {
        MazeManager.Instance().CreateMaze(10,10);
        assertNotNull(MazeManager.Instance().LoadMaze());
    }




}
