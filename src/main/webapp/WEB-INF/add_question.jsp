<%--
  Created by IntelliJ IDEA.
  User: MWGA Tool
  Date: 04.04.2018
  Time: 22:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form method="post" action="start">
        <input type="hidden" name="action" value="add_question">
        <input type="text" name="title"><br/>
        <textarea name="description"></textarea><br/>
        <input type="text" name="tags">
        <button type="submit">Submit</button>
    </form>
</body>
</html>
