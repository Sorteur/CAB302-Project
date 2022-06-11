package DataClasses;
/**
 * An enum containing the two states a Cell wall can be
 * @see Cell
 */
public enum WallType {
    Empty,
    Wall;


public static class Tools
{
    /**
     * Converts an int Id into a WallType, used to translate a WallType Id
     * stored in the database into an enum WallType.
     *
     * @param  Id  An int from the database representing a WallType
     * @return WallType corresponding to the int put in
     * @see WallType
     */
    public static WallType IdToWallType(int Id)
    {
        switch (Id)
        {
            case 1: return Empty;
            case 2:  return Wall;
            default: return Wall;
        }
    }
    /**
     * Converts a WallType, into an int Id used to store a WallType
     * in the database.
     *
     * @param  wallType  An WallType representing a Cell WallType
     * @return int An Id to be stored in the database for a WallType
     * @see WallType
     * @see Cell
     */
     public static int ToId(WallType wallType)
    {
        switch (wallType)
        {
            case Empty: return 1;
            case Wall:  return 2;
            default: return 0;
        }
    }
    /**
     * Converts a WallType, into a boolean value
     *
     * @param  wallType  An WallType representing a Cell WallType
     * @return boolean a true or false value representing the WallType
     * @see WallType
     * @see Cell
     */
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