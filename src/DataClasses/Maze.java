package DataClasses;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class Maze {
    /*Property's*/
    private String Description = "";
    private int Id = 0;
    private final int Length;
    private final int Height;
    private MazeImageResource Logo;
    private MazeImageResource ExitImage;
    private MazeImageResource EntryImage;
    private boolean ImgSrtEnd;
    private final ArrayList<Cell> Grid = new ArrayList<>();

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

    public void ConstructLogo(Image image, int positionX, int posistionY)
    {
        Logo = new MazeImageResource(image, positionX, posistionY);
    }

    public MazeImageResource getLogo() {
        return Logo;
    }
    public void setLogoImage(Image logo) {
        Logo.SetImage(logo);
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

    public void ConstructEntryImage(Image image, int positionX, int posistionY)
    {
        EntryImage = new MazeImageResource(image, positionX, posistionY);
    }

    public void SetEntryImage(MazeImageResource entryImage){
        EntryImage = entryImage;
    }

    public MazeImageResource getEntryImage() {
        return EntryImage;
    }

    public void ConstructExitImage(Image image, int positionX, int posistionY)
    {
        ExitImage = new MazeImageResource(image, positionX, posistionY);
    }

    public void SetExitImage(MazeImageResource exitImage){
        ExitImage =exitImage;
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

        //return !getCell(northcellindex).isVistited();
    }
    public Cell checkEastCell(int index)
    {
        int eastcellindex = index + Height;

        if(eastcellindex >= (Length*Height)) return null;

        return getCell(eastcellindex);

        //return !getCell(eastcellindex).isVistited();
    }
    public Cell checkSouthCell(int index)
    {
        if (index % Height == Height - 1) return null;

        int southcellindex = index + 1 ;

        return getCell(southcellindex);

        //return !getCell(southcellindex).isVistited();
    }
    public Cell checkWestCell(int index)
    {
        int westcellindex = index - Height;

        if(westcellindex < 0) return null;

        return getCell(westcellindex);
        //return !getCell(westcellindex).isVistited();
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

    public Cell SearchByPos(int xPos,int yPos){
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
}

