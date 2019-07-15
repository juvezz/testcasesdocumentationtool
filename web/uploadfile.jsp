<%--
  Created by IntelliJ IDEA.
  User: SPE02
  Date: 2019-07-11
  Time: 14:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Upload Feature File</title>
</head>
<body>
<jsp:include page="components/menu.jsp"/>

    <form action="upload" method="post" enctype="multipart/form-data">
        <div>
            <input type="file" name="featureFile">
            <input type="submit" class="btn btn-success" id="importButton" name="importButton" value="Import feature file"/>
        </div>
    </form>
</body>
</html>
