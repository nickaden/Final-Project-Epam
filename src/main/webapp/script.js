function loadDoc() {

    var file=document.getElementById("upfile"),
        form=new FormData(),
        up_file=file.files[0];
    form.append("upfile",up_file);
    form.append("action","load_description_image");
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
    var desc_text=document.getElementById("description_area").value;
    var pos=0;
    var isEnd=false;
    while(true){

        var openImgPos=desc_text.indexOf("[img]",pos);
        if (openImgPos==-1){
            var text=desc_text.substring(pos+5,desc_text.length);
            isEnd=true;
        } else {
            var text=desc_text.substring(pos+5,openImgPos);
        }


        var p_node=document.createElement("P");
        var p_textnode=document.createTextNode(text);
        p_node.appendChild(p_textnode);
        document.getElementById("view").appendChild(p_node);

        if(isEnd) break;

        var closeImgPos=desc_text.indexOf("[/img]",pos);
        var img_name=desc_text.substring(openImgPos+5,closeImgPos);

        var img_node=document.createElement("IMG");
        img_node.setAttribute("src","/load?type=description&name="+img_name);
        document.getElementById("view").appendChild(img_node);

        pos=closeImgPos+1;
    }
}

function signIn() {

    var form=new FormData();
    var login=document.getElementById("login").value;
    var password=document.getElementById("password").value;

    form.append("login",login);
    form.append("password",password);
    form.append("action","sign_in");

    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            if (this.responseText == "success"){
                location.reload();
            } else {
                var msg=document.getElementById("sign_in_warning");
                msg.innerText="Данного пользователя не существует!";
            }
        }
    };

    xhttp.open("POST", "start", true);
    xhttp.send(form);
}

function signUp() {
    var msg   = $('#sign_up_form').serialize();
    $.ajax({
        type: 'POST',
        url: 'start',
        data: msg,
        success: function(data) {
            location.reload();
        },
        error:  function(xhr, str){
            alert('Возникла ошибка!');
        }
    });
}

function vote_up(type, id) {
    var msg= "action=add_mark&type="+type+"&id="+id+"&mark_type=up";
    $.ajax({
        type: 'POST',
        url: 'start',
        data: msg,
        success: function(data) {
            location.reload();
        },
        error:  function(xhr, str){
            alert('Возникла ошибка!');
        }
    });

}

function vote_down(type,id) {
    var msg= "action=add_mark&type="+type+"&id="+id+"&mark_type=down";
    $.ajax({
        type: 'POST',
        url: 'start',
        data: msg,
        success: function(data) {
            location.reload();
        },
        error:  function(xhr, str){
            alert('Возникла ошибка!');
        }
    });
}