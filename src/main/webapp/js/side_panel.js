define(
    'side_panel',
    function () {
        $(document).ready(function () {

            var msg="action=get_popular_questions";

            $.ajax({
                type: 'GET',
                url: 'start',
                data: msg,
                success: function (data) {
                    var json=JSON.parse(data);
                    var panel=$('.side-panel');
                    var href;
                    for (var i=0; i<json.length; i++){
                        href="/start?action=question_details&id="+json[i].id;
                        var question=$('<a href='+href+'></a>');
                        question.text(json[i].title);
                        question.append($('<span class="badge"></span>').text(json[i].rate));
                        panel.append($('<p></p>').append(question));
                    }
                },
                error: function (xhr, str) {
                    alert('Возникла ошибка!');
                }
            });
        })
    }
);