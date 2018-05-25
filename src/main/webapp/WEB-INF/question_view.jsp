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
<fmt:message bundle="${loc}" key="question.view_all_msg" var="view_all_msg"/>
<fmt:message bundle="${loc}" key="authorization.error" var="auth_error"/>
<fmt:message bundle="${loc}" key="authorization.error_info" var="auth_error_info"/>
<fmt:message bundle="${loc}" key="authorization.sign_in_warning" var="sign_in_warning"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${headTitle}</title>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="bootstrap-3.3.7-dist/css/bootstrap.min.css">


    <script src="jquery-3.3.1.min.js"></script>
    <!-- Latest compiled JavaScript -->
    <script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>


    <script src="js/require.js" data-main="js/question_view"></script>
    <link rel="stylesheet" href="css/general.css">
    <link rel="stylesheet" href="css/navbar.css">
    <link rel="stylesheet" href="css/question.css">

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
                        <li><a href="#" class="lang-option" data="en">${en_option}</a></li>
                        <li><a href="#" class="lang-option" data="ru">${ru_option}</a></li>
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
                                    <p class="help-block" id="sign_in_warning">${sign_in_warning}</p>
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
                                        <button id="signOut" class="btn btn-info btn-sm"
                                                type="submit">${sign_out}</button>
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
                <form method="post" id="sign_up_form" class="form-horizontal">
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
                    <div class="alert alert-danger alert-hidden user-exist">
                        <strong>${auth_error}</strong>${auth_error_info}
                    </div>
                    <button class="btn btn-success" type="submit">${confirm}</button>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="content container">
    <div class="button-space row">
        <div class="col-sm-10">
            <div class="checkbox">
                <c:set var="check" value=""/>
                <c:if test="${sessionScope.view eq 'all'}">
                    <c:set var="check" value="checked"/>
                </c:if>
                <label><input type="checkbox" id="view_all" ${check}>${view_all_msg}</label>
            </div>
        </div>
        <div class="col-sm-2">
            <c:if test="${sessionScope.user != null}">
                <a href="/start?action=ask">
                    <button class="btn btn-primary" id="asking-button">${ask_button}</button>
                </a>
            </c:if>
        </div>
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
                                value="${block.owner.login}"/></a></div>
                    </div>
                </div>
            </c:forEach>
            <c:if test="${noOfPages>1}">
                <ul class="pagination pagination-sm">
                <c:set var="begin" value="1"/>
                <c:set var="end" value="${noOfPages}"/>
                <c:if test="${currentPage-2 gt begin}">
                    <c:set var="begin" value="${currentPage-2}"/>
                </c:if>
                <c:if test="${currentPage+2 lt end}">
                    <c:set var="end" value="${currentPage+2}"/>
                </c:if>


                <c:if test="${currentPage != 1}">
                    <li><a href="/start?action=question_view&page=${currentPage-1}">Prev</a></li>
                </c:if>

                <c:forEach var="i" begin="${begin}" end="${end}">
                    <c:choose>
                        <c:when test="${currentPage eq i}">
                            <c:set var="isActive" value="active"/>
                            <li class="${isActive}">
                                <a href="">${i}</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <c:set var="isActive" value=""/>
                            <li class="">
                                <a href="/start?action=question_view&page=${i}">${i}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:if test="${currentPage lt noOfPages}">
                    <li><a href="/start?action=question_view&page=${currentPage+1}">Next</a></li>
                </c:if>
            </ul>
            </c:if>
        </div>
        <div class="sidebar col-md-3">
            <div class="panel panel-default side-panel">
            </div>
        </div>
    </div>
</div>
</body>
</html>


