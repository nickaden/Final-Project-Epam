
define(
    'validation',
    function () {
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
            if (elem.val().length < 4 || elem.val().length > 1024) {
                jVal.error = true;
                elem.parent().removeClass('has-success').addClass('has-error');
            } else {
                elem.parent().removeClass('has-error').addClass('has-success');
            }
        }
    };

    return jVal;
});
