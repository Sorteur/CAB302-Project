package DataModules;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The class that handles SQL transactions
 */
public class DataModule {

    protected DBConnection DBConnection;

    /**
     * Sets up new SQL connection
     * @param connection Connection to open
     * @see DBConnection
     */
    protected DataModule(DBConnection connection )
    {
        DBConnection = connection;
    }

    /**
     * Starts SQL Transaction
     * @throws SQLException
     * @see DBConnection
     */
    protected void StartTransaction() throws SQLException
    {
        DBConnection.StartTransaction();
    }

    /**
     * Ends SQL transaction and Commits
     * @throws SQLException
     * @see DBConnection
     */
    protected void CommitTransaction() throws SQLException
    {
        DBConnection.CommitTransaction();
    }

    /**
     * Rolls Back Transaction
     * @throws SQLException
     * @see DBConnection
     */
    protected void RollbackTransaction() throws SQLException
    {
        DBConnection.RollbackTransaction();
    }

    /**
     * Prepares provided SQL statement
     * @param sql Statement to be
     * @return PreparedStatement
     * @throws SQLException
     * @see DBConnection
     */
    protected PreparedStatement PrepareStatement(String sql) throws SQLException
    {
        PreparedStatement statement = DBConnection.Connection.prepareStatement(sql);

        return statement;
    }

    /**
     *
     * @param sequenceName
     * @return
     * @throws SQLException
     * @see DBConnection
     */
    protected int GetNextSequence(String sequenceName) throws SQLException
    {
        String sql = "SELECT NEXT VALUE FOR " +sequenceName;
        int id = 0;

        PreparedStatement statement = PrepareStatement(sql);

        ResultSet resultSet = statement.executeQuery();
        try {
            if (resultSet.next())
                id = resultSet.getInt(1);
        }
        finally {
            resultSet.close();
        }
        return id;
    }



}
