package DataClasses;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * The class that stores most of the information relating to a maze, as well as storing many useful methods
 */
public class Maze {
    /*Property's*/
    private String Description = "";
    private String Author = "";
    private LocalDateTime Creation;
    private LocalDateTime LastEdited;
    private int Id = 0;
    private final int Length;
    private final int Height;
    private MazeImageResource Logo;
    private MazeImageResource ExitImage;
    private MazeImageResource EntryImage;
    private boolean ImgSrtEnd;
    private boolean Solved = false;
    private final ArrayList<Cell> Grid = new ArrayList<>();

    /**
     * Used for database loading mazes
     * @param length Length of maze
     * @param height Height of maze
     * @param author Author of maze
     * @param creation Creation time of maze
     * @param lastEdited Last Edited time of maze
     */
    public Maze(int length, int height, String author, LocalDateTime creation, LocalDateTime lastEdited) {
        Length = length;
        Height = height;
        Author = author;
        Creation = creation;
        LastEdited = lastEdited;
        int index = 0;
        int l = 0;
        while (l < Length){
            int h=0;
            while (h < Height){
                Grid.add(new Cell(index,l,h,WallType.Wall,WallType.Wall,WallType.Wall,WallType.Wall));
                index++;
                h++;
            }
            l++;
        }
    }


    /**
     * Given two integers representing the length and height of the maze in Cells,
     * will create a maze with the length and height as given and populate with Cells.
     *
     * @param length Length of the desired maze.
     * @param height Height of the desired maze.
     *
     * @see Cell
     */
    public Maze(int length, int height) {
        Length = length;
        Height = height;

        int index = 0;
        int l = 0;
        while (l < Length){
            int h=0;
            while (h < Height){
                Grid.add(new Cell(index,l,h,WallType.Wall,WallType.Wall,WallType.Wall,WallType.Wall));
                index++;
                h++;
            }
            l++;
        }
    }

    /*Methods*/

    /**
     * Sets description of maze to be stored in database
     * @param description A string which will be saved with the maze.
     */
    public void SetDescription(String description) {Description = description;}

    /**
     * Returns description of maze for database use
     * @return String description of maze
     */
    public String GetDescription(){return Description;}

    /**
     * Sets author of maze
     * @param author String to set author of maze to
     */
    public void SetAuthor(String author) { Author = author;}

    /**
     * Get author of maze
     * @return String Author of maze
     */
    public String GetAuthor() {return Author;}

    /**
     * Gets creation time of maze
     * @return LocalDateTime Creation time of maze
     */
    public LocalDateTime GetCreation(){return Creation;}

    /**
     * Sets the last edited time of maze
     * @param lastEdited Time that maze was last edited
     */
    public void SetLastEdited(LocalDateTime lastEdited) {LastEdited =lastEdited;}

    /**
     * Gets the time the maze was last edited
     * @return LocalDateTime Time maze was last edited.
     */
    public LocalDateTime GetLastEdited(){return LastEdited;}

    /**
     * Sets ID of maze and maze's cells, used in database storage.
     * @param id Integer to be assigned to maze as it's ID
     */
    public void SetId(int id) {
        Id = id;
        for (Cell cell : getGrid()) {
            cell.MazeId = id;
        }
    }

    /**
     * Returns ID of maze
     * @return Integer ID of maze
     */
    public int GetId() {return Id;}

    /**
     * Returns length of maze in cells
     * @return Integer length of maze
     */
    public int getLength() {return Length;}

    /**
     * Returns height of maze in cells
     * @return Integer height of maze
     */
    public int getHeight() {return Height;}

    /**
     * Returns Logo of maze
     * @return MazeImageResource Logo of maze
     * @see MazeImageResource
     */
    public MazeImageResource getLogo() {return Logo;}
    /**
     * Sets Logo of maze to be displayed.
     * @param logo Logo to be attributed to maze for display
     * @see MazeImageResource
     */
    public void SetLogo(MazeImageResource logo) {Logo = logo;}

    /**
     * Returns if the maze as start/end images
     * @return boolean true if the maze has start/end images, false if it doesn't
     */
    public boolean isImgSrtEnd() {return ImgSrtEnd;}

    /**
     * Used to set Start/End image attribute of maze on maze load/creation
     * @param imgSrtEnd True or False, depending on whether maze has start/end image or not
     */
    public void setImgSrtEnd(boolean imgSrtEnd) {ImgSrtEnd = imgSrtEnd;}

    /**
     * Used to set whether a maze is displaying solve line or not
     * @param solved boolean - True or False, on True, line is displayed, on False, not displayed
     */
    public void setSolved(boolean solved) {
        if(!solved)
        {
            Solved = solved;
            for (Cell cell : getGrid()) {
                cell.Searched = false;
                cell.PermaSearched = false;
            }
        }
        else {
            Solved = solved;
        }
    }

