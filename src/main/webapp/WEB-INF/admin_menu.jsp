
<%--
  Created by IntelliJ IDEA.
  User: MWGA Tool
  Date: 10.04.2018
  Time: 9:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="../styles.css">
</head>
<body>
<div class="user_menu">
    <c:forEach var="user" items="${users}">
        <div class="user">
            <c:out value="${user.login}"/><br/>
            <c:out value="${user.name}"/>
            <c:out value="${user.surname}"/><br/>
            <c:out value="${user.email}"/><br/>
            <c:out value="${user.role}"/><br/>
            <form method="post" action="start">
                <input type="hidden" name="id" value="${user.id}"/>
                <input type="hidden" name="login" value="${user.login}"/>
                <input type="hidden" name="password" value="${user.password}"/>
                <input type="hidden" name="name" value="${user.name}"/>
                <input type="hidden" name="surname" value="${user.surname}"/>
                <input type="hidden" name="email" value="${user.email}"/>
                <input type="hidden" name="role" value="${user.role}"/>
                <button type="submit" name="action" value="edit_user_form">Edit</button>
                <button type="submit" name="action" value="delete_user">Delete</button>
            </form>
        </div>
    </c:forEach>
    <form method="post" action="start">
        <input type="hidden" name="action" value="add_user">
        <input type="text" name="login" placeholder="login"/>
        <input type="text" name="password" placeholder="password">
        <input type="text" name="name" placeholder="name"/>
        <input type="text" name="surname" placeholder="surname"/>
        <input type="text" name="email" placeholder="e-mail"/>
        <select name="role">
            <option value="ADMIN">Admin</option>
            <option value="USER">User</option>
        </select>
        <button type="submit">Add New User</button>
    </form>
</div>
<div class="tag_menu">
    <c:forEach var="tag" items="${requestScope.tags}">
        <div class="tag_view">
            <form method="post" action="start">
                <input type="hidden" name="id" value="${tag.id}">
                <input type="text" name="title" value="${tag.title}">
                <button type="submit" name="action" value="edit_tag">Edit</button>
                <button type="submit" name="action" value="delete_tag">Delete</button>
            </form>
        </div>
    </c:forEach>
    <form method="post" action="start">
        <input type="hidden" name="action" value="add_tag">
        <input type="text" name="title">
        <button type="submit">Add new Tag</button>
    </form>
</div>
</body>
</html>
