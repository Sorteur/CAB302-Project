package DataModules;

import DataClasses.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;


public class MazeModule extends DataModule{
    public MazeModule(DBConnection connection) {
        super(connection);
    }

    /**
     * Handles the transaction, returns a maze from the
     * database with the corresponding id.
     *
     * @param  id  An int representing a maze in the database
     * @return Maze from the database
     * @throws SQLException
     * @see Maze
     */
    public Maze GetMazeFromID(int id) throws SQLException{
        StartTransaction();
        try{
            return DoGetMazeFromId(id);
        }
        finally {
            CommitTransaction();
        }
    }

    /**
     * Handles the transaction, returns all Maze Id's, Description,
     * Author, CreationDate, LastEdited as a MazeDescriptions.
     *
     * @return MazeDescriptions
     * @throws SQLException
     * @see MazeDescriptions
     */
    public MazeDescriptions GetMazeDescriptions() throws SQLException{
        StartTransaction();
        try{

            return DoGetMazeDescriptions();

        }
        finally {
            CommitTransaction();
        }
    }


    /**
     * Handles the transaction, Saves/Updates a Maze to the
     * database.
     *
     * @param maze the Maze to be saved or updated
     * @throws SQLException
     * @see Maze
     */
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

    /**
     * Handles the various methods that query the database
     * that need to be called to return a complete maze that can be displayed.
     *
     *
     * @param id int id of the maze
     * @return Maze a Maze from the database corresponding to the id
     * @throws SQLException
     * @see Maze
     */
    private Maze DoGetMazeFromId(int id) throws SQLException {
        Maze maze = DoGetPartialMazeFromId(id);
        maze = PopulateMazeCellsFromMazeId(id, maze);
        maze = PopulateMazeImages(id, maze);
        return maze;
    }

    /**
     * Query's the database for the images that belong to the passed in maze,
     * returns the maze with the images attached.
     *
     * @param maze the Maze to be populated with Images
     * @param mazeid the Id of the Maze
     * @throws SQLException
     * @see Maze
     * @see MazeImageResource
     */
    private Maze PopulateMazeImages(int mazeid ,Maze maze) throws SQLException {
        String sql = "SELECT Id, ImageTypeId, Image, PositionX, PositionY " + "FROM MazeImageResource " + "WHERE MazeId = ?;";

        PreparedStatement statement = PrepareStatement(sql);

        statement.setInt(1, mazeid);

        ResultSet resultSet= statement.executeQuery();
        try {
            while (resultSet.next()) {

                int imageTypeId = resultSet.getInt(2);
                Image image = GetBlobAsImage(resultSet.getBlob(3));
                int posistionX = resultSet.getInt(4);
                int posistionY= resultSet.getInt(5);

                if(imageTypeId == 2)
                {
                    maze.SetLogo(new MazeImageResource(image, posistionX, posistionY));
                }

                if(imageTypeId == 4)
                {
                    maze.SetEntryImage(new MazeImageResource(image, posistionX, posistionY));
                }

                if(imageTypeId == 6)
                {
                    maze.SetExitImage(new MazeImageResource(image, posistionX, posistionY));
                    maze.setImgSrtEnd(true);
                }

            }
            return maze;
        }
        finally {
            statement.close();
        }
    }
    /**
     * Query's the database for the cells that belong to the passed in maze,
     * returns the maze with the cells attached.
     *
     * @param maze the Maze to be populated with cells
     * @param mazeid the Id of the Maze
     * @throws SQLException
     * @see Maze
     * @see Cell
     */

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

    /**
     * Query's the database for the Maze that relates to the passed in id,
     * returns the maze.
     *
     * @param id the id of the Maze
     * @return Maze the corresponding Maze
     * @throws SQLException
     * @see Maze
     */

    private Maze DoGetPartialMazeFromId (int id) throws SQLException {

        String sql = "SELECT Id, description, Author, CreationDate, LastEdited, XDimension, YDimension " + "FROM Maze " + "WHERE Deleted = false and Id = ?";

        PreparedStatement statement = PrepareStatement(sql);

        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();
        int Id = id;
        String description="Error";
        String author="";
        int xdemension;
        int ydemension;
        Timestamp creationDate;
        Timestamp lastEditedDate;


        try {
            if (resultSet.next())
                description = resultSet.getString(2);
                author = resultSet.getString(3);
                creationDate = resultSet.getTimestamp(4);
                lastEditedDate = resultSet.getTimestamp(5);
                xdemension = resultSet.getInt(6);
                ydemension = resultSet.getInt(7);
                Maze maze = new Maze(xdemension,ydemension, author, creationDate.toLocalDateTime(), lastEditedDate.toLocalDateTime());
                maze.SetDescription(description);
                maze.SetId(id);
                return maze;
        }
        finally {
            resultSet.close();
        }
    }

