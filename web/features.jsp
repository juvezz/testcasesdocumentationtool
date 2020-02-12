<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List of feature(test scenarios groups)</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
    <jsp:include page="components/menu.jsp"/>
    <form action="features" method="post">
        <div class="container" id="allcontent">
            <div id="featureslist">
            <c:forEach var="feature" items="${features}">
                <a class="col-md-10" href="index.jsp?featurename=${feature}"><input type="text" class="form-control transparent-input" id="testFeatureName" name="testfeaturename" value="${feature}" readonly="true"></a>
                <input type="submit" class="btn btn-success col-md-1" name="runFeature=${feature}" value="Run" onclick="runFeature()">
                <input type="submit" class="btn btn-danger col-md-1" name="deleteFeature=${feature}" value="delete">
            </c:forEach>
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

    function runFeature() {
        window.alert("Jenkins job for this featute was executed: https://jenkins.kartenmacherei.de/job/runfromtestdocumentationtool/")
    }

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
    input.transparent-input{
        background-color:rgba(0,0,0,0) !important;
        border:none !important;
    }
</style>