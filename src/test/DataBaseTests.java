package test;

import main.logichelpers.SqlHelper;
import org.junit.Test;
import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Date;

public class DataBaseTests {

    @Test
    public void ExportFromDataBaseToCSV() throws SQLException, FileNotFoundException {
        SqlHelper sqlHelper = new SqlHelper();
        Connection connection = sqlHelper.connect();
        String pathName = "/Users/SPE02/myfile1"
                + LocalDateTime.now().getMonth().toString()
                + LocalDateTime.now().getDayOfMonth()
                + ".csv";
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        String query = "COPY teststeps TO '" + pathName + "' WITH (FORMAT CSV, HEADER);";
        statement.executeUpdate(query);
        statement.close();
    }
}
