require(
    ['validation', 'navbar'],
    function (jVal, navbar) {

        $('#edit-user-form-own').find('button[type="submit"]').click(function () {
            jVal.error = false;
            jVal.login($('#user-login-own'));
            jVal.password($('#user-password-own'));
            jVal.firstName($('#user-name-own'));
            jVal.lastName($('#user-surname-own'));
            jVal.email($('#user-email-own'));
            if (jVal.error != true) {
                this.form.submit();
            }
            return false;
        });

        $('#edit-user-modal').find('button[type="submit"]').click(function () {
            var form = $('#edit-user-modal').find('form');
            jVal.error = false;
            jVal.login(form.find('input[name="login"]'));
            jVal.password(form.find('input[name="password"]'));
            jVal.firstName(form.find('input[name="name"]'));
            jVal.lastName(form.find('input[name="surname"]'));
            jVal.email(form.find('input[name="email"]'));
            if (jVal.error != true) {
                this.form.submit();
            }
            return false;
        });

        $('#edit-tag-modal button[type="submit"]').click(function () {
            jVal.error = false;
            jVal.title($('#tag-title'));
            if (jVal.error != true) {
                this.form.submit();
            }
            return false;
        });

        $('#add-user').click(function () {
            $('#add-user-modal').modal();
        });

        $('#add-user-submit').click(function () {
            jVal.error = false;
            jVal.login($('#user-login'));
            jVal.password($('#user-password'));
            jVal.firstName($('#user-name'));
            jVal.lastName($('#user-surname'));
            jVal.email($('#user-email'));
            if (jVal.error != true) {
                this.form.submit();
            }
            return false;
        });

    });

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