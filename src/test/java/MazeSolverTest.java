import DataClasses.Maze;
import Engine.MazeGenerator;
import Engine.MazeSolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MazeSolverTest {

    @BeforeEach
    @Test
    void SolvableMaze() throws Exception{
        Maze maze = MazeGenerator.Instance().GenerateMaze(new Maze(10, 10));

        MazeSolver.Instance().SolveMaze(maze); // if it finds a solution it doesn't throw an Exception
    }
    @Test
    void ImpossibleMaze() {
        Maze maze = new Maze(10, 10);

        assertThrows(Exception.class, () -> {
            MazeSolver.Instance().SolveMaze(maze);
        });

    }
}