    /**
     * returns whether maze is solved or not
     * @return boolean - True or False, corresponding to current status of solved line.
     */
    public boolean GetSolved() {return Solved;}


    /**
     * Sets start image of maze
     * @param entryImage MazeResourceImage to set as start image of maze.
     * @see MazeImageResource
     */
    public void SetEntryImage(MazeImageResource entryImage){EntryImage = entryImage;}

    /**
     * Gets the starting image of the maze.
     * @return MazeImageResource - starting image of maze
     * @see MazeImageResource
     */
    public MazeImageResource getEntryImage() {return EntryImage;}

    /**
     * Sets end image of maze
     * @param exitImage MazeResourceImage to set as end image of maze.
     *                  @see MazeImageResource
     */
    public void SetExitImage(MazeImageResource exitImage){ExitImage = exitImage;}
    /**
     * Gets the end image of the maze.
     * @return MazeImageResource - end image of maze
     * @see MazeImageResource
     */
    public MazeImageResource getExitImage() {return ExitImage;}


    /**
     * Gets all cells of maze
     * @return ArrayList - List of all cells in maze in ID order.
     * @see Cell
     */
    public ArrayList<Cell> getGrid() {
        return Grid;
    }

    /**
     * Gets cell of specified index. Index can be negative to search backwards
     * @param index Index of cell that should be returned.
     * @return Cell at specified index.
     * @see Cell
     * @throws NullPointerException If the index is greater than the number of cells in Grid.
     */
    public Cell getCell(int index) throws NullPointerException {
        if (index < 0 ){
            return Grid.get(Grid.size()+index);
        }
        return  Grid.get(index);
    }

    /**
     * Sets cell of specified index to provided cell
     * @param index Index of cell that is being set
     * @param cell Cell that will override old cell.
     * @throws NullPointerException If the index is greater than the number of cells in Grid.
     * @see Cell
     */
    public void SetCell(int index, Cell cell) throws NullPointerException {
        Grid.set(index, cell);
    }

    /**
     * Checks if provided cell has a north neighbour.
     * @param index index of cell to have checked for north cell.
     * @return The cell that is north of cell at index, Null if cell doesn't exist.
     * @throws NullPointerException If the index is greater than the number of cells in Grid.
     * @see Cell
     */
    public Cell checkNorthCell(int index) throws NullPointerException
    {
        if (index % Height == 0) return null;
        int northcellindex = index - 1 ;
        return getCell(northcellindex);
    }
    /**
     * Checks if provided cell has an east neighbour.
     * @param index index of cell to have checked for east cell.
     * @return The cell that is east of cell at index, Null if cell doesn't exist.
     * @throws NullPointerException If the index is greater than the number of cells in Grid.
     * @see Cell
     */
    public Cell checkEastCell(int index) throws NullPointerException
    {
        int eastcellindex = index + Height;

        if(eastcellindex >= (Length*Height)) return null;

        return getCell(eastcellindex);
    }
    /**
     * Checks if provided cell has a south neighbour.
     * @param index index of cell to have checked for south cell.
     * @return The cell that is south of cell at index, Null if cell doesn't exist.
     * @throws NullPointerException If the index is greater than the number of cells in Grid.
     * @see Cell
     */
    public Cell checkSouthCell(int index) throws NullPointerException
    {
        if (index % Height == Height - 1) return null;

        int southcellindex = index + 1 ;

        return getCell(southcellindex);
    }
    /**
     * Checks if provided cell has a west neighbour.
     * @param index index of cell to have checked for west cell.
     * @return The cell that is west of cell at index, Null if cell doesn't exist.
     * @throws NullPointerException If the index is greater than the number of cells in Grid.
     * @see Cell
     */
    public Cell checkWestCell(int index) throws NullPointerException
    {
        int westcellindex = index - Height;
        if(westcellindex < 0) return null;
        return getCell(westcellindex);

    }

    /**
     * Checks to see if provided cell has a north neighbour and its wall is empty.
     * @param index Index of cell to be checked for neighbour and wall.
     * @return Cell that is north of provided cell, Null if cell doesn't have a northern neighbour, Null if cell's Northernwall is not-empty
     * @throws NullPointerException If the index is greater than the number of cells in Grid.
     * @see Cell
     */
    public Cell PathFindNorthCell(int index) throws NullPointerException
    {
        if (index % Height == 0) return null;
        if(getCell(index).IsNorthernwall().equals(WallType.Wall))
            return null;
        int northcellindex = index - 1 ;
        return getCell(northcellindex);
    }

