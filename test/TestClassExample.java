import main.logichelpers.SqlHelper;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class TestClassExample {

    @Test
    public void sqlHelperTestConnection() throws SQLException, ClassNotFoundException {
        SqlHelper sqlHelper = new SqlHelper();
        Connection connection = sqlHelper.connect();
    }

    @Test
    public void testCreateTableIfNotExists() throws SQLException, ClassNotFoundException {
        SqlHelper sqlHelper = new SqlHelper();
        Connection connection = sqlHelper.connect();
        sqlHelper.createUsersTableIfNotExist(connection);
    }

    @Test
    public void testInsertUserDataToTable() throws SQLException, ClassNotFoundException {
        SqlHelper sqlHelper = new SqlHelper();
        Connection connection = sqlHelper.connect();
        sqlHelper.insertDataToUsersTable(connection, "test4", "pass");
    }

    @Test
    public void testCreateFeatureInDataBase() throws SQLException, ClassNotFoundException {
        SqlHelper sqlHelper = new SqlHelper();
        Connection connection = sqlHelper.connect();
        sqlHelper.createFeaturesTable(connection);
        sqlHelper.insertDataToFeaturesTable(connection, "testfeature");
    }
}
