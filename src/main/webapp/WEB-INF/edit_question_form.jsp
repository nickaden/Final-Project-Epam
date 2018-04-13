<%--
  Created by IntelliJ IDEA.
  User: MWGA Tool
  Date: 07.04.2018
  Time: 21:06
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
    <input type="hidden" name="action" value="edit_question">
    <input type="hidden" name="id" value="${infoblock.question.id}">
    <input type="text" name="title" value="${infoblock.question.title}"><br/>
    <textarea name="description" >${infoblock.question.description}</textarea><br/>
    <c:set var="tags" value=""/>
    <c:forEach items="${infoblock.tags}" var="tag">
        <c:set var="tags" value="${tags.concat(tag.title).concat(' ')}"/>
    </c:forEach>
    <input type="text" name="tags" value="${tags}">
    <button type="submit">Submit</button>
</form>
</body>
</html>
