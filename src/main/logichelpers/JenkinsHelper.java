package main.logichelpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class JenkinsHelper {
    public static void runJobWithFeatureAttribute(String featureName) throws IOException {
        try {
            URL url = new URL("https://jenkins.kartenmacherei.de/job/runfromtestdocumentationtool/buildWithParameters" +
                    "?token=11ab7ad57a88bd2d3de663a5d92da5cc31"+
                    "?feature=" + featureName);
            String user = "SPE02";
            String pass = "7491Mipt";
            String authStr = user + ":" + pass;
            String encoding = Base64.getEncoder().encodeToString(authStr.getBytes("utf-8"));

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Basic " + encoding);
            InputStream content = connection.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(content));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
