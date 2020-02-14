package main;

import main.logichelpers.FeaturesToAttributesMapping;
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
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "Features",
urlPatterns = {"/features"})
@MultipartConfig
public class Features extends HttpServlet {

    private Connection connection;

    private SqlHelper sqlHelper = new SqlHelper();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Enumeration enumeration = request.getParameterNames();
        while(enumeration.hasMoreElements()) {
            String parameter = enumeration.nextElement().toString();
            if (request.getParameter("savebutton")!=null) {
                addNewFeature(request);
            } else if (parameter.contains("runFeature")) {
                String featureName = parameter.replace("runFeature=", "");
                FeaturesToAttributesMapping featuresToAttributesMapping = new FeaturesToAttributesMapping();
                String attribute = featuresToAttributesMapping.featuresToAttributeMap.get(featureName);
                JenkinsHelper.runJobWithFeatureAttribute(attribute);
            }
            else if (parameter.contains("deleteFeature")){
                deleteFeature(request);
            }
        }

        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<String> existFeatures = getExistFeatures();
        Map<String, String> featuresToAttributeMap = new FeaturesToAttributesMapping().featuresToAttributeMap;
        ArrayList<String> implementedAutomatedTestForFeatures = (ArrayList<String>) existFeatures.stream().
                filter(featuresToAttributeMap::containsKey).collect(Collectors.toList());
        request.setAttribute("features", implementedAutomatedTestForFeatures);
        existFeatures.removeAll(implementedAutomatedTestForFeatures);
        ArrayList<String> notImplemented = existFeatures;
        request.setAttribute("notImplemented", notImplemented);
        request.getRequestDispatcher("/features.jsp").forward(request, response);
    }



    private void deleteFeature(HttpServletRequest request) {
        ArrayList<String> existFeatures = getExistFeatures();
        for (int i = 0; i < existFeatures.size(); i++) {
            if (request.getParameter("deleteFeature=" + existFeatures.get(i)) != null) {
                try {
                    sqlHelper.deleteFeatureInFeatureTable(connection, existFeatures.get(i));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private ArrayList<String> getExistFeatures() {
        ArrayList<String> existFeatures = new ArrayList<>();
        try {
            connection = sqlHelper.connect();
            existFeatures = sqlHelper.getFeatureNames(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existFeatures;
    }

    private void addNewFeature(HttpServletRequest request) {
        String[] featureNames = request.getParameterValues("newtestfeaturename");
        ArrayList<String> existFeatures = getExistFeatures();
        for (int i = 0; i < featureNames.length; i++) {
            try {
                if(!existFeatures.contains(featureNames[i]) && !featureNames[i].isEmpty()) {
                    sqlHelper.insertDataToFeaturesTable(connection, featureNames[i]);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
