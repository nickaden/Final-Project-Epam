<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

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
<fmt:message bundle="${loc}" key="button.cancel" var="cancel"/>
<fmt:message bundle="${loc}" key="asking.ask_button" var="ask_button"/>
<fmt:message bundle="${loc}" key="question.answers" var="answers"/>
<fmt:message bundle="${loc}" key="question.edit" var="edit"/>
<fmt:message bundle="${loc}" key="question.delete" var="delete"/>
<fmt:message bundle="${loc}" key="question.delete_warning_msg" var="delete_warning_msg"/>
<fmt:message bundle="${loc}" key="answer.answers" var="answers_title"/>
<fmt:message bundle="${loc}" key="answer.answer_btn" var="answer_btn"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${headTitle}</title>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="../styles.css">

    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="../script.js"></script>
    <script>
        $(document).ready(function () {
            var descriptions = $('.description');
            descriptions.each(function (i, item) {
                style_description($(item));
            });
        });
    </script>

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
                        <input type="text" class="form-control" id="search" placeholder="${search}">
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
                    <c:set var="rus" value="selected"/>
                    <c:if test="${sessionScope.lang eq 'en'}">
                        <c:set var="eng" value="selected"/>
                        <c:set var="rus" value=""/>
                    </c:if>
                    <select class="form-control" id="lang" name="lang" onchange="changeLang()">
                        <option value="en" <c:out value="${eng}"/> >${en_option}</option>
                        <option value="ru" <c:out value="${rus}"/> >${ru_option}</option>
                    </select>
                </div>
            </li>
            <c:choose>
                <c:when test="${sessionScope.user eq null}">
                    <li class="dropdown pull-right" id="menuLogin">
                        <a class="dropdown-toggle" href="#" data-toggle="dropdown" id="navLogin">${sign_in}</a>
                        <div class="dropdown-menu" style="padding:10px;">
                            <form class="form" id="formLogin">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                                    <input id="login" type="text" class="form-control" name="login" placeholder="${login}">
                                </div>
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                                    <input id="password" type="password" class="form-control" name="password"
                                           placeholder="${password}">
                                </div>
                                <p class="help-block" id="sign_in_warning"></p>
                                <button type="button" id="btnLogin" class="btn"
                                        onclick="signIn()">${sign_in}
                                </button>
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
                            <c:out value="${sessionScope.user.login}"/></a>
                        <div class="dropdown-menu" style="padding:10px;">
                            <img class="img-rounded" id="miniature"
                                 src="/load?type=user&name=${sessionScope.user.imageName}">
                            <div id="userInfo">
                                <a href="/start?action=user_details&user=${sessionScope.user.id}">
                                    <c:out value="${sessionScope.user.login}"/>
                                </a>
                                <form method="get" action="start">
                                    <input type="hidden" name="action" value="sign_out">
                                    <button id="signOut" class="btn-default" type="submit">${sign_out}</button>
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
                <h4 class="modal-title">${sign_up}</h4>
            </div>
            <div class="modal-body">
                <form id="sign_up_form">
                    <div class="form-group">
                        <input type="hidden" name="action" value="sign_up_user">
                        <label for="up_login">${login}:</label>
                        <input id="up_login" class="form-control" type="text" name="login" placeholder="${login}"><br/>
                        <label for="up_password">${password}:</label>
                        <input id="up_password" class="form-control" type="password" name="password" placeholder="${password}"><br/>
                        <label for="up_name">${first_name}:</label>
                        <input id="up_name" class="form-control" type="text" name="name" placeholder="${first_name}"><br/>
                        <label for="up_surname">${last_name}:</label>
                        <input id="up_surname" class="form-control" type="text" name="surname" placeholder="${last_name}"><br/>
                        <label for="up_email">${email}:</label>
                        <input id="up_email" class="form-control" type="text" name="email" placeholder="${email}"><br/>
                        <button class="btn btn-success" type="button" onclick="signUp()">${confirm}</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="container">
    <div class="button-space row">
        <c:if test="${sessionScope.user != null}">
            <a href="/start?action=ask">
                <button class="btn btn-primary" id="asking-button">${ask_button}</button>
            </a>
        </c:if>
    </div>
    <div class="content col-md-9">
        <div id="question-details">

            <%---------Setting marked style---------%>
            <c:set var="isMarked" value="false"/>
            <c:forEach var="mark" items="${block.marks}">
                <c:if test="${mark.ownerId eq sessionScope.user.id}">
                    <c:if test="${mark.type=='UP'}">
                        <c:set var="q_up_style"
                               value="background: url('web-images/vote_up_hover.png') no-repeat 50% 50%; background-size: contain;"/>
                        <c:set var="q_down_style" value=""/>
                    </c:if>
                    <c:if test="${mark.type=='DOWN'}">
                        <c:set var="q_up_style" value=""/>
                        <c:set var="q_down_style"
                               value="background: url('web-images/vote_down_hover.png') no-repeat 50% 50%; background-size: contain;"/>
                    </c:if>
                </c:if>
            </c:forEach>
            <%--------------------------------------%>

            <div id="question-title">
                <h3><c:out value="${block.question.title}"/></h3>
            </div>
            <div class="marking">
                <div class="vote-up mark-block" style="${q_up_style}" onclick="vote_up('question',${block.question.id},$(this))"></div>
                <div class="question-details-mark mark-block">

                    <%-- Count Rate--%>
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
                    <%---------------------%>

                </div>
                <br>
                <div class="vote-down mark-block" style="${q_down_style}" onclick="vote_down('question',${block.question.id},$(this))"></div>
            </div>
            <c:if test="${(sessionScope.user.login eq block.owner.login) or (sessionScope.user.role == 'ADMIN')}">
                <div class="ownerMenu">
                    <form>
                        <input type="hidden" name="action" value="ask">
                        <input type="hidden" name="id" value="${block.question.id}">
                        <input id="edit-question-submit" type="submit" style="display: none;">
                        <a href="#" onclick="$('#edit-question-submit').click()">${edit}</a>
                    </form>
                    <div id="delete-question-modal" class="modal fade" role="dialog">
                        <div class="modal-dialog modal-sm">
                            <div class="modal-content">
                                <div class="modal-body">
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                    <form method="post" action="start">
                                        <h5>${delete_warning_msg}</h5>
                                            <input type="hidden" name="action" value="delete_question">
                                            <input type="hidden" name="question" value="${block.question.id}">
                                            <input type="hidden" name="user" value="${sessionScope.user.id}">
                                        <button type="submit" class="btn btn-warning">${confirm}</button>
                                        <button type="button" class="btn btn-success" onclick="$('#delete-user-modal').modal('hide')">${cancel}</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <a style="cursor: pointer;" onclick="$('#delete-question-modal').modal()">${delete}</a>
                </div>
            </c:if>
            <div class="description">
                <c:out value="${block.question.description}"/>
            </div>
            <div class="question-info">
                <div class="answered">
                    <c:out value="${answers}: ${fn:length(block.answers)}"/>
                </div>
                <c:forEach items="${block.tags}" var="tag">
                    <div class="tag"><c:out value="${tag.title}"></c:out></div>
                </c:forEach>
                <div class="date question-date"><fmt:formatDate value="${block.question.creatingDate}" dateStyle="SHORT"/></div>
                <div class="owner question-owner"><a href="/start?action=user_details&user=${block.owner.id}"><c:out
                        value="${block.owner.login}"/>,</a></div>
            </div>
        </div>
        <div id="answers">
            <h3>${answers_title}</h3>
            <c:forEach var="answer" items="${block.answers}">
                <div class="answer">

                        <%---------Setting marked style---------%>
                    <c:set var="isMarked" value="false"/>
                    <c:forEach var="mark" items="${answer.marks}">
                        <c:if test="${mark.ownerId eq sessionScope.user.id}">
                            <c:choose>
                                <c:when test="${mark.type=='UP'}">
                                    <c:set var="an_up_style"
                                           value="background: url('web-images/vote_up_hover.png') no-repeat 50% 50%; background-size: contain;"/>
                                    <c:set var="an_down_style" value=""/>
                                </c:when>
                                <c:when test="${mark.type=='DOWN'}">
                                    <c:set var="an_up_style" value=""/>
                                    <c:set var="an_down_style"
                                           value="background: url('web-images/vote_down_hover.png') no-repeat 50% 50%; background-size: contain;"/>
                                </c:when>
                                    <c:otherwise>
                                        <c:set var="an_up_style" value=""/>
                                        <c:set var="an_down_style" value=""/>
                                    </c:otherwise>
                            </c:choose>
                        </c:if>
                    </c:forEach>
                        <%--------------------------------------%>

                        <%--Solution Style Setting--%>
                    <c:choose>
                        <c:when test="${answer.solution eq true}">
                            <c:set var="solution_style"
                                   value="background: url('web-images/solution_hover.png') no-repeat 50% 50%;
                                  background-size: contain;"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="solution_style" value=""/>
                        </c:otherwise>
                    </c:choose>

                        <%----------------------------%>

                    <div class="marking">
                        <div class="vote-up mark-block" style="${an_up_style}" onclick="vote_up('answer',${answer.id},$(this))"></div>
                        <div class="question-details-mark mark-block">

                                <%-- Count Rate--%>
                            <c:set var="count" value="0"/>
                            <c:forEach var="mark" items="${answer.marks}">
                                <c:choose>
                                    <c:when test="${mark.type=='UP'}"> <c:set var="count" value="${count+1}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="count" value="${count-1}"/>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            <c:out value="${count}"/>
                                <%---------------------%>

                        </div>
                        <br>
                        <div class="vote-down mark-block" style="${an_down_style}" onclick="vote_down('answer',${answer.id},$(this))"></div>
                        <div class="solution mark-block" style="${solution_style}" onclick="set_solution(${block.question.id},${answer.id},$(this))"></div>
                    </div>
                    <c:if test="${(sessionScope.user.login eq answer.owner.login) or (sessionScope.user.role == 'ADMIN')}">
                        <div class="ownerMenu answer-owner-menu">
                            <form method="post" action="start">
                                <input type="hidden" name="action" value="edit_question_form">
                                <input type="hidden" name=question" value="${block.question.id}">
                                <a href="#" onclick="this.form.submit()">${edit}</a>
                            </form>
                            <form method="post" action="start">
                                <input type="hidden" name="action" value="delete_question">
                                <input type="hidden" name="question" value="${block.question.id}">
                                <input type="hidden" name="user" value="${sessionScope.user.id}">
                                <a href="#" onclick="this.form.submit()">${delete}</a>
                            </form>
                        </div>
                    </c:if>
                    <div class="media">
                        <div class="media-body">
                            <div class="description">
                                <c:out value="${answer.description}"/>
                            </div>
                        </div>
                        <div class="media-right media-top">
                            <img src="/load?type=user&name=${answer.owner.imageName}" class="media-object img-rounded"
                                 style="width: 60px; height: 60px">
                            <div class="answer-info">
                                <span><a href="#"><c:out value="${answer.owner.login}"/></a></span>
                                <span class="date answer-date"><fmt:formatDate value="${answer.creatingDate}" dateStyle="SHORT"/></span>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
            <c:if test="${sessionScope.user != null}">
                <form class="form-horizontal answer-form" method="post" action="start">
                    <div class="form-group">
                        <input type="hidden" name="action" value="answer">
                        <input type="hidden" name="question" value="${block.question.id}">
                        <div class="styler">
                            <a href="#" onclick="chooseFile()">
                                <div class="load-picture">
                                    <input type="file" id="picture" style="display: none" onfocus="this.clear()"
                                           onchange="loadPicture()">
                                </div>
                            </a>
                            <a href="#" onclick="setCode()">
                                <div class="set-code"></div>
                            </a>
                        </div>
                        <textarea class="form-control" rows="9" id="description" name="description"
                                  onfocus="showTip('area')"></textarea>
                        <button class="btn btn-primary" type="button" onclick="this.form.submit()">${answer_btn}</button>
                    </div>
                </form>
            </c:if>
        </div>
    </div>
    <div class="sidebar col-md-3">
        <div class="panel panel-default side-panel">
        </div>
    </div>
</div>
</body>
</html>
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

