require(
    ['navbar'],
    function (navbar) {

        $('#view_all').change(function () {
            var msg="action=set_view&view=";
            if ($('#view_all').prop('checked')==true){
                msg+='all';
            } else {
                msg+='lang';
            }
            $.ajax({
                type: 'GET',
                url:'start',
                data:msg
            });
            location.reload();
        });

    }
);