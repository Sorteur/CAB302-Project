package Engine;

import DataClasses.Cell;
import DataClasses.Maze;
import DataClasses.WallType;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

public class MazeGenerator {

    private static MazeGenerator instance = new MazeGenerator();
    public static MazeGenerator Instance(){
        return instance;
    }

    MazeGenerator() {

    }
    Maze Maze;


    public Maze GenerateMaze(Maze themaze) {
        Cell randomstart = randomCell(themaze);

        DepthFirstSearch(randomstart);

        return Maze;
    }

    public Cell RandomCellLogo(Maze themaze, int xrange, int yrange) {
        //assuming xrange is 1/3 of the width
        //assuming yrange is 1/2 of the height

        Cell cell = randomCell(themaze);

        boolean viable = themaze.ViableImageLogo(xrange, yrange, cell);

        if(viable)
            return cell;

        cell = RandomCellLogo(themaze, xrange, yrange);
        return cell;
    }

    private void DepthFirstSearch(Cell currentcell){
        currentcell.IsVisiting();
        Cell unvisitedneighbor = null;

         do{
            unvisitedneighbor = chooseViableNeihgbor(currentcell);
            if (!(unvisitedneighbor == null))
            {
                DepthFirstSearch(unvisitedneighbor);
            }
        }while (unvisitedneighbor != null);

    }

    private Cell randomCell (Maze themaze)
    {
        Maze = themaze;

        int mazemax = Maze.getHeight() * Maze.getLength();
        int start = (int)(Math.random() * mazemax);

        return Maze.getCell(start);
    }

    private Cell chooseViableNeihgbor (Cell currentcell) {
        Cell viableneihgbor = null;

        // I used this guide to make this https://www.tutorialspoint.com/how-to-randomize-and-shuffle-array-of-numbers-in-java
        int[] checkorder = {0,1,2,3};
        Random rand = new Random();
        for(int i = 0; i < checkorder.length; ++i) {
            int index = rand.nextInt(checkorder.length - i);
            int tmp = checkorder[checkorder.length - 1 - i];
            checkorder[checkorder.length - 1 - i] = checkorder[index];
            checkorder[index] = tmp;
        }
        System.out.println("Shuffled Array" + Arrays.toString(checkorder));
        int thrid = 1/2;
        System.out.println(thrid);


        for(int side: checkorder)
        {
            if(checkorder[side] == 0)
            {
                viableneihgbor = Maze.checkNorthCell(currentcell.Index);
                if (viableneihgbor != null){
                    if(!viableneihgbor.IsVistited()) {
                        currentcell.SetNorthernwall(WallType.Empty);
                        viableneihgbor.SetSouthernwall(WallType.Empty);

                        return viableneihgbor;
                    }
                }
            }
            else if(checkorder[side] == 1)
            {
                viableneihgbor = Maze.checkEastCell(currentcell.Index);
                if (viableneihgbor != null){
                    if(!viableneihgbor.IsVistited()) {
                        currentcell.SetEasternwall(WallType.Empty);
                        viableneihgbor.SetWesternwall(WallType.Empty);

                        return viableneihgbor;
                    }
                }
            }
            else if(checkorder[side] == 2) {
                viableneihgbor = Maze.checkSouthCell(currentcell.Index);
                if (viableneihgbor != null){
                    if(!viableneihgbor.IsVistited()) {
                        currentcell.SetSouthernwall(WallType.Empty);
                        viableneihgbor.SetNorthernwall(WallType.Empty);

                        return viableneihgbor;
                    }
                }
            }
             else if(checkorder[side] == 3)
            {
                viableneihgbor = Maze.checkWestCell(currentcell.Index);
                if (viableneihgbor != null){
                    if(!viableneihgbor.IsVistited()){
                        currentcell.SetWesternwall(WallType.Empty);
                        viableneihgbor.SetEasternwall(WallType.Empty);

                        return viableneihgbor;
                    }
                }
            }
        }
        return null;
    }

}
