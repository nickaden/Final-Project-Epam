$(document).ready(function () {
    var jVal = {
        'login': function (input) {
            var elem = input;
            if (elem.val().length < 4 || elem.val().length > 20) {
                jVal.error = true;
                elem.parent().removeClass('has-success').addClass('has-error');
                elem.siblings('.glyphicon-remove').show();
                elem.siblings('.glyphicon-ok').hide();
            } else {
                elem.parent().removeClass('has-error').addClass('has-success');
                elem.siblings('.glyphicon-ok').show();
                elem.siblings('.glyphicon-remove').hide();
            }
        },
        'email': function (input) {
            var elem = input;
            var pattern = /^.+@.+[.].{2,}$/i;
            if (!pattern.test(elem.val())) {
                jVal.error = true;
                elem.parent().removeClass('has-success').addClass('has-error');
                elem.siblings('.glyphicon-remove').show();
                elem.siblings('.glyphicon-ok').hide();
            } else {
                elem.parent().removeClass('has-error').addClass('has-success');
                elem.siblings('.glyphicon-ok').show();
                elem.siblings('.glyphicon-remove').hide();
            }
        },
        'firstName': function (input) {
            var elem = input;
            if (elem.val().length < 4 || elem.val().length > 20) {
                jVal.error = true;
                elem.parent().removeClass('has-success').addClass('has-error');
                elem.siblings('.glyphicon-remove').show();
                elem.siblings('.glyphicon-ok').hide();
            } else {
                elem.parent().removeClass('has-error').addClass('has-success');
                elem.siblings('.glyphicon-ok').show();
                elem.siblings('.glyphicon-remove').hide();
            }
        },
        'lastName': function (input) {
            var elem = input;
            if (elem.val().length < 4 || elem.val().length > 20) {
                jVal.error = true;
                elem.parent().removeClass('has-success').addClass('has-error');
                elem.siblings('.glyphicon-remove').show();
                elem.siblings('.glyphicon-ok').hide();
            } else {
                elem.parent().removeClass('has-error').addClass('has-success');
                elem.siblings('.glyphicon-ok').show();
                elem.siblings('.glyphicon-remove').hide();
            }
        },
        'password': function (input) {
            var elem = input;
            var pattern = /\w{4,}/;
            if (!pattern.test(elem.val())) {
                jVal.error = true;
                elem.parent().removeClass('has-success').addClass('has-error');
                elem.siblings('.glyphicon-remove').show();
                elem.siblings('.glyphicon-ok').hide();
            } else {
                elem.parent().removeClass('has-error').addClass('has-success');
                elem.siblings('.glyphicon-ok').show();
                elem.siblings('.glyphicon-remove').hide();
            }
        },
        'sendIt': function () {
            if (!jVal.error) {
                return true;
            } else {
                return false;
            }
        },
        'description': function (textarea) {
            var elem = textarea;
            if (elem.val().length == 0) {
                jVal.error = true;
                elem.parent().removeClass('has-success').addClass('has-error');
            } else {
                elem.parent().removeClass('has-error').addClass('has-success');
            }
        },
        'title': function (input) {
            var elem = input;
            if (elem.val().length < 4 || elem.val().length > 1024){
                jVal.error = true;
                elem.parent().removeClass('has-success').addClass('has-error');
            } else {
                elem.parent().removeClass('has-error').addClass('has-success');
            }
        }
    };

    $('#up_login').change(function () {
        jVal.login($(this));
    });

    $('#up_email').change(function () {
        jVal.email($(this))
    });

    $('#up_name').change(function () {
        jVal.firstName($(this))
    });

    $('#up_surname').change(function () {
        jVal.lastName($(this))
    });

    $('#up_password').change(function () {
        jVal.password($(this))
    });

    $('#sign_up_form').find('button[type="submit"]').click(function () {
        jVal.error = false;
        jVal.login($('#up_login'));
        jVal.password($('#up_password'));
        jVal.firstName($('#up_name'));
        jVal.lastName($('#up_surname'));
        jVal.email($('#up_email'));
        var sent = jVal.sendIt();
        if (sent) {
            var form = $('#sign_up_form').serialize();
            $.ajax({
                type: 'POST',
                url: 'start',
                data: form,
                success: function (data) {
                    if (data == 'error') {
                        $('.user-exist').fadeIn(300);
                    } else {
                        location.reload();
                    }
                },
                error: function (xhr, str) {
                    alert('Возникла ошибка!');
                }
            });
        }
        return false;
    })

    $('#btnLogin').click(function () {
        var form = $('#formLogin').serialize();
        $.ajax({
            type: 'POST',
            url: 'start',
            data: form,
            success: function (data) {
                if (data == "success") {
                    location.reload();
                } else {
                    $('#sign_in_warning').show();
                }
            },
            error: function (xhr, str) {
                alert('Возникла ошибка!');
            }
        });
        return false;
    });

    $('.answer-form').find('button[type="submit"]').click(function () {
        jVal.error = false;
        jVal.description($('.answer-form').find('textarea'));
        if (jVal.error != true) {
            this.form.submit();
        } else {
            $('.answer-form').find('.alert-warning').show();
        }
        return false;
    });

    $('#add_question_btn').click(function () {
        jVal.error=false;
        jVal.title($('#title'));
        jVal.description($('#description'));
        if (jVal.error!=true){
            this.form.submit();
        } else {
            $('.alert-hidden').fadeIn(300);
        }
        return false;
    });

    $('#edit-user-form-own').find('button[type="submit"]').click(function () {
        jVal.error=false;
        jVal.login($('#user-login-own'));
        jVal.password($('#user-password-own'));
        jVal.firstName($('#user-name-own'));
        jVal.lastName($('#user-surname-own'));
        jVal.email($('#user-email-own'));
        if (jVal.error!=true){
            this.form.submit();
        }
        return false;
    });

    $('#edit-user-modal').find('button[type="submit"]').click(function () {
        var form=$('#edit-user-modal').find('form');
        jVal.error=false;
        jVal.login(form.find('input[name="login"]'));
        jVal.password(form.find('input[name="password"]'));
        jVal.firstName(form.find('input[name="name"]'));
        jVal.lastName(form.find('input[name="surname"]'));
        jVal.email(form.find('input[name="email"]'));
        if (jVal.error!=true){
            this.form.submit();
        }
        return false;
    });

    $('#edit-tag-btn').click(function () {
        jVal.error=false;
        jVal.title($('#tag-title'));
        if (jVal.error != true ){
            this.form.submit();
        }
        return false;
    });

    $('#add-user').click(function () {
        $('#add-user-modal').modal();
    })

    $('#add-user-submit').click(function () {
        jVal.error=false;
        jVal.login($('#user-login'));
        jVal.password($('#user-password'));
        jVal.firstName($('#user-name'));
        jVal.lastName($('#user-surname'));
        jVal.email($('#user-email'));
        if (jVal.error!=true){
            this.form.submit();
        }
        return false;
    })

});

