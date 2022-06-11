package DataClasses;

/**
 * The class that stores information pertaining to cells within a maze.
 */
public class Cell {
    /**
     * Creates a new cell with the specified paramaters
     * @param index the cells position within the maze
     * @param gridx The X position of the cell in the grid
     * @param gridy The Y position of the cell in the grid
     * @param northernwall The state of the northern wall of the cell
     * @param southernwall The state of the southern wall of the cell
     * @param westernwall The state of the western wall of the cell
     * @param easternwall The state of the eastern wall of the cell
     *
     * @see Maze
     */
    public Cell(int index, int gridx, int gridy, WallType northernwall, WallType southernwall, WallType westernwall, WallType easternwall) {
        Index = index;
        GridX = gridx;
        GridY = gridy;

        Northernwall = northernwall;
        Southernwall = southernwall;
        Westernwall = westernwall;
        Easternwall = easternwall;

        Vistited = false;
    }

    public int Id;
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
    private boolean Vistited;

    public boolean Searched = false;
    public boolean PermaSearched = false;

    /**
     * Gets if the cell has been visited by the search algorithm of not
     * @return boolean, True if visited, False if not.
     */
    public boolean IsVistited() {return Vistited;}

    /**
     * Sets cell to visited.
     */
    public void IsVisiting(){Vistited = true;}

    /**
     * Gets the X position of the cell in relation to the display panel
     * @return Integer X position on pnlMaze.
     */
    public int GetPosX() {return PosX;}

    /**
     * Gets the Y position of the cell in relation to the display panel
     * @return Integer Y position on pnlMaze.
     */
    public int GetPosY() {return PosY;}

    /**
     * Sets the X position of the cell on pnlMaze.
     * @param x X-Position for the cell to be set to
     */
    public void SetPosX(int x) {this.PosX = x;}
    /**
     * Sets the Y position of the cell on pnlMaze.
     * @param y Y-Position for the cell to be set to
     */
    public void SetPosY(int y) {this.PosY = y;}

    /**
     * gets the grid X position of the cell
     * @return Integer x position of cell
     */
    public int GetGridX() {return GridX;}

    /**
     * gets the grid Y position of the cell
     * @return Integer Y position of cell
     */
    public int GetGridY() {return GridY;}

    /**
     * Gets the current wall state of the Northern wall
     * @return WallType of Northernwall
     * @see WallType
     */
    public WallType IsNorthernwall() {return Northernwall;}

    /**
     * Sets Northern wall to specified wall type
     * @param northernwall State for northern wall to be set to
     * @see WallType
     */
    public void SetNorthernwall(WallType northernwall) {Northernwall = northernwall;}

    /**
     * Gets the current wall state of the Southern wall
     * @return WallType of Southernwall
     *@see WallType
     */
    public WallType IsSouthernwall() {return Southernwall;}

    /**
     * Sets Southern wall to specified wall type
     * @param southernwall State for southern wall to be set to
     *@see WallType
     */
    public void SetSouthernwall(WallType southernwall) {Southernwall = southernwall;}

    /**
     * Gets the current wall state of the Western wall
     * @return WallType of Westernwall
     * @see WallType
     */
    public WallType IsWesternwall() {return Westernwall;}

    /**
     * Sets Western wall to specified wall type
     * @param westernwall State for Western wall to be set to
     * @see WallType
     */
    public void SetWesternwall(WallType westernwall) {Westernwall = westernwall;}

    /**
     * Gets the current wall state of the Eastern wall
     * @return WallType of Easternwall
     * @see WallType
     */
    public WallType IsEasternwall() {return Easternwall;}

    /**
     * Sets Eastern wall to specified wall type
     * @param easternwall State for Eastern wall to be set to
     * @see WallType
     */
    public void SetEasternwall(WallType easternwall) {Easternwall = easternwall;}



}
