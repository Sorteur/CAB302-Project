package DataClasses;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;

public enum WallType {
    Empty,
    Wall;


public static class Tools
{
    public static WallType IdToWallType(int Id)
    {
        switch (Id)
        {
            case 0: return Empty;
            case 1:  return Wall;
            default: return Wall;
        }
    }
     public static int ToId(WallType wallType)
    {
        switch (wallType)
        {
            case Empty: return 0;
            case Wall:  return 1;
            default: return 0;
        }
    }

    public static boolean ToBool(WallType wallType)
    {
        switch (wallType)
        {
            case Empty: return false;
            case Wall:  return true;
            default: return false;
        }
    }

}
};