<%--
  Created by IntelliJ IDEA.
  User: MWGA Tool
  Date: 11.05.2018
  Time: 10:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.lang}" scope="session"/>
<fmt:setBundle basename="localization.local" var="error_locale"/>
<fmt:message bundle="${error_locale}" key="error.title" var="title"/>
<fmt:message bundle="${error_locale}" key="error.link" var="link"/>
<fmt:message bundle="${error_locale}" key="error.head" var="head"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${head}</title>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">


    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="col-sm-9">
                <h2>Something went wrong...</h2>
                <p><c:out value="${error}"/></p>
                <a href="/home">Go to Main Page</a>
            </div>
            <div class="col-sm-3">
                <img src="../web-images/monkey.png">
            </div>
        </div>
    </div>
</body>
</html>
