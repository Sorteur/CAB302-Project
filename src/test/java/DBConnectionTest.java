import DataModules.DBConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class DBConnectionTest {

    DBConnection connection;
    @BeforeEach

    @Test
    void Test() throws SQLException{
        connection = new DBConnection();
    }

    @Test
    void Test2() throws SQLException{
        connection.StartTransaction();
    }

    @Test
    void Test3() throws SQLException{
        connection.CommitTransaction();
    }

    @Test
    void Test4() throws SQLException{
        connection.StartTransaction();
        connection.RollbackTransaction();
    }


}
