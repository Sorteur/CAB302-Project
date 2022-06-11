import DataClasses.MazeDescriptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class MazeDescriptionsTest {

    MazeDescriptions MazeDescriptions;

    @BeforeEach
    @Test
    void MazeDescription(){
        MazeDescriptions = new MazeDescriptions(1);
    }
    @Test
    void DescriptionReadandWrite(){
        MazeDescriptions = new MazeDescriptions(1);
        LocalDateTime timeC = LocalDateTime.now();
        LocalDateTime timeU = LocalDateTime.now();
        MazeDescriptions.InsertDescription(0, 1,"TestName","TestAuthor", timeC, timeU);

        String[][] ReadDescription = MazeDescriptions.ToStringArray();
        assertEquals("1", ReadDescription[0][0]);
        assertEquals("TestName", ReadDescription[0][1]);
        assertEquals("TestAuthor", ReadDescription[0][2]);
        assertEquals(timeC.toString(), ReadDescription[0][3]);
        assertEquals(timeU.toString(), ReadDescription[0][4]);


    }

}
