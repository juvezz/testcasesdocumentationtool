package main;

import main.logichelpers.SqlHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

@WebServlet(name = "TestCasesList",
            urlPatterns = {"/servlet"})
public class TestCasesList extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("savebutton")!= null) {
            try {
                saveTestScenarios(request);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                deleteTestScenarios(request);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        request.getRequestDispatcher("/index.jsp?featurename=" + request.getParameter("featurename")).forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void deleteTestScenarios(HttpServletRequest request) throws SQLException {
        SqlHelper sqlHelper = new SqlHelper();
        Connection connection = sqlHelper.connect();
        String featurename = request.getParameter("featurename");
        ArrayList<String> existScenarioNames = sqlHelper.getTestScenariosByFeatureName(connection,featurename);
        for (int i = 0; i < existScenarioNames.size(); i++) {
            if(request.getParameter("deleteTestScenario="+existScenarioNames.get(i))!=null) {
                sqlHelper.deleteTestCase(connection, featurename, existScenarioNames.get(i));
            }
        }
    }

    private void saveTestScenarios(HttpServletRequest request) throws SQLException {
        SqlHelper sqlHelper = new SqlHelper();
        Connection connection = sqlHelper.connect();
        sqlHelper.createTestScenarioNamesTable(connection);
        String[] testScenarioNames = request.getParameterValues("testcasename");
        String[] testScenarioNamesWithoutDuplicates = new HashSet<String>(Arrays.asList(testScenarioNames)).toArray(new String[0]);
        String featurename = request.getParameter("featurename");
        System.out.println("Testcaeslist feature name: " + featurename);
        ArrayList<String> existScenarios = sqlHelper.getTestScenariosByFeatureName(connection, featurename);

        for (int i = 0; i < testScenarioNamesWithoutDuplicates.length; i++) {
            if(!testScenarioNames[i].isEmpty() && !existScenarios.contains(testScenarioNames[i])) {
                sqlHelper.insertDataToScenarioNamesTable(connection, testScenarioNames[i], featurename);
            }
        }
    }
}