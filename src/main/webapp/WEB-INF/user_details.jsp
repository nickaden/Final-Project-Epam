<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<fmt:message bundle="${loc}" key="user.change_image" var="change_image"/>
<fmt:message bundle="${loc}" key="user.total_rate" var="total_rate"/>
<fmt:message bundle="${loc}" key="user.reg_date" var="reg_date"/>
<fmt:message bundle="${loc}" key="user.role_title" var="role_title"/>
<fmt:message bundle="${loc}" key="user.admin_role" var="admin_role"/>
<fmt:message bundle="${loc}" key="user.user_role" var="user_role"/>
<fmt:message bundle="${loc}" key="button.confirm" var="confirm"/>
<fmt:message bundle="${loc}" key="button.cancel" var="cancel"/>
<fmt:message bundle="${loc}" key="asking.ask_button" var="ask_button"/>
<fmt:message bundle="${loc}" key="question.answers" var="answers"/>
<fmt:message bundle="${loc}" key="question.edit" var="edit"/>
<fmt:message bundle="${loc}" key="question.delete" var="delete"/>
<fmt:message bundle="${loc}" key="question.delete_warning_msg" var="delete_warning_msg"/>
<fmt:message bundle="${loc}" key="answer.answers" var="answers_title"/>
<fmt:message bundle="${loc}" key="answer.answer_btn" var="answer_btn"/>
<fmt:message bundle="${loc}" key="button.edit" var="edit_btn"/>
<fmt:message bundle="${loc}" key="tab.questions" var="questions_tab"/>
<fmt:message bundle="${loc}" key="tab.answers" var="answers_tab"/>
<fmt:message bundle="${loc}" key="tab.tags" var="tags_tab"/>
<fmt:message bundle="${loc}" key="tab.users" var="users_tab"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Title</title>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="../bootstrap-3.3.7-dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="../styles.css">

    <!-- jQuery library -->
    <script src="../jquery-3.3.1.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="../bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
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
<div id="edit-user-modal" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Изменение пользователя</h4>
            </div>
            <div class="modal-body">
            </div>
        </div>
    </div>
