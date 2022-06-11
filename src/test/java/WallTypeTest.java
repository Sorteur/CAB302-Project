import DataClasses.WallType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class WallTypeTest {
    @BeforeEach
    @Test
    void ToBool() {
        WallType wallTypeWall = WallType.Wall;
        assertEquals(true, WallType.Tools.ToBool(wallTypeWall) );
        wallTypeWall = WallType.Empty;
        assertEquals(false, WallType.Tools.ToBool(wallTypeWall) );
    }
    @Test
    void ToId() {
        WallType wallTypeWall = WallType.Wall;
        assertEquals(2, WallType.Tools.ToId(wallTypeWall) );
        wallTypeWall = WallType.Empty;
        assertEquals(1, WallType.Tools.ToId(wallTypeWall) );
    }
    @Test
    void IdToWallType() {
        int Id = 2;
        assertEquals(WallType.Wall, WallType.Tools.IdToWallType(Id) );
        Id = 1;
        assertEquals(WallType.Empty, WallType.Tools.IdToWallType(Id) );
    }

}