    /**
     * Checks to see if provided cell has an east neighbour and its wall is empty.
     * @param index Index of cell to be checked for neighbour and wall.
     * @return Cell that is east of provided cell, Null if cell doesn't have an eastern neighbour, Null if cell's Easternwall is not-empty
     * @throws NullPointerException If the index is greater than the number of cells in Grid.
     * @see Cell
     */
    public Cell PathFindEastCell(int index) throws NullPointerException
    {
        int eastcellindex = index + Height;
        if(getCell(index).IsEasternwall().equals(WallType.Wall))
            return null;
        if(eastcellindex >= (Length*Height)) return null;
        return getCell(eastcellindex);

    }
    /**
     * Checks to see if provided cell has a South neighbour and its wall is empty.
     * @param index Index of cell to be checked for neighbour and wall.
     * @return Cell that is southern of provided cell, Null if cell doesn't have a southern neighbour, Null if cell's Southernwall is not-empty
     * @throws NullPointerException If the index is greater than the number of cells in Grid.
     * @see Cell
     */
    public Cell PathFindSouthCell(int index) throws NullPointerException
    {
        if (index % Height == Height - 1) return null;
        if(getCell(index).IsSouthernwall().equals(WallType.Wall))
            return null;
        int southcellindex = index + 1 ;
        return getCell(southcellindex);
    }
    /**
     * Checks to see if provided cell has a west neighbour and its wall is empty.
     * @param index Index of cell to be checked for neighbour and wall.
     * @return Cell that is west of provided cell, Null if cell doesn't have a western neighbour, Null if cell's Westernwall is not-empty
     * @throws NullPointerException If the index is greater than the number of cells in Grid.
     * @see Cell
     */
    public Cell PathFindWestCell(int index) throws NullPointerException
    {
        int westcellindex = index - Height;
        if(getCell(index).IsWesternwall().equals(WallType.Wall))
            return null;
        if(westcellindex < 0) return null;
        return getCell(westcellindex);
    }

    /**
     * Searches maze for a cell with specified x,y coordinates.
     * @param xPos The x coordinate for cell to be searched
     * @param yPos The y coordinate for cell to be searched
     * @return Cell with specified x,y coordinates in maze, Null if cell doesn't exist
     * @see Cell
     */
    public Cell Search(int xPos,int yPos){
        for (Cell cells:Grid){
            if (xPos == cells.GetGridX()){
                if (yPos == cells.GetGridY()){
                    return cells;
                }
            }
        }
        return null;
    }

    /**
     * Determines if specified cell is a viable place for a logo or not
     * @param xrange Range that cell logo can't be placed in X direction
     * @param yrange Range that cell logo can't be placed in Y direction
     * @param cell Cell to determine if viable location or not
     * @return True or False, if viable or not
     */
    public boolean ViableImageLogo(int xrange, int yrange, Cell cell) {
        int InvalidStartingCellCoordinateX = (Length/3)-1; //Less than this whilst being less than the Y range means your in a possible starting cell
        int InvalidStartingCellCoordinateY = (Height/2)-1;

        int InvalidExitCellCoordinateX = InvalidStartingCellCoordinateX*2;//Greater than this and being greater than the Y range means your in a possible exit cell
        int InvalidExitCellCoordinateY = InvalidStartingCellCoordinateY;



        int currentcellx = cell.GetGridX();//All-ways in the maze it exits therefor check is it in a valid area
        int currentcelly = cell.GetGridY();

        if (currentcellx <= InvalidStartingCellCoordinateX & currentcelly <= InvalidStartingCellCoordinateY)
            return false;
        if(currentcellx > InvalidExitCellCoordinateX & currentcelly > InvalidExitCellCoordinateY)
            return false;



        int openingcellx = cell.GetGridX()+xrange;//Must check if this cell exists in the maze and is in invalid cell area
        int openingcelly = cell.GetGridY()+yrange;

        if(openingcellx > Length || openingcelly > Height) // does it exist
            return false;
        if (openingcellx <= InvalidStartingCellCoordinateX & currentcelly <= InvalidStartingCellCoordinateY)// does it touch a potental image
            return false;
        if(openingcellx > InvalidExitCellCoordinateX & currentcelly > InvalidExitCellCoordinateY)
            return false;
        return true;
    }

    /**
     * Switches wall type of given wall state
     * @param wallType wallType to be switched.
     * @return wallType.Empty if given wallType.Wall, wallType.Wall if given wallType.Empty
     *
     */
    public WallType SwitchWallType(WallType wallType)
    {
        setSolved(false);// Maze may no longer be solvable since a wall change is happening

        if (wallType == wallType.Wall)
            return wallType.Empty;
        if(wallType == wallType.Empty)
            return wallType.Wall;

        return null;// shouldn't even get here...
    }
}

