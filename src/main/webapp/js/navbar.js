define(
    'navbar',
    ['validation'], function (jVal) {
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
        });

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

        $('.lang-option').click(function () {

            var lang=$(this).attr('data');

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

        });

        return null;
    });

