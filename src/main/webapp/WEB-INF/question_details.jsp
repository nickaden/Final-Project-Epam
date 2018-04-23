<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Title</title>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="../styles.css">

    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="../script.js"></script>

</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <ul class="nav navbar-nav" id="topNav">
            <li>
                <h3><a href="/home">Like IT</a></h3>
            </li>
            <li><a href="#">
                <form class="form-inline">
                    <div class="input-group col-xs-12">
                        <input type="text" class="form-control" id="search" placeholder="Search">
                        <div class="input-group-btn">
                            <button class="btn btn-default" type="submit">
                                <i class="glyphicon glyphicon-search"></i>
                            </button>
                        </div>
                    </div>
                </form>
            </a></li>
            <li>
                <div class="form-group">
                    <form id="change_lang" method="post" action="start"></form>
                    <c:set var="rus" value="selected"/>
                    <c:if test="${sessionScope.lang eq 'en'}">
                        <c:set var="eng" value="selected"/>
                        <c:set var="rus" value=""/>
                    </c:if>
                    <input type="hidden" name="action" value="change_lang" form="change_lang">
                    <select class="form-control" id="sel1" name="lang" form="change_lang" onchange="this.form.submit()">
                        <option value="en" <c:out value="${eng}"/> >EN</option>
                        <option value="ru" <c:out value="${rus}"/> >RU</option>
                    </select>
                </div>
            </li>
            <c:choose>
                <c:when test="${sessionScope.user eq null}">
                    <li class="dropdown pull-right" id="menuLogin">
                        <a class="dropdown-toggle" href="#" data-toggle="dropdown" id="navLogin">Login</a>
                        <div class="dropdown-menu" style="padding:10px;">
                            <form class="form" id="formLogin">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                                    <input id="login" type="text" class="form-control" name="login" placeholder="login">
                                </div>
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                                    <input id="password" type="password" class="form-control" name="password"
                                           placeholder="Password">
                                </div>
                                <p class="help-block" id="sign_in_warning"></p>
                                <button type="button" id="btnLogin" class="btn"
                                        onclick="signIn()">Войти
                                </button>
                                <button type="button" id="btnSignUp" class="btn"
                                        data-toggle="modal" data-target="#sign_up_modal">
                                    Регистрация
                                </button>
                            </form>
                        </div>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="dropdown pull-right" id="menuUser">
                        <a class="dropdown-toggle" href="#" data-toggle="dropdown" id="navUser">
                            <c:out value="${sessionScope.user.login}"/></a>
                        <div class="dropdown-menu" style="padding:10px;">
                            <img class="img-rounded" id="miniature"
                                 src="/load?type=user&name=${sessionScope.user.imageName}">
                            <div id="userInfo">
                                <a href="/start?action=user_details"><c:out value="${sessionScope.user.login}"/></a>
                                <form method="get" action="start">
                                    <input type="hidden" name="action" value="sign_out">
                                    <button id="signOut" class="btn-default" type="submit">Выйти</button>
                                </form>
                            </div>
                        </div>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</nav>