</div>
<div id="delete-user-modal" class="modal fade" role="dialog">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-body">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <form method="post" action="start">
                    <h5>Вы уверены, что хотите удалить пользователя?</h5>
                    <input id='user-id' type="hidden" name="id" value="">
                    <input type="hidden" name="action" value="delete_user">
                    <button type="submit" class="btn btn-warning">Подтвердить</button>
                    <button type="button" class="btn btn-success" onclick="$('#delete-user-modal').modal('hide')">Отмена</button>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="container-fluid">
    <div class="user col-md-5">
        <div class="user-image">
            <img id="user-menu-image" class="img-thumbnail"
                 src="/load?type=user&name=${requestScope.user.imageName}">
            <c:if test="${sessionScope.user.login eq requestScope.user.login}">
                <button class="btn btn-primary" onclick="chooseFile($('#picture'))">${change_image}</button>
                <input type="file" id="picture" style="display: none" onfocus="this.clear()"
                       onchange="loadUserImage($('#picture'),${requestScope.user.id})">
                </form>
            </c:if>
        </div>
        <div class="user-info">
            <h3 id="nickname"><b><c:out value="${requestScope.user.login}"/></b></h3>
            <c:if test="${sessionScope.user.login eq requestScope.user.login}">
                <div id="edit-user" onclick="showEditUserModal($('#edit-user-form-own'))"></div>
                <form id="edit-user-form-own" method="post"
                      action="start" style="display: none">
                    <input type="hidden" name="image_name" value="${requestScope.user.imageName}">
                    <input type="hidden" name="role" value="${requestScope.user.role}">
                    <div class="form-group">
                        <input type="hidden" name="id" value="${requestScope.user.id}"/>
                        <label class="control-label" for="user-login-own">${login}</label>
                        <input id="user-login-own" type="text" class="form-control" name="login"
                               value="${requestScope.user.login}"/>
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="user-password-own">${password}</label>
                        <input id="user-password-own" type="password" class="form-control" name="password"
                               value="${requestScope.user.password}"/>
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="user-name-own">${first_name}</label>
                        <input id="user-name-own" type="text" class="form-control" name="name"
                               value="${requestScope.user.name}"/>
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="user-surname-own">${last_name}</label>
                        <input id="user-surname-own" type="text" class="form-control" name="surname"
                               value="${requestScope.user.surname}"/>
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="user-email-own">${email}</label>
                        <input id="user-email-own" type="text" class="form-control" name="email"
                               value="${requestScope.user.email}"/>
                    </div>
                    <div class="form-group">
                        <button class="btn btn-success " type="submit" name="action"
                                value="edit_user">${edit_btn}
                        </button>
                    </div>
                </form>
            </c:if>
            <h4>${total_rate}: <c:out value="${requestScope.rate}"/></h4>
            <table class="table-condensed">
                <tr>
                    <td>${first_name}:</td>
                    <td><c:out value="${requestScope.user.name}"/></td>
                </tr>
                <tr>
                    <td>${last_name}:</td>
                    <td><c:out value="${requestScope.user.surname}"/></td>
                </tr>
                <tr>
                    <td>${email}:</td>
                    <td><c:out value="${requestScope.user.email}"/></td>
                </tr>
                <tr>
                    <td>${reg_date}:</td>
                    <td><c:out value="${requestScope.user.regDate}"/></td>
                </tr>
            </table>
        </div>
    </div>
    <div class="qa-lists col-md-7">
        <ul class="user-tab nav nav-tabs">
            <li id="question-tab" class="active"><a data-toggle="tab" href="#questions">${questions_tab}</a></li>
            <li id="answer-tab"><a data-toggle="tab" href="#answers">${answers_tab}</a></li>
            <c:if test="${(requestScope.user.login eq sessionScope.user.login) && sessionScope.user.role=='ADMIN'}">
                <li id="tags-tab"><a data-toggle="tab" href="#tags">${tags_tab}</a></li>
                <li id="users-tab"><a data-toggle="tab" href="#users">${users_tab}</a></li>
            </c:if>
        </ul>
        <div class="tab-content">
            <div id="questions" class="questions tab-pane fade in active">
                <c:forEach var="block" items="${requestScope.questions}">
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
                                <c:out value="Ответов: ${fn:length(block.answers)}"/>
                            </div>
                            <c:forEach items="${block.tags}" var="tag">
                                <div class="tag">${tag.title}</div>
                            </c:forEach>
                            <div class="date question-date"><c:out value="${block.question.creatingDate}"/></div>
                            <div class="owner question-owner"><a
                                    href="/start?action=user_details&user=${block.owner.id}"><c:out
                                    value="${block.owner.login}"/>,</a></div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div id="answers" class="answers tab-pane fade">
                <c:forEach var="block" items="${requestScope.answered_questions}">
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
                                <c:out value="Ответов: ${fn:length(block.answers)}"/>
                            </div>
                            <c:forEach items="${block.tags}" var="tag">
                                <div class="tag">${tag.title}</div>
                            </c:forEach>
                            <div class="date question-date"><c:out value="${block.question.creatingDate}"/></div>
                            <div class="owner question-owner"><a
                                    href="/start?action=user_details&user=${block.owner.id}"><c:out
                                    value="${block.owner.login}"/>,</a></div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <c:if test="${sessionScope.user.role =='ADMIN'}">
                <div id="tags" class="tab-pane fade">
                    <div class="tags-menu">
                        <div id="edit-tag-modal" class="modal fade" role="dialog">
                            <div class="modal-dialog modal-sm">
                                <div class="modal-content">
                                    <div class="modal-body">
                                        <form class=form-inline" method="post" action="start">
                                            <div class="form-group">
                                                <input type="hidden" name="id" value="">
                                                <input type="text" class="form-control" name="title" value="">
                                                <button class="btn btn-primary" type="submit" name="action"
                                                        value="edit_tag">
                                                    Изменить
                                                </button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>

                        </div>
                        <div id="delete-tag-modal" class="modal fade" role="dialog">
                            <div class="modal-dialog modal-sm">
                                <div class="modal-content">
                                    <div class="modal-body">
                                        <h4>Вы действительно хотите удалить пользователя?</h4>
                                        <form class=form-inline" method="post" action="start">
                                            <div class="form-group">
                                                <input type="hidden" name="id" value="">
                                                <button class="btn btn-warning" type="submit" name="action"
                                                        value="delete_tag">
                                                    Подтвердить
                                                </button>
                                                <button type="button" class="btn btn-success" onclick="$('#delete-tag-modal').modal('hide')">Отмена</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>

                        </div>
                        <c:forEach var="tag" items="${requestScope.tags}">
                            <div class="tag-sm edit-menu">
                                <h3><c:out value="${tag.title}"/></h3>
                                <div class="edit-buttons">
                                    <div class="delete-by-admin" onclick="showDeleteTagModal(${tag.id})"></div>
                                    <div class="edit-by-admin" onclick="showEditTagModal(${tag.id},${tag.title})"></div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <div id="users" class="tab-pane fade">
                    <div class="users-menu">
                        <c:forEach var="user" items="${requestScope.users}">
                            <div class="user-sm edit-menu">
                                <div class="user-image-container">
                                    <img src="/load?type=user&name=${user.imageName}">
                                </div>
                                <div class="menu-container">
                                    <a href="/start?action=user_details&user=${user.id}">
                                        <h4><c:out value="${user.login}"/></h4>
                                    </a>
                                    <form id="edit-user-form-${user.id}" method="post"
                                          action="start" style="display: none">
                                        <div class="form-group">
                                            <input type="hidden" name="id" value="${user.id}"/>
                                            <label class="control-label" for="user-login-${user.id}">${login}</label>
                                            <input id="user-login-${user.id}" type="text" class="form-control"
                                                   name="login"
                                                   value="${user.login}"/>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label" for="user-password-${user.id}">${password}</label>
                                            <input id="user-password-${user.id}" type="password" class="form-control"
                                                   name="password"
                                                   value="${user.password}"/>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label" for="user-name-${user.id}">${first_name}</label>
                                            <input id="user-name-${user.id}" type="text" class="form-control"
                                                   name="name"
                                                   value="${user.name}"/>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label" for="user-surname-${user.id}">${last_name}</label>
                                            <input id="user-surname-${user.id}" type="text" class="form-control"
                                                   name="surname"
                                                   value="${user.surname}"/>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label" for="user-email-${user.id}">${email}</label>
                                            <input id="user-email-${user.id}" type="text" class="form-control"
                                                   name="email"
                                                   value="${user.email}"/>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label">${role_title}</label><br/>
                                            <label class="radio-inline"><input type="radio" name="role" value="USER"
                                                                               checked="checked">${user_role}</label>
                                            <label class="radio-inline"><input type="radio" name="role"
                                                                               value="ADMIN">${admin_role}</label>
                                        </div>
                                        <div class="form-group">
                                            <input type="hidden" name="image_name" id="image-edit-${user.id}"
                                                   value="${user.imageName}">
                                            <img id="user-image-${user.id}" class="img-thumbnail"
                                                 src="/load?type=user&name=${user.imageName}" width="100px"
                                                 height="100px">
                                            <button class="btn btn-primary" type="button"
                                                    onclick="chooseFile($('#image-load-${user.id}'))">${change_image}
                                            </button>
                                            <input type="file" id="image-load-${user.id}" style="display: none"
                                                   onfocus="this.clear()"
                                                   onchange="editUserImage($('#user-image-${user.id}'), $('#image-load-${user.id}'),$('#image-edit-${user.id}'), ${user.id})">
                                        </div>
                                        <div class="form-group">
                                            <button class="btn btn-success " type="submit" name="action"
                                                    value="edit_user">${edit_btn}
                                            </button>
                                        </div>
                                    </form>
                                    <div class="edit-buttons">
                                        <div class="delete-by-admin" onclick="showDeleteUserModal(${user.id})"></div>
                                        <div class="edit-by-admin"
                                             onclick="showEditUserModal($('#edit-user-form-${user.id}'))"></div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