    /**
     * Handles the various methods that query the database
     * that need to be called to return a complete MazeDescriptions.
     *
     *
     * @return MazeDescriptions various information about all the mazes in the
     * database wrapped up in a MazeDescription
     * @throws SQLException
     * @see MazeDescriptions
     */

    private MazeDescriptions DoGetMazeDescriptions() throws SQLException
    {
        MazeDescriptions descriptions = ReadMazeDescriptions();

        return descriptions;
    }


    /**
     * Query's the database for all the Mazes and packages the information,
     * returns a MazeDescriptions.
     *
     * @return MazeDescriptions, Information about all the mazes in the database
     * @throws SQLException
     * @see MazeDescriptions
     * @see Maze
     */

    private MazeDescriptions ReadMazeDescriptions() throws SQLException
    {
        String sql = "SELECT Id, Description, Author, CreationDate, LastEdited " + "FROM Maze " + "WHERE Deleted = false;";

        PreparedStatement statement = PrepareStatement(sql);

        ResultSet resultSet = statement.executeQuery();

        int rows = 0;
        if(resultSet.last()){
            rows = resultSet.getRow();
            resultSet.beforeFirst();
        }

        MazeDescriptions result = new MazeDescriptions(rows);
        try{
            while(resultSet.next())
            {
                result.InsertDescription(resultSet.getRow()-1, resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getTimestamp(4).toLocalDateTime(), resultSet.getTimestamp(5).toLocalDateTime());
            }
            return result;
        }
        finally {
            statement.close();
        }
    }

    /**
     * Handles the various methods that query the database
     * that need to be called to save/update a complete maze that can be displayed.
     * It uses the maze id to determine if it should update or save.
     *
     * @param maze Maze to be saved or updated
     * @throws SQLException
     * @see Maze
     */

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

    /**
     * Query's the database, Inserting a Maze.
     *
     * @param maze Maze to be saved
     * @throws SQLException
     * @see Maze
     */

    private void InsertMaze (Maze maze) throws SQLException
    {

        int id = GetNextSequence("MazeId");
        String description = maze.GetDescription();
        String Author = maze.GetAuthor();
        int length = maze.getLength();
        int height = maze.getHeight();

        String sql = "INSERT INTO Maze(Id, Deleted, Description, Author, XDimension, YDimension ) " + "VALUES (?, false, ?, ?, ?, ? )";
        PreparedStatement statement = PrepareStatement(sql);

        statement.setInt(1, id);
        statement.setString(2, description);
        statement.setString(3, Author);

        statement.setInt(4, length);
        statement.setInt(5, height);

        statement.executeUpdate();

        maze.SetId(id);

    }

    /**
     * Query's the database, Inserting a Cell of a Maze.
     *
     * @param cell cell to be saved
     * @throws SQLException
     * @see Maze
     * @see Cell
     */
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
    /**
     * Query's the database, Inserting an Image of a Maze.
     *
     * @param maze contains Image to be saved
     * @throws SQLException
     * @see Maze
     * @see MazeImageResource
     */
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
            sql = "INSERT INTO MazeImageResource(Id, MazeId, ImageTypeId, Image, PositionX, PositionY) " + "VALUES (?, ?, ?, ?, ?, ?)";

            id = GetNextSequence("MazeImageResourceId");
            imagetypeid = 2;

            positionx =  maze.getLogo().GetPositionX();
            positiony =  maze.getLogo().GetPositionY();


            statement = PrepareStatement(sql);

            statement.setInt(1, id);
            statement.setInt(2, mazeId);
            statement.setInt(3, imagetypeid);
            statement.setBlob(4,GetImageAsBlob(maze.getLogo().GetImage()));;
            statement.setInt(5, positionx);
            statement.setInt(6, positiony);

