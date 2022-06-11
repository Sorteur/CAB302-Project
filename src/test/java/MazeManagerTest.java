import DataClasses.Maze;
import Engine.MazeManager;
import UserInterface.MazePanel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
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
    void CreateLogoTest() throws SQLException{
        MazePanel TestPanel = new MazePanel();
        Image TestImage = new BufferedImage(2,2,BufferedImage.TYPE_INT_RGB);
        MazeManager.Instance().CreateMaze(10,10);
        MazeManager.Instance().CreateLogo(TestImage,TestPanel,3,3,2,2);

        assertNotNull(MazeManager.Instance().GetMaze().getLogo());
        assertEquals(3,MazeManager.Instance().GetMaze().getLogo().GetPositionX());
        assertEquals(3,MazeManager.Instance().GetMaze().getLogo().GetPositionY());
    }

    //Test 4: Test if maze can be successfully saved and loaded from database using an ID
    @Test
    void SaveAndLoadMazeIDTest() throws SQLException {
        Maze TestMaze = MazeManager.Instance().CreateMaze(10,10);
        TestMaze.SetDescription("This is a Test");
        MazeManager.Instance().SaveMaze();
        int ID = MazeManager.Instance().GetMaze().GetId();

        MazeManager.Instance().LoadMazeFromId(ID);
        assertEquals(TestMaze.GetDescription(),MazeManager.Instance().GetMaze().GetDescription());
    }

    //Test 5:
    @Test
    void LoadMazeDescTest() throws SQLException {
        Maze TestMaze = MazeManager.Instance().CreateMaze(10,10);
        TestMaze.SetDescription("This is a Test");
        MazeManager.Instance().SaveMaze();

        assertNotNull(MazeManager.Instance().LoadMazeDescriptions());
    }

    //Test 6: Test to see if LogoDraw creates a new LogoPlacer
    @Test
    void LogoDrawTest(){
        MazePanel TestPanel = new MazePanel();
        assertEquals(0,TestPanel.getComponents().length);

        MazeManager.Instance().LogoDraw(TestPanel);
        assertEquals(1,TestPanel.getComponents().length);

    }

    //Test 7:
    @Test
    void StartEndCreatorTest(){
        MazePanel TestPanel = new MazePanel();
        Image TestImage = new BufferedImage(2,2,BufferedImage.TYPE_INT_RGB);
        MazeManager.Instance().CreateMaze(10,10);
        MazeManager.Instance().StartEndCreator(TestPanel,5,5,2,2,TestImage,TestImage);

        assertNotNull(MazeManager.Instance().GetMaze().getEntryImage());
        assertNotNull(MazeManager.Instance().GetMaze().getExitImage());
        assertEquals(5,MazeManager.Instance().GetMaze().getExitImage().GetPositionX());
        assertEquals(5,MazeManager.Instance().GetMaze().getExitImage().GetPositionY());
    }

    //Test 8: Test to see if StartEndDraw creates a new SrtEndPlacer
    @Test
    void StartEndDrawTest(){
        MazePanel TestPanel = new MazePanel();
        assertEquals(0,TestPanel.getComponents().length);

        MazeManager.Instance().StartEndDraw(TestPanel);
        assertEquals(1,TestPanel.getComponents().length);

    }
}