<div id="sign_up_modal" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Регистрация</h4>
            </div>
            <div class="modal-body">
                <form id="sign_up_form">
                    <div class="form-group">
                        <input type="hidden" name="action" value="sign_up_user">
                        <label for="up_login">Login:</label>
                        <input id="up_login" class="form-control" type="text" name="login" placeholder="login"><br/>
                        <label for="up_password">Password:</label>
                        <input id="up_password" class="form-control" type="password" name="password"
                               placeholder="password"><br/>
                        <label for="up_name">First Name:</label>
                        <input id="up_name" class="form-control" type="text" name="name" placeholder="First Name"><br/>
                        <label for="up_surname">Last Name:</label>
                        <input id="up_surname" class="form-control" type="text" name="surname"
                               placeholder="Last Name"><br/>
                        <label for="up_email">E-mail:</label>
                        <input id="up_email" class="form-control" type="text" name="email" placeholder="E-mail"><br/>
                        <button class="btn btn-success" type="button" onclick="signUp()">Подтвердить</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="container">
    <div class="button-space row">
        <a href="/start?action=ask">
            <button class="btn btn-primary" id="asking-button">Задать вопрос</button>
        </a>
    </div>
    <div class="content col-md-9">
        <div class="question-details">
            <div id="question-title">
                <h3><c:out value="${block.question.title}"/></h3>
            </div>
            <div class="marking">
                <a onclick="vote_up('question',${block.question.id})">
                    <div class="vote-up mark-block"></div>
                </a>
                <div class="question-details-mark mark-block">
                    <c:set var="count" value="0"/>
                    <c:forEach var="mark" items="${block.marks}">
                        <c:choose>
                            <c:when test="${mark.type=='UP'}">
                                <c:set var="count" value="${count+1}"/>
                            </c:when>
                            <c:otherwise>
                                <c:set var="count" value="${count-1}"/>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <c:out value="${count}"/>
                </div>
                <br>
                <a onclick="vote_down('question',${block.question.id})">
                    <div class="vote-down mark-block"></div>
                </a>
            </div>
            <c:if test="${(sessionScope.user eq block.owner) or (sessionScope.user.role == 'ADMIN')}">
                <div class="ownerMenu">
                    <a href="#">править</a>
                    <a href="#">удалить</a>
                </div>
            </c:if>
            <div class="description">
                <c:out value="${block.question.description}"/>
            </div>
            <div class="question-info">
                <div class="answered">
                    <c:out value="${fn:length(block.answers)} Ответов"/>
                </div>
                <c:forEach items="${block.tags}" var="tag">
                    <div class="tag">${tag.title}</div>
                </c:forEach>
                <div class="date question-date"><c:out value="${block.question.creatingDate}"/></div>
                <div class="owner question-owner"><a href="#"><c:out value="${block.owner.login}"/>,</a></div>
            </div>
        </div>
        <h3>Ответы</h3>
        <div class="answer">
            <div class="marking">
                <div class="vote-up mark-block"><a href="#"></a></div>
                <div class="question-details-mark mark-block">
                    3
                </div>
                <br>
                <div class="vote-down mark-block"><a href="#"></a></div>
                <div class="solution mark-block"><a href="#"></a></div>
            </div>
            <div class="ownerMenu answer-owner-menu">
                <a href="#">править</a>
                <a href="#">удалить</a>
            </div>
            <div class="media">
                <div class="media-body">
                    Смысл в том что сетка бутстрапа должна состоять из 12колонок (4 * 3 = 12) И быть обрамленной в
                    контейнер или безразмерный контейнер (.container-fluid) в котором лежит .row Если важно сохранить
                    фиксированную ширину колонки, то меняем ширину контейнера
                </div>
                <div class="media-right media-top">
                    <img src="../web-images/sample.jpg" class="media-object img-rounded"
                         style="width: 60px; height: 60px">
                    <div class="answer-info">
                        <span><a href="#">user</a></span>
                        <span class="date answer-date">19.04.1998</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="sidebar col-md-3">
        <div class="panel panel-default side-panel">
        </div>
    </div>
</div>
<%-------------------------------------------------------------------------------------------------------------------%>

