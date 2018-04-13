<%--
  Created by IntelliJ IDEA.
  User: MWGA Tool
  Date: 09.04.2018
  Time: 12:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="../styles.css">
</head>
<body>
<div class="user_desc">
    <img src="/load?name=${user.imageName}" width="200px" height="200px">
    <form method="POST" enctype="multipart/form-data" action="start">
        <input type="hidden" name="action" value="load_user_image">
        <input type="file" name="upfile"><br/>
        <input type="submit" value="Press"><br>
    </form>
    <c:out value="${sessionScope.user.login}"/>
    <c:out value="${sessionScope.user.name}"/>
    <c:out value="${sessionScope.user.surname}"/>
    <c:out value="${sessionScope.user.email}"/>
    <c:if test="${sessionScope.user.role == 'ADMIN'}">
        <form method="get" action="start">
            <input type="hidden" name="action" value="admin_menu">
            <button type="submit">Admin Menu</button>
        </form>

    </c:if>
</div>

<h3>Questions (<c:out value="${fn:length(questions)}"/>)</h3>
<c:forEach var="question" items="${requestScope.questions}">
    <div id="user_questions">
            <a href="/start?action=question_details&id=${question.id}">
                <c:out value="${question.title}"/>
            </a>
    </div>
</c:forEach>

<h3>Answers (<c:out value="${fn:length(requestScope.answered_questions)}"/>)</h3>
<c:forEach var="question" items="${requestScope.answered_questions}">
    <div id="user_answered">
        <a href="/start?action=question_details&id=${question.id}">
            <c:out value="${question.title}"/>
        </a>
    </div>
</c:forEach>
</body>
</html>
