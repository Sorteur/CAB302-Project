package DataClasses;

import java.util.ArrayList;

public class Maze {

    private int Length;
    public int getLength() {
        return Length;
    }

    private int Height;
    public int getHeight() {
        return Height;
    }


    boolean Editable = true;

    private ArrayList<Cell> Grid = new ArrayList<>();
    public ArrayList<Cell> getGrid() {
        return Grid;
    }

    public Cell getCell(int index) {

        Cell cell = Grid.get(index);

        return  cell;

    }

    public void setCell(int index, Cell cell) {
        Grid.set(index, cell);
    }



    //TODO exception handling for arrays that are out of bounds
    public boolean checkNorthCell(int index)
    {
        int northcellindex = index - Length;

        if(northcellindex < 0) throw new RuntimeException("Index out of bounds");

        Cell cell = Grid.get(northcellindex);

        return false;
    }

    public boolean checkEastCell(int index)
    {
        int eastcellindex = index + 1;

        if(eastcellindex > (Length*Height)) return false;

        if(getCell(eastcellindex).isVistited()) return false;

        return true;
    }

    public boolean checkSouthCell(int index)
    {
        int southcellindex = index + Length;

        if(southcellindex > (Length*Height)) throw new RuntimeException("Index out of bounds");

        Cell cell = Grid.get(southcellindex);

        return false;
    }
    public boolean checkWestCell(int index)
    {
        int westcellindex = index - 1;

        if(westcellindex < 0) throw new RuntimeException("Index out of bounds");

        Cell cell = Grid.get(westcellindex);

        return false;
    }





    public Maze(int length, int height) {
        Length = length;
        Height = height;
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
                    //System.out.println("Found a cell with that Co-Ordinate");
                    return cells;
                }
            }
        }
        System.out.println("Could not find a cell with that Co-Ordinate");
        return null;
    }

}

