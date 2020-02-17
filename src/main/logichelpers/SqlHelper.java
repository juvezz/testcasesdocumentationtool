package main.logichelpers;

import java.sql.*;
import java.util.ArrayList;

public class SqlHelper {

    private  final String url = "jdbc:postgresql://app-db:5432/testcases";
    private final String user = "postgres";
    private final String password = "12345678";

    public Connection connect() throws SQLException {
        System.out.println("connection start");
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, user, password);
//            conn = DriverManager.getConnection(url); // local running !
            System.out.println("Connected to the PostgreSQL server successfully.");

        } catch (SQLException e) {
            throw e;
        }
        return conn;
    }

    public void createUsersTableIfNotExist(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS usernames(user_id SERIAL NOT NULL PRIMARY KEY,username varchar(225) NOT NULL UNIQUE,password varchar(225),islogged varchar(10))");
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void insertDataToUsersTable(Connection connection, String username, String password) throws SQLException {
        password = "'" + password + "'";
        int userId = getLastUserId(connection) + 1;
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO usernames (user_id, username, password)" +
                        " VALUES (" + userId + ", " + "'" + username + "'" + ", " + password + ");");
        preparedStatement.execute();
        preparedStatement.close();
    }

    private int getLastUserId(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT user_id FROM usernames");
        int lastUserId = 0;
        while (result.next()) {
            lastUserId = Integer.parseInt(result.getString("user_id"));
        }
        return lastUserId;
    }

    private int getLastId(Connection connection, String tableName) throws SQLException {
        if (connection==null) connection = new SqlHelper().connect();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT id FROM " + tableName);
        int lastId = 0;
        if(result==null) {
            return 1;
        }
        while (result.next()) {
            int id = Integer.parseInt(result.getString("id"));
            if (id > lastId) lastId = id;
        }
        return lastId;
    }

    public void createTestScenarioNamesTable(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS testcasenames(id SERIAL NOT NULL PRIMARY KEY, testcasename varchar(225), feature varchar(225))");
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void insertDataToScenarioNamesTable(Connection connection, String testcasename, String featurename) throws SQLException {
        int id = getLastId(connection, "testcasenames") + 1;
        System.out.println("insert data to sceanrios name table: " + testcasename + featurename);
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO testcasenames(id, testcasename, feature)" +
                        "VALUES (" + id + ", " + "'" + testcasename + "'" + ", " + "'"+ featurename + "'" + ");");
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void createFeaturesTable(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS features(id SERIAL NOT NULL PRIMARY KEY, featurename varchar(225))");
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void insertDataToFeaturesTable(Connection connection, String featureName) throws SQLException {
        int id = getLastId(connection, "features") + 1;
        System.out.println("insert new features to table");
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO features(id, featurename)" +
                        "VALUES (" + id + ", " + "'"+ featureName + "'" + ");");
        preparedStatement.execute();
        preparedStatement.close();
    }

    public ArrayList<String> getFeatureNames(Connection connection) throws SQLException {
        createFeaturesTable(connection);
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT featurename FROM features");
        ArrayList<String> features = new ArrayList<String>();
        while (result.next()) {
            String feature = result.getString("featurename");
            features.add(feature);
        }
        return features;
    }

    public ArrayList<String> getTestScenariosByFeatureName(Connection connection, String featureName) throws SQLException {
        createTestScenarioNamesTable(connection);
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT testcasename FROM testcasenames " +
                "WHERE feature = " + "'" + featureName + "';");
        ArrayList<String> testcasenames = new ArrayList<String>();
        while(result.next()) {
            String testcase = result.getString("testcasename");
            testcasenames.add(testcase);
        }
        return testcasenames;
    }

    public void createTestCaseStepsTable(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS teststeps(id SERIAL NOT NULL PRIMARY KEY, teststepname varchar(225), testcasename varchar(225))");
        preparedStatement.execute();
        preparedStatement.close();
    }

    public ArrayList<String> getTestStepsByTestCaseName(Connection connection, String testcasename) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT teststepname FROM teststeps " +
                "WHERE testcasename = " + "'" + testcasename + "'");
        ArrayList<String> testSteps = new ArrayList<String>();
        while (result.next()) {
            String testStep = result.getString("teststepname");
            testSteps.add(testStep);
        }
        return testSteps;
    }

    public void insertDataToTestStepsTable(Connection connection, String teststep, String testcase) throws SQLException {
        createTestCaseStepsTable(connection);
        int id = getLastId(connection, "teststeps") + 1;
        System.out.println("inser new teststeps to table");
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO teststeps(id, teststepname, testcasename)" +
                        "VALUES (" + id + ", " + "'"+ teststep + "'" + ", " + "'"+ testcase + "'" +");");
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void deleteFeatureInFeatureTable(Connection connection, String featureName) throws SQLException {
        System.out.println("deleting started featureName: " + featureName);
        PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM features WHERE featurename = " + "'"+ featureName + "'"
        );
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void deleteTestCase(Connection connection, String featureName, String testCaseName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM testcasenames WHERE feature = " + "'"+ featureName + "'" + " AND "
                + "testcasename = " + "'" + testCaseName + "'"
        );
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void clearTestCaseSteps(Connection connection, String testcasename) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM teststeps WHERE testcasename = " + "'" + testcasename + "'"
        );
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void updateTestCaseName(Connection connection, String testCaseHeader, String testcasename, String featureName) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("" +
                "SELECT id FROM testcasenames WHERE testcasename=" + "'" + testCaseHeader + "'" + " AND " +
                "feature =" + "'" + featureName + "'");
        int id = 0;
        while (result.next()) {
            id = Integer.parseInt(result.getString("id"));
        }
        System.out.println("ID = " + id);
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE testcasenames set testcasename=" + "'" + testcasename + "'" + " WHERE id=" + id
        );
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void deleteTestStep(Connection connection, String testStep, String testCase) throws SQLException {
        String sql = "DELETE FROM teststeps WHERE teststepname=" + "'" + testStep + "'" + " AND " +
                "testcasename=" + "'" + testCase + "'";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.execute();
        preparedStatement.close();
    }
}
