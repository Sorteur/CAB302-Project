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
            case 1: return Empty;
            case 2:  return Wall;
            default: return Wall;
        }
    }
     public static int ToId(WallType wallType)
    {
        switch (wallType)
        {
            case Empty: return 1;
            case Wall:  return 2;
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