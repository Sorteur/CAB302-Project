package DataModules;

import Engine.MazeGenerator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    public Connection Connection = null;
    public DBConnection() throws SQLException {
        Open();
    }

    /**
     * Opens a new connection to database
     * @throws SQLException
     */
    private void Open() throws SQLException {
        // Week 6 Content...
        Properties props = new Properties();
        FileInputStream in = null;
        try {
            in = new FileInputStream(".idea/db.props");
            props.load(in);
            in.close();

            // specify the data source, username and password
            String url = props.getProperty("jdbc.url");
            String username = props.getProperty("jdbc.username");
            String password = props.getProperty("jdbc.password");
            String schema = props.getProperty("jdbc.schema");

            // get a connection
                Connection = DriverManager.getConnection(url + "/" + schema, username, password);

                if (Connection == null)
                {

                }

        }
        catch (FileNotFoundException fnfe) {
            System.err.println(fnfe);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    /**
     * Starts SQL transaction
     * @throws SQLException
     */
    public void StartTransaction() throws SQLException
    {
        Connection.setAutoCommit(false);
    }

    /**
     * Commits SQL Transaction
     * @throws SQLException
     */
    public void CommitTransaction() throws SQLException
    {
        Connection.commit();
    }

    /**
     * Rolls back SQL Transaction
     * @throws SQLException
     */
    public void RollbackTransaction() throws SQLException
    {
        Connection.rollback();
    }


}
