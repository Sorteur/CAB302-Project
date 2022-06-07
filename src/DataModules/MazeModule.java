package DataModules;

import DataClasses.*;
import Engine.MazeManager;
import UserInterface.LogoPlacer;
import UserInterface.SrtEndPlacer;

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        maze = PopulateMazeImages(id, maze);
        return maze;
    }
    private Maze PopulateMazeImages(int mazeid ,Maze maze) throws SQLException {
        String sql = "SELECT Id, ImageTypeId, Image, PositionX, PositionY, GridScaleX, GridScaleY " + "FROM MazeImageResource " + "WHERE MazeId = ?;";

        PreparedStatement statement = PrepareStatement(sql);

        statement.setInt(1, mazeid);

        ResultSet resultSet= statement.executeQuery();
        try {
            while (resultSet.next()) {

                int id = resultSet.getInt(1);
                int imageTypeId = resultSet.getInt(2);
                Image image = MazeImageResource.GetBlobAsImage(resultSet.getBlob(3));
                int posistionX = resultSet.getInt(4);
                int posistionY= resultSet.getInt(5);
                int gridScaleX= resultSet.getInt(6);
                int gridScaleY= resultSet.getInt(7);

                if(imageTypeId == 2)
                {
                    maze.SetLogo(new MazeImageResource(id, image, posistionX, posistionY, gridScaleX, gridScaleY));
                }

                if(imageTypeId == 4)
                {
                    maze.SetEntryImage(new MazeImageResource(id, image, posistionX, posistionY, gridScaleX, gridScaleY));
                }

                if(imageTypeId == 6)
                {
                    maze.SetExitImage(new MazeImageResource(id, image, posistionX, posistionY, gridScaleX, gridScaleY));
                }

                if(maze.getExitImage()!= null)
                {
                    maze.setImgSrtEnd(true);
                    MazeManager.Instance().pnlMaze.add(new SrtEndPlacer());
                }

                if(imageTypeId == 2)
                {
                    MazeManager.Instance().pnlMaze.add(new LogoPlacer(),BorderLayout.CENTER);
                }

            }
            return maze;
        }
        finally {
            statement.close();
        }
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
        if(maze.GetId() == 0){
            InsertMaze(maze);

            for (Cell cell : maze.getGrid())
            {
                InsertCell(cell);
            }

            InsertImages(maze);
        }
        else{
            UpdateMaze(maze);

            for (Cell cell : maze.getGrid())
            {
                UpdateCell(cell);
            }

            UpdateImages(maze);
        }

    }

    private void InsertMaze (Maze maze) throws SQLException
    {

        int id = GetNextSequence("MazeId");
        String description = maze.GetDescription();
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

    //Should consider making a ImageModule...
    private void InsertImages(Maze maze) throws SQLException
    {
        String sql;
        int id;
        int mazeId = maze.GetId();
        int imagetypeid;
        int positionx;
        int positiony;
        int gridscalex;
        int gridscaley;

        PreparedStatement statement;

       if(maze.getLogo()!=null)
        {
            sql = "INSERT INTO MazeImageResource(Id, MazeId, ImageTypeId, Image, PositionX, PositionY, GridScaleX, GridScaleY) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            id = GetNextSequence("MazeImageResourceId");
            imagetypeid = 2;

            positionx =  maze.getLogo().GetPositionX();
            positiony =  maze.getLogo().GetPositionY();
            gridscalex =  maze.getLogo().GetGridScaleX();
            gridscaley =  maze.getLogo().GetGridScaleY();

            statement = PrepareStatement(sql);

            statement.setInt(1, id);
            statement.setInt(2, mazeId);
            statement.setInt(3, imagetypeid);
            statement.setBlob(4, maze.getLogo().GetImageAsBlob());
            statement.setInt(5, positionx);
            statement.setInt(6, positiony);
            statement.setInt(7, gridscalex);
            statement.setInt(8, gridscaley);

            try{
                statement.executeUpdate();
            }
            finally {
                statement.close();
            }
        }
        if(maze.getEntryImage()!=null) {
            sql = "INSERT INTO MazeImageResource(Id, MazeId, ImageTypeId, Image, PositionX, PositionY, GridScaleX, GridScaleY) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            id = GetNextSequence("MazeImageResourceId");
            imagetypeid = 4;

            positionx = maze.getEntryImage().GetPositionX();
            positiony = maze.getEntryImage().GetPositionY();
            gridscalex = maze.getEntryImage().GetGridScaleX();
            gridscaley = maze.getEntryImage().GetGridScaleY();

            statement = PrepareStatement(sql);

            statement.setInt(1, id);
            statement.setInt(2, mazeId);
            statement.setInt(3, imagetypeid);
            statement.setBlob(4, maze.getEntryImage().GetImageAsBlob());
            statement.setInt(5, positionx);
            statement.setInt(6, positiony);
            statement.setInt(7, gridscalex);
            statement.setInt(8, gridscaley);

            try {
                statement.executeUpdate();
            } finally {
                statement.close();
            }
        }
        if(maze.getExitImage()!=null)
        {
            sql = "INSERT INTO MazeImageResource(Id, MazeId, ImageTypeId, Image, PositionX, PositionY, GridScaleX, GridScaleY) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            id = GetNextSequence("MazeImageResourceId");
            imagetypeid = 6;

            positionx =  maze.getExitImage().GetPositionX();
            positiony =  maze.getExitImage().GetPositionY();
            gridscalex =  maze.getExitImage().GetGridScaleX();
            gridscaley =  maze.getExitImage().GetGridScaleY();

            statement = PrepareStatement(sql);

            statement.setInt(1, id);
            statement.setInt(2, mazeId);
            statement.setInt(3, imagetypeid);
            statement.setBlob(4, maze.getExitImage().GetImageAsBlob());
            statement.setInt(5, positionx);
            statement.setInt(6, positiony);
            statement.setInt(7, gridscalex);
            statement.setInt(8, gridscaley);

            try{
                statement.executeUpdate();
            }
            finally {
                statement.close();
            }
        }
    }


    private void UpdateMaze (Maze maze) throws SQLException {
        int id = maze.GetId();
        String description = maze.GetDescription();

        String sql = "UPDATE Maze " + "SET description = ? " + "WHERE Id = ?";
        PreparedStatement statement = PrepareStatement(sql);

        statement.setString(1, description);
        statement.setInt(2, id);

        statement.executeUpdate();
    }

    private void UpdateCell (Cell cell) throws SQLException {

        int id = cell.Id;
        int mazeid = cell.MazeId;
        int arraylistpos = cell.Index;
        int xposistion = cell.GetGridX();
        int yposistion = cell.GetGridY();
        int NorthernWallTypeId = WallType.Tools.ToId(cell.IsNorthernwall());
        int EasternWallTypeId = WallType.Tools.ToId(cell.IsEasternwall());
        int SouthernWallTypeId = WallType.Tools.ToId(cell.IsSouthernwall());
        int WesternWallTypeId = WallType.Tools.ToId(cell.IsWesternwall());

        String sql = "UPDATE Cell " + "SET MazeId = ?, ArrayListPos = ?, XPosistion = ?, YPosistion = ?, NorthWallTypeId = ?, EastWallTypeId = ?, SouthWallTypeId = ?, WestWallTypeId = ? " + "WHERE Id = ?";
        PreparedStatement statement = PrepareStatement(sql);

        statement.setInt(1, mazeid);
        statement.setInt(2,arraylistpos);
        statement.setInt(3,xposistion);
        statement.setInt(4,yposistion);
        statement.setInt(5,NorthernWallTypeId);
        statement.setInt(6,EasternWallTypeId);
        statement.setInt(7,SouthernWallTypeId);
        statement.setInt(8,WesternWallTypeId);
        statement.setInt(9, id);

        statement.executeUpdate();
    }


    private int GetLogoIdByMazeId(int mazeId) throws SQLException{

        String sql = "SELECT Id " + "FROM MazeImageResource " + "WHERE MazeId = ? AND ImageTypeId = 2;";

        PreparedStatement statement = PrepareStatement(sql);

        statement.setInt(1, mazeId);

        ResultSet resultSet= statement.executeQuery();
        try {
            while (resultSet.next()) {

                int id = resultSet.getInt(1);
                return id;
            }
        }
        finally {
            statement.close();
        }
        return 0;
    }


    private void UpdateImages(Maze maze) throws SQLException {
        String sql;
        int id;
        int mazeId = maze.GetId();
        int imagetypeid;
        int positionx;
        int positiony;
        int gridscalex;
        int gridscaley;

        PreparedStatement statement;

        if (maze.getLogo() != null) {
            sql = "UPDATE MazeImageResource " + "SET ImageTypeId = ?, Image = ?, PositionX = ?, PositionY = ?, GridScaleX = ?, GridScaleY = ? " + "WHERE Id = ?";

            id = GetLogoIdByMazeId(maze.GetId());

            if(id == 0)
            {
                sql = "INSERT INTO MazeImageResource(Id, MazeId, ImageTypeId, Image, PositionX, PositionY, GridScaleX, GridScaleY) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                id = GetNextSequence("MazeImageResourceId");
                imagetypeid = 2;

                positionx =  maze.getLogo().GetPositionX();
                positiony =  maze.getLogo().GetPositionY();
                gridscalex =  maze.getLogo().GetGridScaleX();
                gridscaley =  maze.getLogo().GetGridScaleY();

                statement = PrepareStatement(sql);

                statement.setInt(1, id);
                statement.setInt(2, mazeId);
                statement.setInt(3, imagetypeid);
                statement.setBlob(4, maze.getLogo().GetImageAsBlob());
                statement.setInt(5, positionx);
                statement.setInt(6, positiony);
                statement.setInt(7, gridscalex);
                statement.setInt(8, gridscaley);

                try {
                    statement.executeUpdate();
                } finally {
                    statement.close();
                }
            }
            else
            {
                imagetypeid = 2;

                positionx = maze.getLogo().GetPositionX();
                positiony = maze.getLogo().GetPositionY();
                gridscalex = maze.getLogo().GetGridScaleX();
                gridscaley = maze.getLogo().GetGridScaleY();

                statement = PrepareStatement(sql);


                statement.setInt(1, imagetypeid);
                statement.setBlob(2, maze.getLogo().GetImageAsBlob());
                statement.setInt(3, positionx);
                statement.setInt(4, positiony);
                statement.setInt(5, gridscalex);
                statement.setInt(6, gridscaley);

                statement.setInt(7, id);

                try {
                    statement.executeUpdate();
                } finally {
                    statement.close();
                }
            }

        } else {

        }

    }

    public void DeleteMaze (int ID) {

    }

}
