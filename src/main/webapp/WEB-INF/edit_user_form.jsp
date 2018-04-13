<%--
  Created by IntelliJ IDEA.
  User: MWGA Tool
  Date: 10.04.2018
  Time: 11:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form method="post" action="start">
        <input type="hidden" name="path" value="${requestScope.referer}">
        <input type="hidden" name="action" value="edit_user">
        <input type="hidden" name="id" value="${requestScope.user.id}">

        <input type="text" name="login" value="${requestScope.user.login}"><br/>
        <input type="text" name="password" value="${requestScope.user.password}"><br/>
        <input type="text" name="name" value="${requestScope.user.name}"><br/>
        <input type="text" name="surname" value="${requestScope.user.surname}"><br/>
        <input type="text" name="email" value="${requestScope.user.email}"><br/>
        <select name="role" >
            <option value="ADMIN">Admin</option>
            <option value="USER" selected >User</option>
        </select><br/>
        <button type="submit">Submit</button>
    </form>
</body>
</html>
