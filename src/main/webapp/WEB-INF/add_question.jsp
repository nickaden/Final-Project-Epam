<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
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
                    <c:set var="rus" value="selected"/>
                    <c:if test="${sessionScope.lang eq 'en'}">
                        <c:set var="eng" value="selected"/>
                        <c:set var="rus" value=""/>
                    </c:if>
                    <select class="form-control" id="lang" name="lang" onchange="changeLang()">
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
                                <a href="/start?action=user_details&user=${sessionScope.user.id}">
                                    <c:out value="${sessionScope.user.login}"/>
                                </a>
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
    <div class="col-md-8">
        <form class="form-horizontal" method="post" action="start">
            <input type="hidden" name="action" value="add_question">
            <div class="form-group">
                <label class="control-label title-label" for="title">Заголовок</label>
                <input type="text" class="form-control title-text" id="title" placeholder="Введите заголовок"
                       name="title" onfocus="showTip('title')" onload="showTip('title')">
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
                <label class="control-label" for="tags">Тэги</label>
                <input type="text" name="tags" class="form-control tag-text" id="tags"
                       placeholder="Введите название тэгов через пробел..." onfocus="showTip('tag')">
            </div>
            <button class="btn btn-info" type="button" onclick="showPreview()">Показать
                превью
            </button>
            <button class="btn btn-success" type="submit">Готово</button>
        </form>
    </div>
    <div class="col-md-4">
        <div class="panel panel-default side-panel">
            <div class="help-tip" id="how-to-title">
                <h4><b>Как задавать вопросы</b></h4>
                <p>Мы предпочитаем вопросы, на которые можно дать конкретные ответы, а не те, которые порождают
                    дискуссию.</p>
                <p>Предоставьте как можно больше деталей, поделитесь проделанным исследованием.</p>
            </div>
            <div class="help-tip" id="how-to-format" style="display: none">
                <h4><b>Как форматировать вопрос?</b></h4>
                <p>Сообщество Like-IT постаралось, чтобы ваши ответы были наглядны и быстро осознавались пользователями.
                    Поэтому мы добавили возможность добавления изображений и специальное отображение кода</p>
                <p>Чтобы добавить изображение, щелкните по соответствующей иконке вверху области заполнения. Изображение
                    добавиться в конец формы</p>
                <p>Чтобы отформатировать область с кодом, добавьте специальные обозначения [code]...[/code] или нажмите
                    на соответствующую иконку вверху формы</p>
            </div>
            <div class="help-tip" id="how-to-tag" style="display: none">
                <h4><b>Зачем нужны тэги?</b></h4>
                <p>Метка — это ключевое слово, позволяющее отнести ваш вопрос в одну категорию с другими похожими
                    вопросами. Выберите одну или более (до 5) меток, которые помогут отвечающим найти и осмыслить ваш
                    вопрос.</p>
                <p>объедините несколько слов в составное слово с помощью дефиса (например, база-данных) длиной до 20
                    символов включительно</p>
            </div>
        </div>
    </div>
    <div class="col-md-9">
        <div id="preview" class="collapse"></div>
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
