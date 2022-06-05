package DataClasses;

import java.awt.*;
import java.util.ArrayList;

public class Maze {
    /*Property's*/
    private String Description;
    private int Id;
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
    public void SetId(int id) {
        Id = id;
        for (Cell cell : getGrid()) {
            cell.MazeId = id;
        }
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
    public void setLogo(Image logo) {
        Logo.SetImage(logo);
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

    public MazeImageResource getEntryImage() {
        return EntryImage;
    }

    public void ConstructExitImage(Image image, int positionX, int posistionY)
    {
        ExitImage = new MazeImageResource(image, positionX, posistionY);
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
}

