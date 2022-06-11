package Engine;

import DataClasses.Cell;
import DataClasses.Maze;
import DataClasses.WallType;

import java.security.spec.ECField;
import java.util.Arrays;
import java.util.Queue;
import java.util.Random;

public class MazeSolver {

    private static MazeSolver instance = new MazeSolver();
    public static MazeSolver Instance(){
        return instance;
    }

    Queue queue;

    MazeSolver() {

    }
    Maze CurrentMaze;

    public void SolveMaze(Maze maze) throws Exception {
        CurrentMaze = maze;
        DepthFirstSearch();
    }


    private void DepthFirstSearch ()throws Exception{
        Cell currentcell = CurrentMaze.Search(0,0); // Start with the First Cell E.G. The start of the Maze
        Cell endcell = CurrentMaze.Search(CurrentMaze.getLength()-1, CurrentMaze.getHeight()-1); // Get the end of the Maze... when our current cell is the end cell we have found the end.

        DepthFirstSearch(currentcell, endcell);
        if(!CurrentMaze.GetSolved())
        {
            Exception Exception = new Exception("Unsolvable Maze!");
            CurrentMaze.setSolved(false);
            throw Exception;
        }
    }


    private void DepthFirstSearch(Cell currentcell, Cell endcell) throws Exception {

        if(currentcell == endcell)
            CurrentMaze.setSolved(true);



        Cell unvisitedneighbor = null;

            do {

                unvisitedneighbor = chooseViableNeihgbor(currentcell);
                if (unvisitedneighbor == endcell) {
                    CurrentMaze.setSolved(true);
                    endcell.Searched=true;
                    break;
                }
                if (!(unvisitedneighbor == null)) {
                    unvisitedneighbor.Searched =true;
                    unvisitedneighbor.PermaSearched=true;
                    DepthFirstSearch(unvisitedneighbor, endcell);
                }
            } while (unvisitedneighbor != null && !CurrentMaze.GetSolved());

            if(unvisitedneighbor == null )
            {
                currentcell.Searched=false;
            }
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

        for(int side: checkorder)
        {
            if(checkorder[side] == 0)
            {
                viableneihgbor = CurrentMaze.PathFindNorthCell(currentcell.Index);
                if (viableneihgbor != null){
                    if(!viableneihgbor.PermaSearched) {

                        return viableneihgbor;
                    }
                }
            }
            else if(checkorder[side] == 1)
            {
                viableneihgbor = CurrentMaze.PathFindEastCell(currentcell.Index);
                if (viableneihgbor != null){
                    if(!viableneihgbor.PermaSearched) {

                        return viableneihgbor;
                    }
                }
            }
            else if(checkorder[side] == 2) {
                viableneihgbor = CurrentMaze.PathFindSouthCell(currentcell.Index);
                if (viableneihgbor != null){
                    if(!viableneihgbor.PermaSearched) {

                        return viableneihgbor;
                    }
                }
            }
            else if(checkorder[side] == 3)
            {
                viableneihgbor = CurrentMaze.PathFindWestCell(currentcell.Index);
                if (viableneihgbor != null){
                    if(!viableneihgbor.PermaSearched){

                        return viableneihgbor;
                    }
                }
            }
        }
        return null;
    }

}
