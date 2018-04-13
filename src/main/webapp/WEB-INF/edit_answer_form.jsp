<%--
  Created by IntelliJ IDEA.
  User: MWGA Tool
  Date: 08.04.2018
  Time: 11:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post" action="start">
    <input type="hidden" name="action" value="edit_answer">
    <input type="hidden" name="answer" value="${answer.id}">
    <input type="hidden" name="path" value="${path}">
    <textarea name="description">${requestScope.answer.description}</textarea>
    <button type="submit">Submit</button>
</form>

</body>
</html>
