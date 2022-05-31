package Engine;
import DataClasses.Maze;

public class MazeManager {
    private static MazeManager instance = new MazeManager();
    public static MazeManager Instance(){
        return instance;
    }
    private MazeManager (){
    }

    private Maze Maze;


    public Maze GetMaze() {
        return Maze;
    }

    public Maze CreateMaze (int Length, int Height) {
        Maze = new Maze(Length, Height);
        return Maze;
    }

    public void LoadMaze(int ID) {

    }

    public void SaveMaze (Maze maze) {

    }

    public void ExportMaze (Maze maze){

    }

}
