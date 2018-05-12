<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.lang}" scope="session"/>
<fmt:setBundle basename="localization.local" var="loc"/>
<fmt:message bundle="${loc}" key="local.search.placeholder" var="search"/>
<fmt:message bundle="${loc}" key="local.head.title" var="headTitle"/>
<fmt:message bundle="${loc}" key="local.en_option" var="en_option"/>
<fmt:message bundle="${loc}" key="local.ru_option" var="ru_option"/>
<fmt:message bundle="${loc}" key="authorization.sign_in" var="sign_in"/>
<fmt:message bundle="${loc}" key="authorization.sign_up" var="sign_up"/>
<fmt:message bundle="${loc}" key="authorization.sign_out" var="sign_out"/>
<fmt:message bundle="${loc}" key="user.login" var="login"/>
<fmt:message bundle="${loc}" key="user.password" var="password"/>
<fmt:message bundle="${loc}" key="user.first_name" var="first_name"/>
<fmt:message bundle="${loc}" key="user.last_name" var="last_name"/>
<fmt:message bundle="${loc}" key="user.email" var="email"/>
<fmt:message bundle="${loc}" key="button.confirm" var="confirm"/>
<fmt:message bundle="${loc}" key="asking.ask_button" var="ask_button"/>
<fmt:message bundle="${loc}" key="question.answers" var="answers"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${headTitle}</title>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">


    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <link rel="stylesheet" href="../styles.css">
    <link rel="stylesheet" href="../sign_up_styles.css">
    <script src="../validation.js"></script>
    <script src="../script.js"></script>
    <%--<script>--%>
        <%--$(document).ready(function () {--%>
            <%--$('.user-info-dropdown').find('#navUser').remove();--%>
        <%--})--%>
    <%--</script>--%>


</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <ul class="nav navbar-nav" id="topNav">
            <li>
                <h3><a href="/home">Like IT</a></h3>
            </li>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <c:out value="${fn:toUpperCase(sessionScope.lang)}"/>
                        <span class="caret"></span></a>
                    <ul class="dropdown-menu lang-item">
                        <li><a href="#" onclick="changeLang('en')">${en_option}</a></li>
                        <li><a href="#" onclick="changeLang('ru')">${ru_option}</a></li>
                    </ul>
                </li>
                <c:choose>
                    <c:when test="${sessionScope.user eq null}">
                        <li class="dropdown pull-right" id="menuLogin">
                            <a class="dropdown-toggle" href="#" data-toggle="dropdown" id="navLogin">
                                <span class="glyphicon glyphicon-user"></span>${sign_in}
                            </a>
                            <div class="dropdown-menu" style="padding:10px;">
                                <form class="form" id="formLogin">
                                    <input type="hidden" name="action" value="sign_in">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                                        <input id="login" type="text" class="form-control" name="login"
                                               placeholder="${login}">
                                    </div>
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                                        <input id="password" type="password" class="form-control" name="password"
                                               placeholder="${password}">
                                    </div>
                                    <p class="help-block" id="sign_in_warning">Данного пользователя не существует!</p>
                                    <button id="btnLogin" class="btn" type="submit">${sign_in}</button>
                                    <button type="button" id="btnSignUp" class="btn"
                                            data-toggle="modal" data-target="#sign_up_modal">
                                            ${sign_up}
                                    </button>
                                </form>
                            </div>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="dropdown pull-right" id="menuUser">
                            <a class="dropdown-toggle" href="#" data-toggle="dropdown" id="navUser">
                                <img class="img-rounded dropdown-image"
                                     src="/load?type=user&name=${sessionScope.user.imageName}"></a>
                            <div class="dropdown-menu user-info-dropdown" style="padding:10px;">
                                <img class="img-rounded" id="miniature"
                                     src="/load?type=user&name=${sessionScope.user.imageName}">
                                <div id="userInfo">
                                    <a href="/start?action=user_details&user=${sessionScope.user.id}">
                                        <h3><c:out value="${sessionScope.user.login}"/></h3>
                                    </a>
                                    <form method="get" action="start">
                                        <input type="hidden" name="action" value="sign_out">
                                        <button id="signOut" class="btn btn-info btn-sm" type="submit">${sign_out}</button>
                                    </form>
                                </div>
                            </div>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </ul>
    </div>
