package DataClasses;

import DataClasses.Cell;

import java.util.*;

public class Maze {

    //TODO Grid is currently inverted need to fix.
    private int Length;

    public int getLength() {
        return Length;
    }

    private int Height;

    public int getHeight() {
        return Height;
    }

    int Size;
    boolean Editable = true;
    private ArrayList<Cell> Grid = new ArrayList<>();

    public ArrayList<Cell> getGrid() {
        return Grid;
    }

    public Maze(int length, int height) {
        Length = length;
        Height = height;
        Size = length*height;
        int l = length;
        while (l > 0){
            int h=height;
            while (h > 0){
                Grid.add(new Cell(l-1,h-1,true,true,true,true,false,false));
                h-=1;
            }
            l-=1;
        }
    }


    public void GridInfo(){
        for (Cell cells:Grid){
            System.out.println("This DataClasses.Cell's Position is "+cells.getGridX()+","+cells.getGridY());
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

