package DataClasses;

import java.awt.*;
import java.util.ArrayList;

public class Maze {

    public String Description;

    private final int Length;
    public int getLength() {
        return Length;
    }

    private final int Height;
    public int getHeight() {
        return Height;
    }

    //public boolean Editable;

    public Image getLogo() {
        return Logo;
    }

    public void setLogo(Image logo) {
        Logo = logo;
    }



    public int getLogoX() {
        return LogoX;
    }

    public void setLogoX(int logoX) {
        LogoX = logoX;
    }

    public int getLogoY() {
        return LogoY;
    }

    public void setLogoY(int logoY) {
        LogoY = logoY;
    }

    public boolean isImgSrtEnd() {
        return ImgSrtEnd;
    }

    public void setImgSrtEnd(boolean imgSrtEnd) {
        ImgSrtEnd = imgSrtEnd;
    }

    private Image Logo;
    private int LogoX;
    private int LogoY;
    private boolean ImgSrtEnd;

    private final ArrayList<Cell> Grid = new ArrayList<>();
    public ArrayList<Cell> getGrid() {
        return Grid;
    }

    public Cell getCell(int index) {
        return  Grid.get(index);
    }

    public void setCell(int index, Cell cell) {
        Grid.set(index, cell);
    }

    //TODO exception handling for arrays that are out of bounds
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





    public Maze(int length, int height) {
        Length = length;
        Height = height;
        int id = 0;
        int l = 0;
        while (l < Length){
            int h=0;

            while (h < Height){
                Grid.add(new Cell(id,l,h,true,true,true,true,false,false));
                id++;
                h++;

            }
            l++;
        }
    }

    public Cell Search(int xPos,int yPos){
        for (Cell cells:Grid){
            if (xPos == cells.getGridX()){
                if (yPos == cells.getGridY()){
                    return cells;
                }
            }
        }
        return null;
    }

}

