<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="main.logichelpers.SqlHelper" %><%--
  Created by IntelliJ IDEA.
  User: SPE02
  Date: 2019-05-28
  Time: 16:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<div id="sidebar-wrapper">
    <ul class="sidebar-nav">
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
            for (int i = 0; i < features.size(); i++) {
        %>  <ul class="sidebar-band">
                <li><a href="index.jsp?featurename=<%=features.get(i)%>"><%=features.get(i)%></a></li>
            </ul><%
        }
    %>
    </ul>
</div>
</body>
</html>

<style>
    #wrapper {
        padding-left: 250px;
        transition: all 0.4s ease 0s;
    }

    #sidebar-wrapper {
        margin-left: -20%;
        left: 20%;
        width: 20%;
        background: #111111;
        position: fixed;
        height: 100%;
        overflow-y: auto;
        z-index: 1000;
        transition: all 0.4s ease 0s;
    }

    #page-content-wrapper {
        width: 100%;
    }

    .sidebar-nav {
        position: absolute;
        top: 0;
        width: 250px;
        list-style: none;
        margin: 0;
        padding: 0;
    }

    .sidebar-band {
        color: green;
    }

    @media (max-width:767px) {

        #wrapper {
            padding-left: 0;
        }

        #sidebar-wrapper {
            left: 0;
        }


        #wrapper.active {
            position: relative;
            left: 250px;
        }

        #wrapper.active #sidebar-wrapper {
            left: 250px;
            width: 250px;
            transition: all 0.4s ease 0s;
        }

    }
</style>
