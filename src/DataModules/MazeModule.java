package DataModules;

import DataClasses.Cell;
import DataClasses.Maze;
import DataClasses.WallType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

public class MazeModule extends DataModule{
    public MazeModule(DBConnection connection) {
        super(connection);
    }

    public Maze GetMazeFromID(int id) throws SQLException{
        StartTransaction();
        try{
            return DoGetMazeFromId(id);
        }
        finally {
            CommitTransaction();
        }
    }

    private Maze DoGetMazeFromId(int id) throws SQLException {
        Maze maze = DoGetPartialMazeFromId(id);
        maze = PopulateMazeCellsFromMazeId(id, maze);
        return maze;
    }

    private Maze PopulateMazeCellsFromMazeId(int mazeid, Maze maze) throws SQLException {
        String sql = "SELECT Id, ArrayListPos, XPosistion, YPosistion, NorthWallTypeId, EastWallTypeId, SouthWallTypeId, WestWallTypeId " + "FROM Cell " + "WHERE MazeId = ?;";

        PreparedStatement statement = PrepareStatement(sql);

        statement.setInt(1, mazeid);

        ResultSet resultSet= statement.executeQuery();
        try {
            while (resultSet.next()) {

                int Id = resultSet.getInt(1);
                int Index = resultSet.getInt(2);
                int XPosistion = resultSet.getInt(3);
                int YPosisiton = resultSet.getInt(4);
                WallType NorthWallTypeId = WallType.Tools.IdToWallType(resultSet.getInt(5));
                WallType EastWallTypeId = WallType.Tools.IdToWallType(resultSet.getInt(6));
                WallType SouthWallTypeId = WallType.Tools.IdToWallType(resultSet.getInt(7));
                WallType WestWallTypeId = WallType.Tools.IdToWallType(resultSet.getInt(8));

                Cell newCell = new Cell(Index, XPosistion, YPosisiton, NorthWallTypeId, SouthWallTypeId, WestWallTypeId, EastWallTypeId);

                newCell.MazeId = mazeid;
                newCell.Id = Id;

                maze.SetCell(Index,newCell);

            }
            return maze;
        }
        finally {
            statement.close();
        }
    }

    private Maze DoGetPartialMazeFromId (int id) throws SQLException {

        String sql = "SELECT Id, description, XDimension, YDimension " + "FROM Maze " + "WHERE Deleted = false and Id = ?";

        PreparedStatement statement = PrepareStatement(sql);

        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();
        int Id = id;
        String description="Error";
        int xdemension;
        int ydemension;


        try {
            if (resultSet.next())
                description = resultSet.getString(2);
                xdemension = resultSet.getInt(3);
                ydemension = resultSet.getInt(4);
                Maze maze = new Maze(xdemension,ydemension);
                maze.SetDescription(description);
                maze.SetId(id);
                return maze;
        }
        finally {
            resultSet.close();
        }
    }

    public Hashtable GetMazeDescriptions() throws SQLException{
        StartTransaction();
        try{

             return DoGetMazeDescriptions();

        }
        finally {
            CommitTransaction();
        }
    }

    private Hashtable DoGetMazeDescriptions() throws SQLException
    {
        Hashtable descriptions = ReadMazeDescriptions();

        return descriptions;
    }

    private Hashtable ReadMazeDescriptions() throws SQLException
    {
        Hashtable result = new Hashtable<Integer, String>();

        String sql = "SELECT Id, description " + "FROM Maze " + "WHERE Deleted = false;";

        PreparedStatement statement = PrepareStatement(sql);

        ResultSet resultSet = statement.executeQuery();

        try{
            while(resultSet.next())
            {
                result.put(resultSet.getInt(1),resultSet.getString(2));
            }

            return result;
        }
        finally {
            statement.close();
        }
    }

    public void SaveMaze (Maze maze) throws SQLException {

        StartTransaction();
        try{

            DoSaveMaze(maze);

            CommitTransaction();
        }
        catch (SQLException sqle){
            RollbackTransaction();
        }

    }

    private void DoSaveMaze(Maze maze) throws SQLException
    {
        InsertMaze(maze);

        for (Cell cell : maze.getGrid())
        {
            InsertCell(cell);
        }

    }

    private void InsertMaze (Maze maze) throws SQLException
    {

        int id = GetNextSequence("MazeId");
        String description = "fake";
        int length = maze.getLength();
        int height = maze.getHeight();

        String sql = "INSERT INTO Maze(Id, Deleted, Description, XDimension, YDimension) " + "VALUES (?, false, ?, ?, ? )";
        PreparedStatement statement = PrepareStatement(sql);

        statement.setInt(1, id);
        statement.setString(2, description);
        statement.setInt(3, length);
        statement.setInt(4, height);

        statement.executeUpdate();

        maze.SetId(id);
    }

    private void InsertCell(Cell cell) throws SQLException
    {
        int id = GetNextSequence("CellId");
        int mazeid = cell.MazeId;
        int arraylistpos = cell.Index;
        int xposistion = cell.GetGridX();
        int yposistion = cell.GetGridY();
        int NorthernWallTypeId = WallType.Tools.ToId(cell.IsNorthernwall());
        int EasternWallTypeId = WallType.Tools.ToId(cell.IsEasternwall());
        int SouthernWallTypeId = WallType.Tools.ToId(cell.IsSouthernwall());
        int WesternWallTypeId = WallType.Tools.ToId(cell.IsWesternwall());

        String sql = "INSERT INTO Cell(Id, MazeId, ArrayListPos, XPosistion, YPosistion, NorthWallTypeId, EastWallTypeId, SouthWallTypeId, WestWallTypeId) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = PrepareStatement(sql);

        statement.setInt(1, id);
        statement.setInt(2, mazeid);
        statement.setInt(3,arraylistpos);
        statement.setInt(4,xposistion);
        statement.setInt(5,yposistion);
        statement.setInt(6,NorthernWallTypeId);
        statement.setInt(7,EasternWallTypeId);
        statement.setInt(8,SouthernWallTypeId);
        statement.setInt(9,WesternWallTypeId);

        statement.executeUpdate();

    }

    public void UpdateMaze (Maze maze) {

    }

    public void DeleteMaze (int ID) {

    }
}