function loadDoc() {

    var file = document.getElementById("upfile"),
        form = new FormData(),
        up_file = file.files[0];
    form.append("upfile", up_file);
    form.append("action", "load_description_image");
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var text = '\n[img]' + this.responseText + '[/img]\n';
            document.getElementById('description_area').value =
                document.getElementById('description_area').value + text;
        }
    };

    xhttp.open("POST", "start", true);
    xhttp.send(form);
}

function view() {
    var desc_text = document.getElementById("description_area").value;
    var pos = 0;
    var isEnd = false;
    while (true) {

        var openImgPos = desc_text.indexOf("[img]", pos);
        if (openImgPos == -1) {
            var text = desc_text.substring(pos + 5, desc_text.length);
            isEnd = true;
        } else {
            var text = desc_text.substring(pos + 5, openImgPos);
        }


        var p_node = document.createElement("P");
        var p_textnode = document.createTextNode(text);
        p_node.appendChild(p_textnode);
        document.getElementById("view").appendChild(p_node);

        if (isEnd) break;

        var closeImgPos = desc_text.indexOf("[/img]", pos);
        var img_name = desc_text.substring(openImgPos + 5, closeImgPos);

        var img_node = document.createElement("IMG");
        img_node.setAttribute("src", "/load?type=description&name=" + img_name);
        document.getElementById("view").appendChild(img_node);

        pos = closeImgPos + 1;
    }
}

