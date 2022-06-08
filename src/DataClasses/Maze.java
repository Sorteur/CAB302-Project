package DataClasses;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

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
     * Given two integers representing the length and height of the maze in Cells,
     * will create a maze with the length and height as given.
     *
     * @param length Length of the desired maze.
     * @param height Height of the desired maze.
     *
     * @
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
    public void SetDescription(String description) {
        Description = description;
    }
    public String GetDescription(){
        return Description;
    }

    public void SetAuthor(String author) { Author = author;}
    public String GetAuthor() {return Author;}
    public LocalDateTime GetCreation(){return Creation;}
    public void SetLastEdited(LocalDateTime lastEdited) {LastEdited =lastEdited;}
    public LocalDateTime GetLastEdited(){return LastEdited;}
    public void SetId(int id) {
        Id = id;
        for (Cell cell : getGrid()) {
            cell.MazeId = id;
        }
    }

    public int GetId()
    {
        return Id;
    }

    public int getLength() {
        return Length;
    }

    public int getHeight() {
        return Height;
    }


    public MazeImageResource getLogo() {
        return Logo;
    }
    public void SetLogo(MazeImageResource logo) {
        Logo = logo;
    }


    public boolean isImgSrtEnd() {
        return ImgSrtEnd;
    }
    public void setImgSrtEnd(boolean imgSrtEnd) {
        ImgSrtEnd = imgSrtEnd;
    }

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

    public boolean GetSolved() {return Solved;}



    public void SetEntryImage(MazeImageResource entryImage){
        EntryImage = entryImage;
    }
    public MazeImageResource getEntryImage() {
        return EntryImage;
    }


    public void SetExitImage(MazeImageResource exitImage){
        ExitImage = exitImage;
    }
    public MazeImageResource getExitImage() {
        return ExitImage;
    }



    public ArrayList<Cell> getGrid() {
        return Grid;
    }

    public Cell getCell(int index) {
        if (index < 0 ){
            return Grid.get(Grid.size()+index);
        }
        return  Grid.get(index);
    }

    public void SetCell(int index, Cell cell) {
        Grid.set(index, cell);
    }

    public Cell checkNorthCell(int index)
    {
        if (index % Height == 0) return null;
        int northcellindex = index - 1 ;
        return getCell(northcellindex);
    }
    public Cell checkEastCell(int index)
    {
        int eastcellindex = index + Height;

        if(eastcellindex >= (Length*Height)) return null;

        return getCell(eastcellindex);
    }
    public Cell checkSouthCell(int index)
    {
        if (index % Height == Height - 1) return null;

        int southcellindex = index + 1 ;

        return getCell(southcellindex);


    }
    public Cell checkWestCell(int index)
    {
        int westcellindex = index - Height;
        if(westcellindex < 0) return null;
        return getCell(westcellindex);

    }


    public Cell PathFindNorthCell(int index)
    {
        if (index % Height == 0) return null;
        if(getCell(index).IsNorthernwall().equals(WallType.Wall))
            return null;
        int northcellindex = index - 1 ;
        return getCell(northcellindex);
    }

    public Cell PathFindEastCell(int index)
    {
        int eastcellindex = index + Height;
        if(getCell(index).IsEasternwall().equals(WallType.Wall))
            return null;

        if(eastcellindex >= (Length*Height)) return null;
        return getCell(eastcellindex);

        //return !getCell(eastcellindex).isVistited();
    }
    public Cell PathFindSouthCell(int index)
    {
        if (index % Height == Height - 1) return null;

        if(getCell(index).IsSouthernwall().equals(WallType.Wall))
            return null;

        int southcellindex = index + 1 ;

        return getCell(southcellindex);

    }

    public Cell PathFindWestCell(int index)
    {
        int westcellindex = index - Height;

        if(getCell(index).IsWesternwall().equals(WallType.Wall))
            return null;

        if(westcellindex < 0) return null;

        return getCell(westcellindex);
    }


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


    public boolean ViableImageLogo(int xrange, int yrange, Cell cell) {
        int InvalidStartingCellCoordinateX = (Length/3)-1; //Less then this whilst being less then the Y range means your in a possible starting cell
        int InvalidStartingCellCoordinateY = (Height/2)-1;

        int InvalidExitCellCoordinateX = InvalidStartingCellCoordinateX*2;//Greater then this and being greater then the Y range means your in a possible exit cell
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

