package main;

import main.logichelpers.JenkinsHelper;
import main.logichelpers.SqlHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;

@WebServlet(name = "Features",
urlPatterns = {"/save"})
@MultipartConfig
public class Features extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Enumeration enumeration = request.getParameterNames();
        while(enumeration.hasMoreElements()) {
            String parameter = enumeration.nextElement().toString();
            if (request.getParameter("savebutton")!=null) {
                System.out.println("add new feature(1)");
                addNewFeature(request);
            } else if (parameter.contains("runFeature")) {
                String featureName = "@cpfilters";
                JenkinsHelper.runJobWithFeatureAttribute(featureName);
            }
            else if (parameter.contains("deleteFeature")){
                System.out.println("delete Feature");
                deleteFeature(request);
            }
        }

        request.getRequestDispatcher("/features.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h1>" + "Get test" + "</h1>");
    }



    private void deleteFeature(HttpServletRequest request) {
        System.out.println("deleting features");
        SqlHelper sqlHelper = new SqlHelper();
        Connection connection = null;
        ArrayList<String> existFeatures = new ArrayList<String>();
        try {
            connection = sqlHelper.connect();
            existFeatures = sqlHelper.getFeatureNames(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("existFeature: " + existFeatures.size());
        for (int i = 0; i < existFeatures.size(); i++) {
            System.out.println(i);
            if (request.getParameter("deleteFeature=" + existFeatures.get(i)) != null) {
                System.out.println("deletingFeature: " + existFeatures.get(i));
                try {
                    sqlHelper.deleteFeatureInFeatureTable(connection, existFeatures.get(i));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void addNewFeature(HttpServletRequest request) {
        System.out.println("add new feature...");
        String[] featureNames = request.getParameterValues("newtestfeaturename");
        System.out.println(featureNames.length);
        SqlHelper sqlHelper = new SqlHelper();
        Connection connection = null;
        ArrayList<String> existFeatures = new ArrayList<String>();
        try {
            connection = sqlHelper.connect();
            existFeatures = sqlHelper.getFeatureNames(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < featureNames.length; i++) {
            try {
                System.out.println("featureName: " + featureNames[0]);
                if(!existFeatures.contains(featureNames[i]) && !featureNames[i].isEmpty()) {
                    sqlHelper.insertDataToFeaturesTable(connection, featureNames[i]);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
