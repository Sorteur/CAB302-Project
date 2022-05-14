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

    Stack stk = new Stack();

    private Cell GenerateCellState () {
        return null;
    }

    private Maze GenerateStartCell(Maze maze) {
        return this.maze;
    }


    private Maze GenerateEndCell(Maze maze) {
        return this.maze;
    }

    public Maze GenerateMaze(Maze themaze) {
        Cell randomstart = randomCell(themaze);

        Cell nextcell = chooseViableNeihgbor(randomstart);

        return maze;
    }



    public Cell randomCell (Maze themaze)
    {
        maze = themaze;

        int mazemax = maze.getHeight() * maze.getLength();
        int start = (int)(Math.random() * mazemax);

        //TODO Comment Out
        System.out.println(start);

        return maze.getCell(start);

    }

    private Cell chooseViableNeihgbor (Cell cell) {
        boolean viableneihgbor = false;

        // I used this guide to make this https://www.tutorialspoint.com/how-to-randomize-and-shuffle-array-of-numbers-in-java
        int[] checkorder = {1,2,3,4};
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
            if(side == 0)
            {
                viableneihgbor = maze.checkEastCell(cell.Id);
            }
            else if(side == 1)
            {

            }
            else if(side == 2)
            {

            }
             else if(side == 3)
            {

            }

        }

        return cell;
    }

    private void addcelltoStack(Cell cell){
        stk.add(cell);



    }


}
