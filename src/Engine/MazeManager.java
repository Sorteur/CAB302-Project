package Engine;

public class MazeManager {
    private static MazeManager instance = new MazeManager();
    private MazeManager (){

    }

    public static MazeManager Instance(){
        return instance;
    }

    public void writesomething () {
        System.out.println("Something");
    }
}