</div>
</div>

</body>
</html>

<%--------------------------------------------------------------------------%>

<%--<%@ page contentType="text/html;charset=UTF-8" %>--%>
<%--<%@ page isELIgnored="false" %>--%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>--%>
<%--<html>--%>
<%--<head>--%>
<%--<title>Title</title>--%>
<%--<link rel="stylesheet" href="../styles.css">--%>
<%--</head>--%>
<%--<body>--%>
<%--<div class="user_desc">--%>
<%--<img src="/load?type=user&name=${requestScope.user.imageName}" width="200px" height="200px">--%>
<%--<form method="POST" enctype="multipart/form-data" action="start">--%>
<%--<input type="hidden" name="action" value="load_user_image">--%>
<%--<input type="file" name="upfile"><br/>--%>
<%--<input type="submit" value="Press"><br>--%>
<%--</form>--%>
<%--<c:out value="${requestScope.user.login}"/>--%>
<%--<c:out value="${requestScope.user.name}"/>--%>
<%--<c:out value="${requestScope.user.surname}"/>--%>
<%--<c:out value="${requestScope.user.email}"/>--%>
<%--<h3><c:out value="${requestScope.rate}"/></h3>--%>
<%--<c:if test="${sessionScope.user.role == 'ADMIN'}">--%>
<%--<form method="get" action="start">--%>
<%--<input type="hidden" name="action" value="admin_menu">--%>
<%--<button type="submit">Admin Menu</button>--%>
<%--</form>--%>

<%--</c:if>--%>
<%--</div>--%>

<%--<h3>Questions (<c:out value="${fn:length(questions)}"/>)</h3>--%>
<%--<c:forEach var="question" items="${requestScope.questions}">--%>
<%--<div id="user_questions">--%>
<%--<a href="/start?action=question_details&id=${question.id}">--%>
<%--<c:out value="${question.title}"/>--%>
<%--</a>--%>
<%--</div>--%>
<%--</c:forEach>--%>

<%--<h3>Answers (<c:out value="${fn:length(requestScope.answered_questions)}"/>)</h3>--%>
<%--<c:forEach var="question" items="${requestScope.answered_questions}">--%>
<%--<div id="user_answered">--%>
<%--<a href="/start?action=question_details&id=${question.id}">--%>
<%--<c:out value="${question.title}"/>--%>
<%--</a>--%>
<%--</div>--%>
<%--</c:forEach>--%>
<%--</body>--%>
<%--</html>--%>