            try{
                statement.executeUpdate();
            }
            finally {
                statement.close();
            }
        }
        if(maze.getEntryImage()!=null) {
            sql = "INSERT INTO MazeImageResource(Id, MazeId, ImageTypeId, Image, PositionX, PositionY) " + "VALUES (?, ?, ?, ?, ?, ?)";

            id = GetNextSequence("MazeImageResourceId");
            imagetypeid = 4;

            positionx = maze.getEntryImage().GetPositionX();
            positiony = maze.getEntryImage().GetPositionY();

            statement = PrepareStatement(sql);

            statement.setInt(1, id);
            statement.setInt(2, mazeId);
            statement.setInt(3, imagetypeid);
            statement.setBlob(4, GetImageAsBlob(maze.getEntryImage().GetImage()));
            statement.setInt(5, positionx);
            statement.setInt(6, positiony);


            try {
                statement.executeUpdate();
            } finally {
                statement.close();
            }
        }
        if(maze.getExitImage()!=null)
        {
            sql = "INSERT INTO MazeImageResource(Id, MazeId, ImageTypeId, Image, PositionX, PositionY) " + "VALUES (?, ?, ?, ?, ?, ?)";

            id = GetNextSequence("MazeImageResourceId");
            imagetypeid = 6;

            positionx =  maze.getExitImage().GetPositionX();
            positiony =  maze.getExitImage().GetPositionY();

            statement = PrepareStatement(sql);

            statement.setInt(1, id);
            statement.setInt(2, mazeId);
            statement.setInt(3, imagetypeid);
            statement.setBlob(4, GetImageAsBlob(maze.getExitImage().GetImage()));
            statement.setInt(5, positionx);
            statement.setInt(6, positiony);


            try{
                statement.executeUpdate();
            }
            finally {
                statement.close();
            }
        }
    }

    /**
     * Query's the database, Updating a Maze.
     *
     * @param maze Maze to be Updated
     * @throws SQLException
     * @see Maze
     */

    private void UpdateMaze (Maze maze) throws SQLException {
        int id = maze.GetId();
        String description = maze.GetDescription();
        String author = maze.GetAuthor();


        String sql = "UPDATE Maze " + "SET description = ?, Author = ?, LastEdited = default " + "WHERE Id = ?";
        PreparedStatement statement = PrepareStatement(sql);

        statement.setString(1, description);
        statement.setString(2, author);
        statement.setInt(3, id);

        statement.executeUpdate();
    }

    /**
     * Query's the database, Updating a Cell of a Maze.
     *
     * @param cell cell to be Updated
     * @throws SQLException
     * @see Maze
     * @see Cell
     */
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

    /**
     * Query's the database, returning the logo id
     * from the corresponding maze id
     *
     * @param mazeId int to be queried
     * @return int representing the logo id
     * @throws SQLException
     * @see Maze
     * @see MazeImageResource
     */
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

    /**
     * Query's the database, Updating a Image of a Maze.
     *
     * @param maze containing the image to be Updated
     * @throws SQLException
     * @see Maze
     * @see Cell
     */

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
            sql = "UPDATE MazeImageResource " + "SET ImageTypeId = ?, Image = ?, PositionX = ?, PositionY = ? " + "WHERE Id = ?";

            id = GetLogoIdByMazeId(maze.GetId());

            if(id == 0)
            {
                sql = "INSERT INTO MazeImageResource(Id, MazeId, ImageTypeId, Image, PositionX, PositionY ) " + "VALUES (?, ?, ?, ?, ?, ?)";

                id = GetNextSequence("MazeImageResourceId");
                imagetypeid = 2;

                positionx =  maze.getLogo().GetPositionX();
                positiony =  maze.getLogo().GetPositionY();


                statement = PrepareStatement(sql);

                statement.setInt(1, id);
                statement.setInt(2, mazeId);
                statement.setInt(3, imagetypeid);
                statement.setBlob(4, GetImageAsBlob(maze.getLogo().GetImage()));
                statement.setInt(5, positionx);
                statement.setInt(6, positiony);


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

                statement = PrepareStatement(sql);

                statement.setInt(1, imagetypeid);
                statement.setBlob(2, GetImageAsBlob(maze.getLogo().GetImage()));
                statement.setInt(3, positionx);
                statement.setInt(4, positiony);

                statement.setInt(5, id);

                try {
                    statement.executeUpdate();
                } finally {
                    statement.close();
                }
            }
        }
    }



    //Tools//

    /**
     * Returns a ByteArrayInputStream intended to be used as blob from an Image
     * to help store Images in a database
     *
     * @param Image Image to be converted
     * @return ByteArrayInputStream to be used as blob
     * @throws SQLException
     * @see Maze
     * @see Cell
     */
    private ByteArrayInputStream GetImageAsBlob(Image Image)
    {// with help of this as reference https://stackoverflow.com/questions/20961065/converting-image-in-memory-to-a-blob
        BufferedImage bufferedImage = new BufferedImage(Image.getWidth(null), Image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.drawImage(Image, 0, 0, null);
        graphics2D.dispose();

        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        } catch (Exception exception){

        }
        finally {
            try{
                byteArrayOutputStream.close();
            } catch (Exception exception) {

            }
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        return byteArrayInputStream;
    }
    /**
     * Returns an Image intended to populate a MazeImageResource
     * to help load image out of the database
     *
     * @param blob Blob to be converted
     * @return Image to be used
     * @throws SQLException
     * @see Maze
     * @see Cell
     */
    private static Image GetBlobAsImage (Blob blob) throws SQLException {
        // with help of this as reference https://stackoverflow.com/questions/22923518/how-can-i-convert-a-bufferedimage-to-an-image
        // and this https://stackoverflow.com/questions/50427495/java-blob-to-image-file

        Image image = null;
        try {
            InputStream inputStream = blob.getBinaryStream(1, blob.length());
            image = ImageIO.read(inputStream);

        }catch (IOException ioException){

        }
        return image;
    }





}
