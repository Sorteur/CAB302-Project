package DataClasses;

import java.time.LocalDateTime;
import java.util.prefs.PreferenceChangeListener;

public class MazeDescriptions {
    private int [] Mazeid;
    private String[] Description;
    private String[] Author;
    private LocalDateTime[] Creation;
    private LocalDateTime[] LastEdited;
    private int Count;

    /**
     * Holds Information about many Mazes
     * Includes a Maze's Id, Description, Author, Creation Date, Last Edited Date.
     * @param  TotalDescriptions  An int for how many descriptions there will be
     * @see Maze
     */
    public MazeDescriptions(int TotalDescriptions)
    {
        Mazeid = new int[TotalDescriptions];
        Description = new String[TotalDescriptions];
        Author = new String[TotalDescriptions];
        Creation = new LocalDateTime[TotalDescriptions];
        LastEdited = new LocalDateTime[TotalDescriptions];
        Count = TotalDescriptions;
    }

    /**
     * Inserts information about a specific maze.
     * @param  index  an int as an absolute position of where the following elements should sit in their respective arrays
     * @param  mazeId an int for an id representing this Maze
     * @param  description a String for either a title/name of the Maze
     * @param  author a String for the name of the author of this Maze
     * @param  creation a LocalDateTime for the date this Maze was created
     * @param  lastEdited a LocalDateTime for the date this Maze was last edited
     * @see MazeDescriptions
     * @see Maze
     */

    public void InsertDescription(int index, int mazeId, String description, String author, LocalDateTime creation, LocalDateTime lastEdited){
        if (!(index < 0 || index > Count))
        {
            Mazeid[index] = mazeId;
            Description[index] = description;
            Author[index] = author;
            Creation[index] = creation;
            LastEdited[index] = lastEdited;
        }
    }
    /**
     * Returns Information about movie descriptions that have been
     * added, this includes: Id, Description, Author, CreationDate, and LastEditedDate.
     *
     * @return an ArrayArray of Strings containing information about
     * every Insertion in this MazeDescriptions
     * @see MazeDescriptions
     * @see Maze
     */
    public String[][] ToStringArray(){
        String[][] result = new String[Count][5];
        for(int i = 0; i < Count; i++)
        {
            result[i][0]= ""+Mazeid[i];
            result[i][1]= Description[i];
            result[i][2]= Author[i];
            result[i][3]= Creation[i].toString();
            result[i][4]= LastEdited[i].toString();
        }
        return result;
    }
}
