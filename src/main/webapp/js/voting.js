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
            if (data != 'error') {
                mark.css("background", "url(\"web-images/vote_down_hover.png\") no-repeat 50% 50%");
                mark.css("background-size", "contain");
                mark.siblings(".vote-up").css("background", "url(\"web-images/vote_up.png\") no-repeat 50% 50%").css("background-size", "contain");
                mark.siblings(".question-details-mark").text(data);
            }
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
                if (data == 'success') {
                    if (solution.css('background').indexOf('solution_hover.png') != -1) {
                        $('#answers').find('.solution').css("background", "url(\"web-images/solution.png\") no-repeat 50% 50%")
                            .css("background-size", "contain");
                    } else {
                        $('#answers').find('.solution').css("background", "url(\"web-images/solution.png\") no-repeat 50% 50%")
                            .css("background-size", "contain");
                        solution.css('background', "url(\"web-images/solution_hover.png\") no-repeat 50% 50%");
                    }
                    solution.css('background-size', 'contain');
                }
            },
            error:

                function (xhr, str) {
                    alert('Возникла ошибка!');
                }
        }
    )
    ;
}