<%--<div class="question">--%>
<%--<c:if test="${sessionScope.user != null}">--%>
<%--<form class="marks" method="post" action="start">--%>
<%--<input type="hidden" name="action" value="add_mark">--%>
<%--<input type="hidden" name="type" value="question">--%>
<%--<input type="hidden" name="id" value="${block.question.id}">--%>
<%--<button type="submit" name="mark_type" value="up">Up</button>--%>
<%--<button type="submit" name="mark_type" value="down">Down</button>--%>
<%--</form>--%>
<%--</c:if>--%>
<%--<c:if test="${(sessionScope.user eq block.owner) or (sessionScope.user.role == 'ADMIN')}">--%>
<%--<form method="post" action="start">--%>
<%--<input type="hidden" name="action" value="edit_question_form">--%>
<%--<input type="hidden" name="question" value="${block.question.id}">--%>
<%--<button type="submit">Edit Question</button>--%>
<%--</form>--%>
<%--<form method="post" action="start">--%>
<%--<input type="hidden" name="action" value="delete_question">--%>
<%--<input type="hidden" name="question" value="${block.question.id}">--%>
<%--<input type="hidden" name="user" value="${sessionScope.user.id}">--%>
<%--<button type="submit">Delete Question</button>--%>
<%--</form>--%>
<%--</c:if>--%>
<%--<c:out value="${block.question.title}"/><br/>--%>
<%--<c:out value="${block.question.description}"/><br/>--%>
<%--<c:out value="${fn:length(block.marks)}"/><br/>--%>
<%--<c:out value="${block.question.answered}"/><br/>--%>
<%--<c:out value="${block.owner.login}"/><br/>--%>
<%--<c:forEach items="${block.tags}" var="tag">--%>
<%--<div class="tag">--%>
<%--<c:out value="${tag.title}"></c:out>--%>
<%--</div>--%>
<%--</c:forEach><br/>--%>
<%--</div>--%>

<%--<div class="answers">--%>
<%--<c:forEach var="answer" items="${answers}">--%>
<%--<div class="answer">--%>
<%--<c:if test="${sessionScope.user != null}">--%>
<%--<form class="marks" method="post" action="start">--%>
<%--<input type="hidden" name="action" value="add_mark">--%>
<%--<input type="hidden" name="type" value="answer">--%>
<%--<input type="hidden" name="id" value="${answer.id}">--%>
<%--<button type="submit" name="mark_type" value="up">Up</button>--%>
<%--<button type="submit" name="mark_type" value="down">Down</button>--%>
<%--</form>--%>
<%--</c:if>--%>
<%--<c:if test="${(sessionScope.user eq block.owner) or (sessionScope.user.role == 'ADMIN')}">--%>
<%--<form method="post" action="start">--%>
<%--<input type="hidden" name="action" value="set_solution">--%>
<%--<input type="hidden" name="question" value="${block.question.id}">--%>
<%--<input type="hidden" name="answer" value="${answer.id}">--%>
<%--<button type="submit">Make as Solution</button>--%>
<%--</form>--%>
<%--</c:if>--%>
<%--<c:out value="${answer.description}"/><br/>--%>
<%--<c:out value="${answer.owner.login}"/><br/>--%>
<%--<c:out value="${fn:length(answer.marks)}"/><br/>--%>
<%--<c:out value="${answer.solution}"/><br/>--%>
<%--<c:if test="${(answer.owner eq sessionScope.user) or (sessionScope.user.role == 'ADMIN')}">--%>
<%--<form method="post" action="start">--%>
<%--<input type="hidden" name="answer" value="${answer.id}">--%>
<%--<input type="hidden" name="user" value="${sessionScope.user.id}">--%>
<%--<button type="submit" name="action" value="delete_answer">Delete</button>--%>
<%--<button type="submit" name="action" value="edit_answer_form">Edit</button>--%>
<%--</form>--%>
<%--</c:if>--%>
<%--</div>--%>
<%--</c:forEach>--%>
<%--</div>--%>
<%--<c:if test="${sessionScope.user != null}">--%>
<%--<form id="add_answer" method="post" action="start">--%>
<%--<textarea name="description" placeholder="Write your answer here"></textarea><br/>--%>
<%--<input type="hidden" name="action" value="answer">--%>
<%--<input type="hidden" name="question" value="${block.question.id}">--%>
<%--<button type="submit">Answer</button>--%>
<%--</form>--%>
<%--</c:if>--%>
<%--</body>--%>
<%--</html>--%>
