<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="../styles.css">
</head>
<header>
    <a id="back" href="/start?action=question_view"><button>Back</button></a>
    <h2>LikeIT</h2>
    <c:choose>
        <c:when test="${sessionScope.user eq null}">
            <form method="post" class="signin" action="start">
                <input type="hidden" name="action" value="sign_in">
                <input type="hidden" name="old_action" value="question_details">
                <input type="text" name="login">
                <input type="text" name="password">
                <button type="submit">Sign in</button>
            </form>
            <form method="get" class="signup" action="start">
                <input type="hidden" name="action" value="sign_up">
                <button type="submit">Sign Up</button>
            </form>
        </c:when>
        <c:otherwise>
            <c:out value="${sessionScope.user.login}"/>
            <form method="get" action="start">
                <input type="hidden" name="action" value="sign_out">
                <button type="submit">Sign Out</button>
            </form>
        </c:otherwise>
    </c:choose>
</header>
<body>
<div class="question">
    <c:if test="${sessionScope.user != null}">
        <form class="marks" method="post" action="start">
            <input type="hidden" name="action" value="add_mark">
            <input type="hidden" name="type" value="question">
            <input type="hidden" name="id" value="${block.question.id}">
            <button type="submit" name="mark_type" value="up">Up</button>
            <button type="submit" name="mark_type" value="down">Down</button>
        </form>
    </c:if>
    <c:if test="${(sessionScope.user eq block.owner) or (sessionScope.user.role == 'ADMIN')}">
        <form method="post" action="start">
            <input type="hidden" name="action" value="edit_question_form">
            <input type="hidden" name="question" value="${block.question.id}">
            <button type="submit">Edit Question</button>
        </form>
        <form method="post" action="start">
            <input type="hidden" name="action" value="delete_question">
            <input type="hidden" name="question" value="${block.question.id}">
            <input type="hidden" name="user" value="${sessionScope.user.id}">
            <button type="submit">Delete Question</button>
        </form>
    </c:if>
    <c:out value="${block.question.title}"/><br/>
    <c:out value="${block.question.description}"/><br/>
    <c:out value="${fn:length(block.marks)}"/><br/>
    <c:out value="${block.question.answered}"/><br/>
    <c:out value="${block.owner.login}"/><br/>
    <c:forEach items="${block.tags}" var="tag">
        <div class="tag">
            <c:out value="${tag.title}"></c:out>
        </div>
    </c:forEach><br/>
</div>

<div class="answers">
    <c:forEach var="answer" items="${answers}">
        <div class="answer">
            <c:if test="${sessionScope.user != null}">
                <form class="marks" method="post" action="start">
                    <input type="hidden" name="action" value="add_mark">
                    <input type="hidden" name="type" value="answer">
                    <input type="hidden" name="id" value="${answer.id}">
                    <button type="submit" name="mark_type" value="up">Up</button>
                    <button type="submit" name="mark_type" value="down">Down</button>
                </form>
            </c:if>
            <c:if test="${(sessionScope.user eq block.owner) or (sessionScope.user.role == 'ADMIN')}">
                <form method="post" action="start">
                    <input type="hidden" name="action" value="set_solution">
                    <input type="hidden" name="question" value="${block.question.id}">
                    <input type="hidden" name="answer" value="${answer.id}">
                    <button type="submit">Make as Solution</button>
                </form>
            </c:if>
            <c:out value="${answer.description}"/><br/>
            <c:out value="${answer.owner.login}"/><br/>
            <c:out value="${fn:length(answer.marks)}"/><br/>
            <c:out value="${answer.solution}"/><br/>
            <c:if test="${(answer.owner eq sessionScope.user) or (sessionScope.user.role == 'ADMIN')}">
                <form method="post" action="start">
                    <input type="hidden" name="answer" value="${answer.id}">
                    <input type="hidden" name="user" value="${sessionScope.user.id}">
                    <button type="submit" name="action" value="delete_answer">Delete</button>
                    <button type="submit" name="action" value="edit_answer_form">Edit</button>
                </form>
            </c:if>
        </div>
    </c:forEach>
</div>

<c:if test="${sessionScope.user != null}">
        <form  id="add_answer" method="post" action="start">
            <textarea name="description" placeholder="Write your answer here"></textarea><br/>
            <input type="hidden" name="action" value="answer">
            <input type="hidden" name="question" value="${block.question.id}">
            <button type="submit">Answer</button>
        </form>
</c:if>


</body>
</html>
