
import java.util.*;

public class Maze {
    int Length;
    int Height;
    int Size;
    ArrayList<Cell> Grid = new ArrayList<>();
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
            System.out.println("This Cell's Position is "+cells.getPosX()+","+cells.getPosy());
        }

    }

    public Cell Search(int xPos,int yPos){
        for (Cell cells:Grid){
            if (xPos == cells.getPosX()){
                if (yPos == cells.getPosy()){
                    System.out.println("Found a cell with that Co-Ordinate");
                    return cells;
                }
            }
        }
        System.out.println("Could not find a cell with that Co-Ordinate");
        return null;
    }
}