</nav>
<div id="sign_up_modal" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">${sign_up}</h4>
            </div>
            <div class="modal-body">
                <form id="sign_up_form" class="form-horizontal">
                    <div class="form-group has-feedback">
                        <input type="hidden" name="action" value="sign_up_user">
                        <label for="up_login" class="control-label col-sm-2">${login}:</label>
                        <div class="col-sm-10">
                            <input id="up_login" class="form-control" type="text" name="login" placeholder="${login}">
                            <span class="glyphicon glyphicon-ok form-control-feedback val-obj"></span>
                            <span class="glyphicon glyphicon-remove form-control-feedback val-obj"></span>
                        </div>
                    </div>
                    <div class="form-group has-feedback">
                        <label for="up_password" class="control-label col-sm-2">${password}:</label>
                        <div class="col-sm-10">
                            <input id="up_password" class="form-control" type="password" name="password"
                                   placeholder="${password}">
                            <span class="glyphicon glyphicon-ok form-control-feedback val-obj"></span>
                            <span class="glyphicon glyphicon-remove form-control-feedback val-obj"></span>
                        </div>
                    </div>
                    <div class="form-group has-feedback">
                        <label for="up_name" class="control-label col-sm-2">${first_name}:</label>
                        <div class="col-sm-10">
                            <input id="up_name" class="form-control" type="text" name="name"
                                   placeholder="${first_name}">
                            <span class="glyphicon glyphicon-ok form-control-feedback val-obj"></span>
                            <span class="glyphicon glyphicon-remove form-control-feedback val-obj"></span>
                        </div>
                    </div>
                    <div class="form-group has-feedback">
                        <label for="up_surname" class="control-label col-sm-2">${last_name}:</label>
                        <div class="col-sm-10">
                            <input id="up_surname" class="form-control" type="text" name="surname"
                                   placeholder="${last_name}">
                            <span class="glyphicon glyphicon-ok form-control-feedback val-obj"></span>
                            <span class="glyphicon glyphicon-remove form-control-feedback val-obj"></span>
                        </div>
                    </div>
                    <div class="form-group has-feedback">
                        <label for="up_email" class="control-label col-sm-2">${email}:</label>
                        <div class="col-sm-10">
                            <input id="up_email" class="form-control" type="text" name="email" placeholder="${email}">
                            <span class="glyphicon glyphicon-ok form-control-feedback val-obj"></span>
                            <span class="glyphicon glyphicon-remove form-control-feedback val-obj"></span>
                        </div>
                    </div>
                    <div class="alert alert-danger alert-hidden">
                        <strong>Ошибка!</strong> Пользователь с таким логином уже существует.
                    </div>
                    <button class="btn btn-success" type="submit">${confirm}</button>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="content container">
    <div class="button-space row">
        <c:if test="${sessionScope.user != null}">
            <a href="/start?action=ask">
                <button class="btn btn-primary" id="asking-button">${ask_button}</button>
            </a>
        </c:if>
    </div>
    <div class="row">
        <div class="content col-md-9">
            <c:forEach var="block" items="${requestScope.blocks}">
                <div class="question">
                    <table>
                        <tr>
                            <td>
                                <div class="question-mark">
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
                            </td>
                            <td width="95%">
                                <div class="title">
                                    <a href="/start?action=question_details&id=${block.question.id}">
                                        <c:out value="${block.question.title}"/>
                                    </a>
                                </div>
                            </td>
                        </tr>
                    </table>
                    <div class="question-info">
                        <div class="answered">
                            <c:out value="${answers}: ${fn:length(block.answers)}"/>
                        </div>
                        <c:forEach items="${block.tags}" var="tag">
                            <div class="tag">${tag.title}</div>
                        </c:forEach>
                        <div class="date question-date"><fmt:formatDate value="${block.question.creatingDate}"
                                                                        dateStyle="SHORT"/></div>
                        <div class="owner question-owner"><a
                                href="/start?action=user_details&user=${block.owner.id}"><c:out
                                value="${block.owner.login}"/>,</a></div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div class="sidebar col-md-3">
            <div class="panel panel-default side-panel">
            </div>
        </div>
    </div>
