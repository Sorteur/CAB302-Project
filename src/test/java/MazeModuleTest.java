import DataClasses.Maze;
import DataClasses.MazeDescriptions;
import DataModules.DBConnection;
import DataModules.MazeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;



public class MazeModuleTest {

    MazeModule mazeModule;
    Maze Insertmaze = new Maze(10, 10);
    Maze Updatemaze;
    int Id;
    @BeforeEach
    @Test
    void CreateAMazeModule() throws SQLException{
        mazeModule = new MazeModule(new DBConnection());
    }

    @Test
    void SaveAMaze() throws SQLException {
        Insertmaze.SetAuthor("TestAuthor"); //normally gets this from the user as they are saving
        Insertmaze.SetDescription("TestMaze"); //normally gets this from the user as they are saving
        mazeModule.SaveMaze(Insertmaze);
    }
    @Test
    void ReadSomeMazeDescriptions() throws  SQLException {
        MazeDescriptions result = mazeModule.GetMazeDescriptions();

        String[][] resultString =  result.ToStringArray();

        for (int i = 0; i < resultString.length; i++ ) {

            if(resultString[i][1] == "TestMaze")
            {
                Id = Integer.parseInt(resultString[i][0]); // this is the Id that user would pick from a table
                assertEquals("TestMaze", resultString[i][1]);
            }

        }
    }
    @Test
    void ReadAMaze() throws SQLException {
        Updatemaze = mazeModule.GetMazeFromID(Id);
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
        Updatemaze.SetDescription("UpdatedMaze");
        mazeModule.SaveMaze(Updatemaze);

        Maze UpdatedMaze = mazeModule.GetMazeFromID(Id);// Note we are getting it from the oldmazes Id  (the savemaze method is able to dynamically switch between updating or saving)

        assertEquals("UpdatedMaze", UpdatedMaze.GetDescription());

    }

}
