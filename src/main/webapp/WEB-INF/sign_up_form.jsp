<%--
  Created by IntelliJ IDEA.
  User: MWGA Tool
  Date: 02.04.2018
  Time: 14:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <link rel="stylesheet" href="../styles.css">
<head>
    <title>Title</title>
</head>
<body>
    <form method="post" action="start">
        <input type="hidden" name="action" value="sign_up_user">
        Login: <input type="text" name="login" placeholder="login"><br/>
        Password: <input type="password" name="password" placeholder="password"><br/>
        First Name: <input type="text" name="name" placeholder="First Name"><br/>
        Last Name: <input type="text" name="surname" placeholder="Last Name"><br/>
        E-mail: <input type="text" name="email" placeholder="E-mail"><br/>
        <button type="submit">Sign Up</button>
    </form>
    <c:if test="${requestScope.isAdded eq false}">
        <p id="error">This user does exist!</p>
    </c:if>
</body>
</html>
