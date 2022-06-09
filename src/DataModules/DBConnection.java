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

    private static DBConnection instance = new DBConnection();
    public static DBConnection Instance(){
        return instance;
    }

    DBConnection () {
        Open();
    }

    private void Open() {
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
            Connection = DriverManager.getConnection(url + "/" + schema, username,
                    password);
        } catch (SQLException sqle) {
            System.err.println(sqle);
        } catch (FileNotFoundException fnfe) {
            System.err.println(fnfe);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void Close() {
        System.out.println("Closing Connection");
        try {
            Connection.close();
        }
        catch (SQLException sqle) {
            System.err.println(sqle);
        }
    }

    public void StartTransaction() throws SQLException
    {
        Connection.setAutoCommit(false);
    }

    public void CommitTransaction() throws SQLException
    {
        Connection.commit();
    }

    public void RollbackTransaction() throws SQLException
    {
        Connection.rollback();
    }



}
