package DataClasses;

public class Cell {
    public Cell(int index, int gridx, int gridy, WallType northernwall, WallType southernwall, WallType westernwall, WallType easternwall, boolean start, boolean finish) {
        Index = index;
        GridX = gridx;
        GridY = gridy;

        Northernwall = northernwall;
        Southernwall = southernwall;
        Westernwall = westernwall;
        Easternwall = easternwall;
        Start = start;
        Finish = finish;
        Vistited = false;
    }

    private int Id;

    public int MazeId;


    public int Index;
    int GridX;
    int GridY;

    private int PosX;
    private int PosY;

    private WallType Northernwall;
    private WallType Southernwall;
    private WallType Westernwall;
    private WallType Easternwall;

    private boolean Start;
    private boolean Finish;

    private boolean Vistited;

    public boolean IsVistited()
    {
        return Vistited;
    }

    public void IsVisiting()
    {
        Vistited = true;
    }

    public int GetPosX()
    {
        return PosX;
    }

    public int GetPosY() {
        return PosY;
    }

    public void SetPosX(int x)
    {
        this.PosX = x;
    }

    public void SetPosY(int y)
    {
        this.PosY = y;
    }

    public int GetGridX()
    {
        return GridX;
    }

    public int GetGridY()
    {
        return GridY;
    }

    public WallType IsNorthernwall()
    {
        return Northernwall;
    }

    public void SetNorthernwall(WallType northernwall)
    {
        Northernwall = northernwall;
    }

    public WallType IsSouthernwall()
    {
        return Southernwall;
    }

    public void SetSouthernwall(WallType southernwall)
    {
        Southernwall = southernwall;
    }

    public WallType IsWesternwall()
    {
        return Westernwall;
    }

    public void SetWesternwall(WallType westernwall)
    {
        Westernwall = westernwall;
    }

    public WallType IsEasternwall()
    {
        return Easternwall;
    }

    public void SetEasternwall(WallType easternwall)
    {
        Easternwall = easternwall;
    }

    public boolean IsStart()
    {
        return Start;
    }

    public void SetStart(boolean start)
    {
        Start = start;
    }

    public boolean IsFinish()
    {
        return Finish;
    }

    public void SetFinish(boolean finish)
    {
        Finish = finish;
    }

    public WallType SwitchWallType(WallType wallType)
    {
        if (wallType == wallType.Wall)
            return wallType.Empty;
        if(wallType == wallType.Empty)
            return wallType.Wall;


        return null;// shouldn't even get here...
    }


}
