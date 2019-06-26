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

@WebServlet(name = "TestCase",
urlPatterns = {"/saveTestCaseSteps"})
public class TestCase extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("savebutton")!=null) {
            saveTestSteps(request);
        }
        else{
            deleteTestSteps(request);
        }

        request.getRequestDispatcher("/testcase.jsp?testcasename="+ request.getParameter("testcasename")+
                "&featureName=" + request.getParameter("featureName")).forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        // Actual logic goes here.
        PrintWriter out = response.getWriter();
        out.println("<h1>" + "Get test" + "</h1>");
    }

    private void deleteTestSteps(HttpServletRequest request) {
        String testCase = request.getParameterValues("testCaseHeader")[0];
        String testStep = request.getParameterValues("deleteTestStep")[0];
        SqlHelper sqlHelper = new SqlHelper();
        try {
            Connection connection = sqlHelper.connect();
            sqlHelper.deleteTestStep(connection, testStep, testCase);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveTestSteps(HttpServletRequest request) {
        String[] testSteps = request.getParameterValues("teststep");
        System.out.println("test steps : " + testSteps.length);
        SqlHelper sqlHelper = new SqlHelper();
        Connection connection = null;
        try {
            connection = sqlHelper.connect();
            sqlHelper.createTestCaseStepsTable(connection);
            sqlHelper.clearTestCaseSteps(connection, request.getParameter("testCaseHeader"));
            sqlHelper.clearTestCaseSteps(connection, request.getParameter("testcasename"));
            if(!request.getParameter("testCaseHeader").equals(request.getParameter("testcasename"))) {
                sqlHelper.updateTestCaseName(
                        connection,
                        request.getParameter("testCaseHeader"),
                        request.getParameter("testcasename"),
                        request.getParameter("featureName"));
            }
            for (int i = 0; i < testSteps.length; i++) {
                if(!testSteps[i].isEmpty()) {
                    System.out.println(testSteps[i]);
                    sqlHelper.insertDataToTestStepsTable(connection, testSteps[i], request.getParameter("testcasename"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