function vote_up(type, id, mark) {
    var msg = "action=add_mark&type=" + type + "&id=" + id + "&mark_type=up";
    $.ajax({
        type: 'POST',
        url: 'start',
        data: msg,
        dataType: 'text',
        success: function (data) {
            if (data != "error") {
                mark.css("background", "url(\"web-images/vote_up_hover.png\") no-repeat 50% 50%");
                mark.css("background-size", "contain");
                mark.siblings(".vote-down").css("background", "url(\"web-images/vote_down.png\") no-repeat 50% 50%").css("background-size", "contain");
                mark.siblings(".question-details-mark").text(data);
            }
        },
        error: function (xhr, str) {
            alert('Возникла ошибка!');
        }
    });

}

function vote_down(type, id, mark) {
    var msg = "action=add_mark&type=" + type + "&id=" + id + "&mark_type=down";
    $.ajax({
        type: 'POST',
        url: 'start',
        data: msg,
        dataType: 'text',
        success: function (data) {
            mark.css("background", "url(\"web-images/vote_down_hover.png\") no-repeat 50% 50%");
            mark.css("background-size", "contain");
            mark.siblings(".vote-up").css("background", "url(\"web-images/vote_up.png\") no-repeat 50% 50%").css("background-size", "contain");
            mark.siblings(".question-details-mark").text(data);
        },
        error: function (xhr, str) {
            alert('Возникла ошибка!');
        }
    });
}

function set_solution(questionId, answerId, solution) {
    var msg = "action=set_solution&question=" + questionId + "&answer=" + answerId;
    $.ajax({
        type: 'POST',
        url: 'start',
        data: msg,
        success: function (data) {
            $('#answers').find('.solution').css("background", "url(\"web-images/solution.png\") no-repeat 50% 50%")
                .css("background-size", "contain");
            solution.css('background', 'url("web-images/solution_hover.png") no-repeat 50% 50%')
                .css('background-size', 'contain');
        },
        error: function (xhr, str) {
            alert('Возникла ошибка!');
        }
    });
}

function style_description(block) {

    var descriptionBlock = block;
    var description = descriptionBlock.text();
    descriptionBlock.empty();
    var pos = 0;
    var newPos = 0

    while (pos <= description.length) {

        if ((newPos = description.indexOf("[img]", pos)) != -1) {

            var end = description.indexOf("[/img]", newPos);

            var tempText = description.substring(pos, newPos);
            if (tempText.length > 0) {
                var textBlock = $('<p></p>').text(tempText);
                descriptionBlock.append(textBlock);
            }


            var imgName = description.substring(newPos + 5, end);
            var imgBlock = $('<img id="preview-img">');
            imgBlock.attr('src', '/load?type=description&name=' + imgName);
            descriptionBlock.append(imgBlock);

            pos = end + 6;

        } else if ((newPos = description.indexOf("[code]", pos)) != -1) {

            var end = description.indexOf("[/code]", newPos);

            var tempText = description.substring(pos, newPos);
            if (tempText.length > 0) {
                var textBlock = $('<p></p>').text(tempText);
                descriptionBlock.append(textBlock);
            }

            var codeText = description.substring(newPos + 6, end);
            var codeBlock = $('<div class="well code"></div>');
            var preBlock = $('<pre></pre>').text(codeText);
            codeBlock.append(preBlock);
            descriptionBlock.append(codeBlock);

            pos = end + 7;
        } else {

            var text = description.substring(pos, description.length);
            var textBlock = $('<p></p>').text(text);
            descriptionBlock.append(textBlock);
            pos = description.length + 1;
        }
    }

    return descriptionBlock;

}

