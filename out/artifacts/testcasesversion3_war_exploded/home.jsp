<%@ page import="main.logichelpers.SqlHelper" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.SQLException" %><%--
  Created by IntelliJ IDEA.
  User: SPE02
  Date: 2019-06-05
  Time: 12:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List of feature(test scenarios groups)</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
    <jsp:include page="components/menu.jsp"/>
    <form action="save" method="post">
        <div class="container" id="allcontent">
            <div id="featureslist">
            <%
            SqlHelper sqlHelper = new SqlHelper();
            Connection connection = sqlHelper.connect();
            try {
                sqlHelper.createFeaturesTable(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ArrayList<String> features = null;
            try {
                features = sqlHelper.getFeatureNames(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            int j = 0;
            System.out.println("feautres count: " + features.size());
            for (int i = 0; i < features.size(); i++) {
                j =i;%>
                <a class="col-md-11" href="index.jsp?featurename=<%=features.get(i)%>"><input type="text" class="form-control" id="testFeatureName" name="testfeaturename" value="<%=features.get(i)%>" readonly="true"></a>
                <input type="submit" class="btn btn-danger col-md-1" name="deleteFeature=<%=features.get(i)%>" value="delete">
                <%}
            %>
            </div>
            <div align="center">
                <button type="button" class="btn btn-success" id="addNewFeature" name="addnewfeature" onclick="addNewFeatureFile();">Add new feature</button>
                <input type="submit" class="btn btn-success" id="saveButton" name="savebutton" onclick="saveButtonFeatureFile();" value="Save"/>
            </div>
        </div>
    </form>
</body>
</html>

<script>
    function addNewFeatureFile() {
        var input = document.createElement("input");
        input.type = "text";
        input.className = "col-md-11 form-control";
        input.placeholder="feature name";
        input.name = "newtestfeaturename";
        var parent = document.getElementById("featureslist");
        parent.appendChild(input);
    }
    
    function saveButtonFeatureFile() {
        var feautures = document.getElementsByName("testfeaturename");
        var featuresNames = new Array();
        for (i=0; i<feautures.length;i++) {
            featuresNames[i] = feautures.item(i).value;
        }
        if(featuresNames.includes(document.getElementsByName("newtestfeaturename").item(0).value)) {
            window.alert("feature names should be different!");
        } else {
            window.alert("Data saved successfully");
        }

    }

    function deleteFeature() {
        window.alert("Deleting is not safe for other users(yet)");
    }

</script>

<style>
    .delete {
        width: 1%;
        border: none;
    }
</style>
