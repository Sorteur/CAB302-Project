package Engine;
import DataClasses.Maze;

public class MazeManager {
    private static MazeManager instance = new MazeManager();

    private Maze Maze;
    private MazeManager (){

    }

    public static MazeManager Instance(){
        return instance;
    }

    public Maze LoadMaze () {
        return Maze;
    }

    public void CreateMaze (int Length, int Height) {
        Maze = new Maze(Length, Height);
    }


}