function chooseFile(picture) {
    var picture = picture.get(0)
    if (picture) {
        picture.click();
    } else {
        alert('Error! Input not found')
    }
}

function loadPicture() {

    var picture = document.getElementById("picture");
    var form = new FormData(),
        up_file = picture.files[0];
    form.append("upfile", up_file);
    form.append("action", "load_description_image");
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var imgText = '\n[img]' + this.responseText + '[/img]\n';
            document.getElementById('description').value += imgText;
        }
    };

    xhttp.open("POST", "start", true);
    xhttp.send(form);
}

function setCode() {
    var codeText = "\n[code]\nWrite your code here...\n[/code]\n";
    document.getElementById('description').value += codeText;
}

function showPreview() {

    $('.tags').empty();

    $('#preview').slideToggle(500);

    var title = $('.title-text').val();
    var tags = $('#tags').val();
    var date = new Date();

    var text = document.getElementById('description').value;
    var block = $('<div></div>').text(text);
    var descriptionBlock = style_description(block);

    $('#preview').find('#question-title').text(title);
    $('#preview').find('.description').html(descriptionBlock);
    $('#preview').find('.question-date').text(date.getDate() + '-' + date.getMonth() + '-' + date.getFullYear());

    tags = tags.split(' ');

    for (var i = 0; i < tags.length; i++) {
        $('.tags').append($('<div class=tag></div>').text(tags[i]));
    }
}

function showTip(type) {
    var title = $('#how-to-title');
    var format = $('#how-to-format');
    var tags = $('#how-to-tag');

    switch (type) {

        case 'title':
            title.fadeIn(500);
            format.hide();
            tags.hide();
            break;

        case 'area' :
            title.hide();
            format.fadeIn(500);
            tags.hide()
            break;

        case'tag' :
            title.hide();
            format.hide();
            tags.fadeIn(500);
            break;
    }
}

function changeLang() {

    var e = document.getElementById('lang');
    var lang = e.options[e.selectedIndex].value;

    var msg = "action=change_lang&lang=" + lang;
    $.ajax({
        type: 'POST',
        url: 'start',
        data: msg,
        success: function (data) {
            location.reload();
        },
        error: function (xhr, str) {
            alert('Возникла ошибка!');
        }
    });
}

function loadUserImage(picture, id) {

    picture = picture.get(0);
    var form = new FormData(),
        up_file = picture.files[0];
    form.append("upfile", up_file);
    form.append("user", id);
    form.append("action", "load_user_image");
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            location.reload();
        }
    };

    xhttp.open("POST", "start", true);
    xhttp.send(form);
}

function editUserImage(image, inputLoad, inputEdit, id) {

    inputLoad = inputLoad.get(0);
    var form = new FormData(),
        up_file = inputLoad.files[0];
    form.append("upfile", up_file);
    form.append("user", id);
    form.append("action", "load_user_image");
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            image.attr('src', '/load?type=user&name=' + this.responseText);
            inputEdit.val(this.responseText);
        }
    };

    xhttp.open("POST", "start", true);
    xhttp.send(form);
}

function showEditUserModal(form) {

    var modal = $('#edit-user-modal').find('.modal-body');
    modal.empty();
    modal.append(form.css('display', 'block'));
    $('#edit-user-modal').modal();

}

function showDeleteUserModal(id) {

    $('#delete-user-modal').find('#user-id').val(id);
    $('#delete-user-modal').modal();
}

function showEditTagModal(id, title) {
    $('#edit-tag-modal').find('input[name="id"]').val(id);
    $('#edit-tag-modal').find('input[name="title"]').val(title)
    $('#edit-tag-modal').modal();
}

function showDeleteTagModal(id, title) {
    $('#delete-tag-modal').find('input[name="id"]').val(id);
    $('#delete-tag-modal').find('input[name="title"]').val(title);
    $('#delete-tag-modal').modal();
}

function showDeleteAnswerModal(answer, user) {

    $('#delete-answer-modal').find('input[name="answer"]').val(answer);
    $('#delete-answer-modal').find('input[name="user"]').val(user);
    $('#delete-answer-modal').modal();
}