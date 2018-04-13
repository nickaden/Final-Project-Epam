<%--
  Created by IntelliJ IDEA.
  User: MWGA Tool
  Date: 02.04.2018
  Time: 10:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<header>LikeIT</header>
<h2>Authorization Error</h2>
<form method="post" class="signin" action="start">
    <input type="hidden" name="action" value="sign_in">
    <input type="hidden" name="source" value="${requestScope.header}">
    <input type="text" name="login">
    <input type="text" name="password">
    <button type="submit">Sign in</button>
</form>
<form method="get" class="signup" action="start">
    <input type="hidden" name="action" value="sign_up">
    <button type="submit">Sign Up</button>
</form>
</body>
</html>
