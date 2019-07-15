package main;

import main.logichelpers.SqlHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "UploadFile",
        urlPatterns = {"/upload"})
@MultipartConfig
public class UploadFile extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("importButton") !=null) {
            try {
                importFromFeatureFile(request);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        request.getRequestDispatcher("/uploadfile.jsp").forward(request, response);
    }

    public void importFromFeatureFile(HttpServletRequest request) throws SQLException, ServletException {
        BufferedReader reader;
        try {
            Part filePart = request.getPart("featureFile");
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            InputStream fileContent = filePart.getInputStream();
            reader = new BufferedReader(new InputStreamReader(fileContent));
            String line = reader.readLine();
            String feature = null;
            String scenario = null;
            SqlHelper sqlHelper = new SqlHelper();
            Connection connection = sqlHelper.connect();
            ArrayList<String> existScenarios;
            while (line != null) {
                if(line.contains("Feature")) {
                    feature = line.substring(line.indexOf(":")+2);
                    feature = feature.replaceAll("[^a-zA-Z0-9\\s]", "");
                    sqlHelper.insertDataToFeaturesTable(connection, feature);
                }
                else if(line.contains("Scenario")) {
                    scenario = line.substring(line.indexOf(":")+2);
                    scenario = scenario.replaceAll("[^a-zA-Z0-9\\s]", "");
                    existScenarios = sqlHelper.getTestScenariosByFeatureName(connection, feature);
                    if (!existScenarios.contains(scenario)) {
                        sqlHelper.insertDataToScenarioNamesTable(connection, scenario, feature);
                    }
                } else {
                    line = line.trim();
                    line = line.replaceAll("[^a-zA-Z0-9\\s]", "");
                    if(!line.isEmpty()) {
                        sqlHelper.insertDataToTestStepsTable(connection, line, scenario);
                    }
                }
                System.out.println(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
