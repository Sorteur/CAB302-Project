package DataModules;

import DataClasses.Cell;
import DataClasses.Maze;
import DataClasses.WallType;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MazeModule extends DataModule{
    public MazeModule(DBConnection connection) {
        super(connection);
    }

    public int GetMazeIDFromDescription(String description) {
        return 0;
    }

    public Maze GetMazeFromID (int ID) {
        return null; //temp
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

    private void DoSaveMaze (Maze maze) throws SQLException
    {

        int id = GetNextSequence("MazeId");
        String description = maze.Description;
        int length = maze.getLength();
        int height = maze.getHeight();

        String sql = "INSERT INTO Maze(Id, Deleted, Description, XDimension, YDimension) " + "VALUES (?, false, ?, ?, ? )";
        PreparedStatement statement = PrepareStatement(sql);

        statement.setInt(1, id);
        statement.setString(2, description);
        statement.setInt(3, length);
        statement.setInt(4, height);




        int rowsInserted = statement.executeUpdate();

        InsertCells(id, maze);
        System.out.println("Rows inserted: " + rowsInserted);
    }


    private void InsertCells(int id, Maze maze) throws SQLException
    {
        for (Cell cell : maze.getGrid()) {
            DoInsertCell(id, cell);
        }
    }

    private void DoInsertCell(int mazeid, Cell cell) throws SQLException
    {
        int id = GetNextSequence("CellId");

        int arraylistpos = cell.Index;
        int xposistion = cell.GetPosX();
        int yposistion = cell.GetPosY();
        int NorthernWallTypeId = WallType.Tools.ToId(cell.IsNorthernwall());
        int EasternWallTypeId = WallType.Tools.ToId(cell.IsEasternwall());
        int SouthernWallTypeId = WallType.Tools.ToId(cell.IsSouthernwall());
        int WesternWallTypeId = WallType.Tools.ToId(cell.IsWesternwall());



    }

    public void UpdateMaze (Maze maze) {

    }

    public void DeleteMaze (int ID) {

    }
}
