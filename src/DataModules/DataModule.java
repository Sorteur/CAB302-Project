package DataModules;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataModule {

    protected DBConnection DBConnection;

    protected DataModule(DBConnection connection )
    {
        DBConnection = connection;
    }

    protected void StartTransaction() throws SQLException
    {
        DBConnection.StartTransaction();
    }

    protected void CommitTransaction() throws SQLException
    {
        DBConnection.CommitTransaction();
    }

    protected void RollbackTransaction() throws SQLException
    {
        DBConnection.RollbackTransaction();
    }

    protected PreparedStatement PrepareStatement(String sql) throws SQLException
    {
        PreparedStatement statement = DBConnection.Connection.prepareStatement(sql);

        return statement;
    }

    protected int GetNextSequence(String sequenceName) throws SQLException
    {
        String sql = "SELECT NEXT VALUE FOR " +sequenceName;
        int id = 0;
        StartTransaction();
        try
        {
            PreparedStatement statement = PrepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next())
                id = resultSet.getInt(1);

        }
        finally {
            CommitTransaction();
        }

        return id;
    }



}
