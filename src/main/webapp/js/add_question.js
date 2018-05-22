require(['validation','style_description','navbar'], function (jVal,style_description,navbar) {

    $('#add_question_btn').click(function () {
        jVal.error = false;
        jVal.title($('#title'));
        jVal.description($('#description'));
        if (jVal.error != true) {
            this.form.submit();
        } else {
            $('.alert-hidden').fadeIn(300);
        }
        return false;
    });

    $('#preview-btn').click(function () {
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
    });

});

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