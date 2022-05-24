package Engine;

import DataClasses.Cell;
import DataClasses.Maze;


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
    Maze maze;

    private void GenerateStartCell() {
        this.maze.getCell(0).setStart(true);
    }

    private void GenerateEndCell(){
        this.maze.getCell(this.maze.getGrid().size()-1).setFinish(true);
    }

    public Maze GenerateMaze(Maze themaze) {
        Cell randomstart = randomCell(themaze);

        DepthFirstSearch(randomstart);

        GenerateStartCell();

        GenerateEndCell();

        return maze;
    }

    private void DepthFirstSearch(Cell currentcell){
        currentcell.isVisiting();
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
        maze = themaze;

        int mazemax = maze.getHeight() * maze.getLength();
        int start = (int)(Math.random() * mazemax);

        return maze.getCell(start);
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


        for(int side: checkorder)
        {
            if(checkorder[side] == 0)
            {
                viableneihgbor = maze.checkNorthCell(currentcell.Id);
                if (viableneihgbor != null){
                    if(!viableneihgbor.isVistited()) {
                        currentcell.setNwall(false);
                        viableneihgbor.setSwall(false);

                        return viableneihgbor;
                    }
                }
            }
            else if(checkorder[side] == 1)
            {
                viableneihgbor = maze.checkEastCell(currentcell.Id);
                if (viableneihgbor != null){
                    if(!viableneihgbor.isVistited()) {
                        currentcell.setEwall(false);
                        viableneihgbor.setWwall(false);

                        return viableneihgbor;
                    }
                }
            }
            else if(checkorder[side] == 2) {
                viableneihgbor = maze.checkSouthCell(currentcell.Id);
                if (viableneihgbor != null){
                    if(!viableneihgbor.isVistited()) {
                        currentcell.setSwall(false);
                        viableneihgbor.setNwall(false);

                        return viableneihgbor;
                    }
                }
            }
             else if(checkorder[side] == 3)
            {
                viableneihgbor = maze.checkWestCell(currentcell.Id);
                if (viableneihgbor != null){
                    if(!viableneihgbor.isVistited()){
                        currentcell.setWwall(false);
                        viableneihgbor.setEwall(false);

                        return viableneihgbor;
                    }
                }
            }
        }

        return null;
    }

}
