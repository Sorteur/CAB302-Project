import DataClasses.Maze;
import Engine.MazeManager;
import UserInterface.MazePanel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

public class MazeManagerTest {

    //Test 1: Verify maze creation and test if GetMaze() returns maze
    @Test
     void GetMazeTest(){
        MazeManager.Instance().CreateMaze(10,10);
        assertNotNull(MazeManager.Instance().GetMaze(),"Couldn't find current maze");
    }

    //Test 2: Test if Autologo creates a maze and gives it a logo.
    @Test
    void AutoLogoCreatorTest(){
        MazePanel TestPanel = new MazePanel();
        Image TestImage = new BufferedImage(2,2,BufferedImage.TYPE_INT_RGB);
        MazeManager.Instance().AutoLogoCreator(TestPanel,10,10,false,false,2,2,TestImage);

        assertNotNull(MazeManager.Instance().GetMaze());
        assertNotNull(MazeManager.Instance().GetMaze().getLogo());
    }

    //Test 3: Test if Logo is placed at 3,3 using CreateLogo
    @Test
    void CreateLogoTest(){
        MazePanel TestPanel = new MazePanel();
        Image TestImage = new BufferedImage(2,2,BufferedImage.TYPE_INT_RGB);
        MazeManager.Instance().CreateMaze(10,10);
        MazeManager.Instance().CreateLogo(TestImage,TestPanel,3,3,2,2);

        assertNotNull(MazeManager.Instance().GetMaze().getLogo());
        assertEquals(3,MazeManager.Instance().GetMaze().getLogo().GetPositionX());
        assertEquals(3,MazeManager.Instance().GetMaze().getLogo().GetPositionY());
    }

    //Test 4: Test if maze can be sucessfully saved and loaded from database
    @Test
    void SaveAndLoadMazeTest(){
        Maze TestMaze = MazeManager.Instance().CreateMaze(10,10);
        TestMaze.SetAuthor("Tyler");
        //TestMaze.
        MazeManager.Instance().SaveMaze();

        MazeManager.Instance().LoadMazeFromId(1);
        assertEquals(TestMaze,MazeManager.Instance().GetMaze());
    }




}
