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

    public MazeDescriptions(int TotolDescriptions)
    {
        Mazeid = new int[TotolDescriptions];
        Description = new String[TotolDescriptions];
        Author = new String[TotolDescriptions];
        Creation = new LocalDateTime[TotolDescriptions];
        LastEdited = new LocalDateTime[TotolDescriptions];
        Count = TotolDescriptions;
    }

    public int GetCount() {return Count;}

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
