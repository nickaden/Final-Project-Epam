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
<fmt:message bundle="${loc}" key="question.answers" var="answers"/>
<fmt:message bundle="${loc}" key="question.edit" var="edit"/>
<fmt:message bundle="${loc}" key="question.delete" var="delete"/>
<fmt:message bundle="${loc}" key="question.title" var="q_title"/>
<fmt:message bundle="${loc}" key="question.enter_title_msg" var="enter_title_msg"/>
<fmt:message bundle="${loc}" key="question.tags" var="q_tags"/>
<fmt:message bundle="${loc}" key="question.enter_tags_msg" var="enter_tags_msg"/>
<fmt:message bundle="${loc}" key="question.show_preview_btn" var="show_preview_btn"/>
<fmt:message bundle="${loc}" key="question.done_btn" var="done_btn"/>
<fmt:message bundle="${loc}" key="question.how_to_ask.title" var="ask_title"/>
<fmt:message bundle="${loc}" key="question.how_to_ask.msg_1" var="ask_msg_1"/>
<fmt:message bundle="${loc}" key="question.how_to_ask.msg_2" var="ask_msg_2"/>
<fmt:message bundle="${loc}" key="question.how_to_format.title" var="format_title"/>
<fmt:message bundle="${loc}" key="question.how_to_format.msg_1" var="format_msg_1"/>
<fmt:message bundle="${loc}" key="question.how_to_format.msg_2" var="format_msg_2"/>
<fmt:message bundle="${loc}" key="question.how_to_format.msg_3" var="format_msg_3"/>
<fmt:message bundle="${loc}" key="question.how_to_tag.title" var="tag_title"/>
<fmt:message bundle="${loc}" key="question.how_to_tag.msg_1" var="tag_msg_1"/>
<fmt:message bundle="${loc}" key="question.how_to_tag.msg_2" var="tag_msg_2"/>

<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <title>${headTitle}</title>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="../styles.css">

    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="../script.js"></script>
    <link rel="stylesheet" href="../sign_up_styles.css">
    <script src="../validation.js"></script>

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
<div class="container">
    <div class="col-md-8">
        <form class="form-horizontal" method="post" action="start">
            <input type="hidden" name="action" value="edit_answer">
            <input type="hidden" name="answer" value="${answer.id}">
            <input type="hidden" name="path" value="${path}">
            <div class="form-group">
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
                <textarea class="form-control" rows="9" id="description" name="description">
                    ${requestScope.answer.description}
                </textarea>
            </div>
            <button class="btn btn-success" type="submit">${done_btn}</button>
        </form>
    </div>
    <div class="col-md-4">
        <div class="panel panel-default side-panel">
            <div class="help-tip" id="how-to-format">
                <h4><b>${format_title}</b></h4>
                <p>${format_msg_1}</p>
                <p>${format_msg_2}</p>
                <p>${format_msg_3}</p>
            </div>
        </div>
    </div>
</div>
</body>
</html>


<%-------------------------------------------------------------------------------------------------------------------%>

<%--<html>--%>
<%--<head>--%>
<%--<title>Title</title>--%>
<%--</head>--%>
<%--<body>--%>
<%--<form method="post" action="start">--%>
<%--<input type="hidden" name="action" value="add_question">--%>
<%--<input type="text" name="title"><br/>--%>
<%--<textarea name="description"></textarea><br/>--%>
<%--<input type="text" name="tags">--%>
<%--<button type="submit">Submit</button>--%>
<%--</form>--%>
<%--</body>--%>
<%--</html>--%>

<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<%@ page isELIgnored="false" %>--%>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%--<html>--%>
<%--<head>--%>
    <%--<title>Title</title>--%>
<%--</head>--%>
<%--<body>--%>
<%--<form method="post" action="start">--%>
    <%--<input type="hidden" name="action" value="edit_answer">--%>
    <%--<input type="hidden" name="answer" value="${answer.id}">--%>
    <%--<input type="hidden" name="path" value="${path}">--%>
    <%--<textarea name="description">${requestScope.answer.description}</textarea>--%>
    <%--<button type="submit">Submit</button>--%>
<%--</form>--%>

<%--</body>--%>
<%--</html>--%>
