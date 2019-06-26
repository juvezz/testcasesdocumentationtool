package main;

import main.logichelpers.SqlHelper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/MainPageLogic")
public class MainPageLogic extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("hello");
        SqlHelper sqlHelper = new SqlHelper();
        try {
            Connection connection = sqlHelper.connect();
            sqlHelper.insertDataToUsersTable(connection, "test5", "pass");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        // Actual logic goes here.
        PrintWriter out = response.getWriter();
        out.println("<h1>" + "Hello world" + "</h1>");
    }

    private void saveTestScenarios(HttpServletRequest request) throws SQLException {
        String[] testScenarioNames = request.getParameterValues("testcasename");
        SqlHelper sqlHelper = new SqlHelper();
        Connection connection = sqlHelper.connect();
        for (int i = 0; i < testScenarioNames.length; i++) {
            sqlHelper.insertDataToUsersTable(connection, testScenarioNames[i], "pass");
        }
    }


}
