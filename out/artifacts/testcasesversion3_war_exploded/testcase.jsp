<%@ page import="main.logichelpers.SqlHelper" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: SPE02
  Date: 2019-06-06
  Time: 14:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Test case description</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap-theme.min.css" integrity="sha384-6pzBo3FDv/PJ8r2KRkGHifhEocL+1X2rVCTTkUfGk7/0pbek5mMa1upzvWbrUbOZ" crossorigin="anonymous">
</head>
<body>
<jsp:include page="components/menu.jsp"/>
    <form action="saveTestCaseSteps" method="post">
        <div class="container" id="mainpart">
            <div align="center">
                <input type="text" hidden="true" name="featureName" value="<%=request.getParameter("featureName")%>">
                <input type="text" hidden="true" name="testCaseHeader" value="<%=request.getParameter("testcasename")%>">
                <br/>
                <span>Test case: </span><span><%=request.getParameter("testcasename")%></span>
            </div>
            <input type="text" class="form-control" id="testCaseName" name="testcasename" value="<%=request.getParameter("testcasename")%>">

        <%
            SqlHelper sqlHelper = new SqlHelper();
            Connection connection = sqlHelper.connect();
            sqlHelper.createTestCaseStepsTable(connection);
            ArrayList<String> testSteps = sqlHelper.getTestStepsByTestCaseName(connection, request.getParameter("testcasename"));
            for (int i = 0; i < testSteps.size(); i++) {
                %>
            <div name="testStepContainer<%=i%>">
                <div class="col-md-1">
                    <i class="fa fa-plus-square" style="font-size:24px" onclick="addElementAfterThisStep(<%=i%>);"></i>
                    <i class="fa fa-minus-square" style="font-size:24px" onclick="deleteTestStepFunction(<%=i%>)"></i>
                    <input type="submit" hidden="true" id="deleteTestStepInput<%=i%>" name="deleteTestStep" value="<%=testSteps.get(i)%>">
                </div>

                <input type="text" class="col-md-11" name="teststep" value="<%=testSteps.get(i)%>">
            </div>
        <%
            }
        %>
        </div>
        <div class="container" align="right">
            <input type="submit" class="btn btn-success" id="saveButton" name="savebutton" onclick="saveButton()" value="Save"/>
        </div>
    </form>
</body>
</html>

<script>
    document.body.onload = addElement;
    function addElement() {

        var div = document.createElement("div")
        div.className= "col-md-1";

        var plus = document.createElement("i");
        plus.className = "fa fa-plus-square";
        plus.style = "font-size:24px";
        plus.onclick = addElement;

        var input = document.createElement("input");
        input.type = "text";
        input.className = "col-md-11";
        input.name = "teststep";
        input.placeholder="test case step";
        var parent = document.getElementById("mainpart");

        div.appendChild(plus);
        parent.appendChild(div);
        parent.appendChild(input);
    }

    function addElementAfterThisStep(testStepNumber) {
        var div = document.createElement("div");
        div.className= "col-md-1";

        var plus = document.createElement("i");
        plus.className = "fa fa-plus-square";
        plus.style = "font-size:24px";
        plus.onclick = addElementAfterThisStep;
        plus.hidden = true;

        var input = document.createElement("input");
        input.type = "text";
        input.className = "col-md-11";
        input.name = "teststep";
        input.placeholder="test case step";
        var parent = document.getElementsByName("testStepContainer"+testStepNumber).item(0);

        div.appendChild(plus);
        parent.appendChild(div);
        parent.appendChild(input);
    }

    function deleteTestStepFunction(i) {
        window.alert("deleting test step: ");
        var deleteButton = document.getElementById("deleteTestStepInput"+i);
        deleteButton.click();
    }
</script>