</div>
</div>
</body>
</html>


<%------------------------------------------------------------------------------------>



<%--<!DOCTYPE html>--%>
<%--<html lang="en">--%>
<%--<head>--%>
<%--<title>Title</title>--%>
<%--<link rel="stylesheet" href="../styles.css">--%>
<%--<meta charset="utf-8">--%>
<%--<meta name="viewport" content="width=device-width, initial-scale=1">--%>
<%--<!-- Latest compiled and minified CSS -->--%>
<%--<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">--%>
<%--</head>--%>
<%--<body>--%>
<%--<header>--%>
<%--<h2>LikeIT</h2>--%>
<%--<c:choose>--%>
<%--<c:when test="${sessionScope.user eq null}">--%>
<%--<div class="signin">--%>
<%--<form method="post" action="start">--%>
<%--<input type="hidden" name="action" value="sign_in">--%>
<%--<input type="hidden" name="old_action" value="question_view">--%>
<%--<input type="text" name="login">--%>
<%--<input type="text" name="password">--%>
<%--<button type="submit">Sign in</button>--%>
<%--</form>--%>
<%--<form method="get" action="start">--%>
<%--<input type="hidden" name="action" value="sign_up">--%>
<%--<button type="submit">Sign Up</button>--%>
<%--</form>--%>
<%--</div>--%>
<%--</c:when>--%>
<%--<c:otherwise>--%>
<%--<a href="/start?action=user_details"><c:out value="${sessionScope.user.login}"/></a>--%>
<%--<form method="get" action="start">--%>
<%--<input type="hidden" name="action" value="sign_out">--%>
<%--<button type="submit">Sign Out</button>--%>
<%--</form>--%>
<%--</c:otherwise>--%>
<%--</c:choose>--%>
<%--<form class="lang" method="post" action="start">--%>
<%--<c:set var="rus" value="selected"/>--%>
<%--<c:if test="${sessionScope.lang eq 'en'}">--%>
<%--<c:set var="eng" value="selected"/>--%>
<%--<c:set var="rus" value=""/>--%>
<%--</c:if>--%>
<%--<input type="hidden" name="action" value="change_lang">--%>
<%--<select name="lang" onchange="this.form.submit()">--%>
<%--<option value="en" <c:out value="${eng}"/> >En</option>--%>
<%--<option value="ru" <c:out value="${rus}"/> >Ru</option>--%>
<%--</select>--%>
<%--</form>--%>
<%--</header>--%>
<%--<c:if test="${sessionScope.user !=null}">--%>
<%--<a href="/start?action=ask"><button>Ask New Question</button></a>--%>
<%--</c:if>--%>
<%--<div class="questions">--%>
<%--<c:forEach var="block" items="${requestScope.blocks}">--%>
<%--<div class="question">--%>
<%--<a href="/start?action=question_details&id=${block.question.id}">--%>
<%--<c:out value="${block.question.title}"/><br/>--%>
<%--</a>--%>
<%--<c:out value="${block.question.description}"/><br/>--%>
<%--<c:out value="${fn:length(block.marks)}"/><br/>--%>
<%--<c:out value="${block.question.answered}"/><br/>--%>
<%--<c:forEach items="${block.tags}" var="tag">--%>
<%--<div class="tag">--%>
<%--<c:out value="${tag.title}"></c:out>--%>
<%--</div>--%>
<%--</c:forEach><br/>--%>
<%--</div>--%>
<%--</c:forEach>--%>
<%--</div>--%>
<%--</body>--%>
<%--</html>--%>
