function chooseFile(picture) {
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