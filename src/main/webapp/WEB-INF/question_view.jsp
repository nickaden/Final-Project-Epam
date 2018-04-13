<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="../styles.css">
</head>
<body>
<header>
    <h2>LikeIT</h2>
    <c:choose>
        <c:when test="${sessionScope.user eq null}">
            <div class="signin">
                <form method="post" action="start">
                    <input type="hidden" name="action" value="sign_in">
                    <input type="hidden" name="old_action" value="question_view">
                    <input type="text" name="login">
                    <input type="text" name="password">
                    <button type="submit">Sign in</button>
                </form>
                <form method="get" action="start">
                    <input type="hidden" name="action" value="sign_up">
                    <button type="submit">Sign Up</button>
                </form>
            </div>
        </c:when>
        <c:otherwise>
            <a href="/start?action=user_details"><c:out value="${sessionScope.user.login}"/></a>
            <form method="get" action="start">
                <input type="hidden" name="action" value="sign_out">
                <button type="submit">Sign Out</button>
            </form>
        </c:otherwise>
    </c:choose>
    <form class="lang" method="post" action="start">
        <c:set var="rus" value="selected"/>
        <c:if test="${sessionScope.lang eq 'en'}">
            <c:set var="eng" value="selected"/>
            <c:set var="rus" value=""/>
        </c:if>
        <input type="hidden" name="action" value="change_lang">
        <select name="lang" onchange="this.form.submit()">
            <option value="en" <c:out value="${eng}"/> >En</option>
            <option value="ru" <c:out value="${rus}"/> >Ru</option>
        </select>
    </form>
</header>
<c:if test="${sessionScope.user !=null}">
    <a href="/start?action=ask"><button>Ask New Question</button></a>
</c:if>
<div class="questions">
    <c:forEach var="block" items="${requestScope.blocks}">
        <div class="question">
            <a href="/start?action=question_details&id=${block.question.id}">
                <c:out value="${block.question.title}"/><br/>
            </a>
            <c:out value="${block.question.description}"/><br/>
            <c:out value="${fn:length(block.marks)}"/><br/>
            <c:out value="${block.question.answered}"/><br/>
            <c:forEach items="${block.tags}" var="tag">
                <div class="tag">
                    <c:out value="${tag.title}"></c:out>
                </div>
            </c:forEach><br/>
        </div>
    </c:forEach>
</div>
</body>
</html>
