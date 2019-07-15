<%@ page import="main.logichelpers.SqlHelper" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: SPE02
  Date: 2019-05-10
  Time: 11:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  </head>
  <body>
  <form action="servlet" method="post">
    <jsp:include page="components/menu.jsp"/>
    <%
      SqlHelper sqlHelper = new SqlHelper();
      Connection connection = sqlHelper.connect();
      String featureName = request.getParameter("featurename");
      if (featureName == null && sqlHelper.getFeatureNames(connection).size()!=0) {
        featureName = sqlHelper.getFeatureNames(connection).get(0);
      }
    %>
    <input type="text" name="featurename" hidden="true" value="<%=featureName%>">
    <h1 align="center">Test Cases</h1>
    <p align="center" id="featurename" name="featurename">Feature: <%= featureName %></p>


    <div id="wrapper">
      <jsp:include page="components/sidebar.jsp"/>


      </div>
      <div id="page-content-wrapper">
        <div class="page-content" id="scenarios">
          <%

            ArrayList<String> testCaseNames = sqlHelper.getTestScenariosByFeatureName(connection, featureName);
            for (int i = 0; i < testCaseNames.size(); i++) {
          %>
          <div class="page-content" id="savescenarios">
            <div class="container" id="savemainpart">
              <a href="testcasesteps.jsp?testcasename=<%=testCaseNames.get(i)%>&featureName=<%=featureName%>"><input type="text" class="form-control" id="savetestCaseName" name="savetestcasename" value="<%=testCaseNames.get(i)%>" readonly="true"></a>
              <div align="right">
                <input type="submit" class="btn btn-danger" id="deleteTestScenario" name="deleteTestScenario=<%=testCaseNames.get(i)%>" value="Delete"/>
              </div>
              </br>
            </div>
          </div>

          <%
            }
          %>
          <div class="container" id="mainpart">
            </br>
            <input type="text" class="form-control" id="testCaseName" name="testcasename" placeholder="test case name">
            </br>
          </div>
        </div>
        <div class="container" align="right">
          </br>

          <button type="button" class="btn btn-success" id="addNewScenario" name="addnewscenario" onclick="addNewScenarioForm();">Add new scenario</button>

          <input type="submit" class="btn btn-success" id="saveButton" name="savebutton" onclick="saveButton()" value="Save"/>


        </div>
      </div>
    </form>
  </body>
</html>

<script>

  function addNewScenarioForm() {
    var scenario = document.createElement("input");
    scenario.type = "text";
    scenario.className = "form-control";
    scenario.name = "testcasename";
    scenario.placeholder = "test case name";
    var div = document.createElement("div");
    div.className = "container";
    var br = document.createElement("br");
    div.appendChild(scenario);
    div.appendChild(br);
    var parent = document.getElementById("scenarios");
    parent.appendChild(div);
  }

  function saveButton() {
    window.alert("sometext");
  }

</script>

<style>
  .container {
    width: 60%;
  }
</style>