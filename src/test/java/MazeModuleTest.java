import DataClasses.Maze;
import DataClasses.MazeDescriptions;
import DataClasses.MazeImageResource;
import DataModules.DBConnection;
import DataModules.MazeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.SQLException;



public class MazeModuleTest {


    MazeModule mazeModule;
    Maze Insertmaze;
    Maze Updatemaze;
    int Id;

    @BeforeEach
    @Test
    void CreateAMazeModule() throws SQLException{
        mazeModule = new MazeModule(new DBConnection());
    }

    @Test
    void SaveAMaze() throws SQLException {
        Insertmaze = new Maze(10,10);
        Insertmaze.SetLogo(new MazeImageResource(new BufferedImage(2,2,BufferedImage.TYPE_INT_RGB), 3, 3 ));
        Insertmaze.SetExitImage(new MazeImageResource(new BufferedImage(2,2,BufferedImage.TYPE_INT_RGB), 9, 9 ));
        Insertmaze.SetEntryImage(new MazeImageResource(new BufferedImage(2,2,BufferedImage.TYPE_INT_RGB), 0, 0 ));
        Insertmaze.SetAuthor("TestAuthor"); //normally gets this from the user as they are saving
        Insertmaze.SetDescription("TestMaze"); //normally gets this from the user as they are saving
        mazeModule.SaveMaze(Insertmaze);
    }
    @Test
    void ReadSomeMazeDescriptions() throws  SQLException {
        SaveAMaze();
        MazeDescriptions result = mazeModule.GetMazeDescriptions();

        String[][] resultString =  result.ToStringArray();

        for (int i = 0; i < resultString.length; i++ ) {

            if( resultString[i][1].equals("TestMaze")) {
                    Id = Integer.parseInt(resultString[i][0]); // this is the Id that user would pick from a table

                    assertEquals("TestMaze", resultString[i][1]);
                }
        }
    }
    @Test
    void ReadAMaze() throws SQLException {
        ReadSomeMazeDescriptions();

        Updatemaze = mazeModule.GetMazeFromID(Id);
        Updatemaze  = Updatemaze;
        assertEquals("TestMaze",Updatemaze.GetDescription() );
    }

    @Test
    void OutofBoundsMaze (){
        assertThrows(SQLException.class, () -> {
            mazeModule.GetMazeFromID(9999);
        });
    }

    @Test
    void UpdateMaze() throws SQLException {
        ReadAMaze();
        Updatemaze.SetDescription("UpdatedMaze");
        mazeModule.SaveMaze(Updatemaze);

        Maze UpdatedMaze = mazeModule.GetMazeFromID(Id);// Note we are getting it from the oldmazes Id  (the savemaze method is able to dynamically switch between updating or saving)

        assertEquals("UpdatedMaze", UpdatedMaze.GetDescription());

    }
    @Test
    void UpdateMazeNewLogo() throws SQLException {
        ReadAMaze();
        Updatemaze.SetDescription("UpdatedMaze");
        Updatemaze.SetLogo(new MazeImageResource(new BufferedImage(2,2,BufferedImage.TYPE_INT_RGB), 3, 3 ));
        mazeModule.SaveMaze(Updatemaze);

        Maze UpdatedMaze = mazeModule.GetMazeFromID(Id);// Note we are getting it from the oldmazes Id  (the savemaze method is able to dynamically switch between updating or saving)

        assertEquals("UpdatedMaze", UpdatedMaze.GetDescription());

    }

    @Test
    void UpdateMazeException() {
        assertThrows(SQLException.class, () -> {
            Updatemaze = new Maze(10, 10);
            Updatemaze.SetId(99999);
            Updatemaze.SetDescription("UpdatedMaze");
            mazeModule.SaveMaze(Updatemaze);
        });



    }




}
