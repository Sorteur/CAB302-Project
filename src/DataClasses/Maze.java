package DataClasses;

import java.util.ArrayList;

public class Maze {

    private final int Length;
    public int getLength() {
        return Length;
    }

    private final int Height;
    public int getHeight() {
        return Height;
    }

    //public boolean Editable;

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
        //Editable = true;
        int id = 0;
        int l = length;
        while (l > 0){
            int h=height;
            while (h > 0){
                Grid.add(new Cell(id,l-1,h-1,true,true,true,true,false,false));
                id++;
                h-=1;
            }
            l-=1;
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

