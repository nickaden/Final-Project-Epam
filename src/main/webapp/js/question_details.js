require(
    ['validation','style_description','navbar'],
    function (jVal,style_description,navbar) {

    $(document).ready(function () {
        var descriptions = $('.description');
        descriptions.each(function (i, item) {
            style_description($(item));
        });
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

});

function showDeleteAnswerModal(answer, user) {

    var modal=$('#delete-answer-modal');
    modal.find('input[name="answer"]').val(answer);
    modal.find('input[name="user"]').val(user);
    modal.modal();
}