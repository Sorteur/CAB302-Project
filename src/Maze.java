//import java.awt.*;
import java.util.*;

public class Maze {
    int Length;
    int Height;
    int Size;

    public Maze(int length, int height) {
        Length = length;
        Height = height;
        Size = length*height;
        int l = length;
        List Cells = new ArrayList<Cell>();
        while (l > 0){
            int h=height;
            while (h > 0){
                Cells.add(new Cell(l,h,true,true,true,true,false,false));
                System.out.println("New Cell Created with coords"+l+h);
                h-=1;
            }
            l-=1;
        }
        //new Cell(length,height,true,true,true,true,false,false);
    }


}

