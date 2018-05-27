define(
    'style_description',
    function () {

        return function style_description(block) {

            var descriptionBlock = block;
            var description = descriptionBlock.text();
            descriptionBlock.empty();
            var pos = 0;
            var newPos = 0;
            var text="";

            while (pos <= description.length) {

                var part=description.substring(pos,pos+6);

                if (part.indexOf('[img]')!= -1) {

                    var end = description.indexOf("[/img]", pos);

                    var tempText = text;
                    text="";
                    var textPos=0;
                    while ((textPos=tempText.indexOf('\n')) !=-1) {

                        var textBlock = $('<p></p>').text(tempText.substring(0,textPos));
                        tempText=tempText.substring(textPos+1);
                        descriptionBlock.append(textBlock);
                    }


                    var imgName = description.substring(pos + 6, end);
                    var imgBlock = $('<img class="preview-img">');
                    imgBlock.attr('src', '/load?type=description&name=' + imgName);
                    descriptionBlock.append(imgBlock);

                    pos = end + 6;

                }
                if (part.indexOf('[code]') != -1) {

                    var end = description.indexOf("[/code]", pos);

                    var tempText = text;
                    text="";
                    var textPos=0;
                    while ((textPos=tempText.indexOf('\n')) !=-1) {

                        var textBlock = $('<p></p>').text(tempText.substring(0,textPos));
                        tempText=tempText.substring(textPos+1);
                        descriptionBlock.append(textBlock);
                    }

                    var codeText = description.substring(pos + 6, end);
                    var codeBlock = $('<div class="well code"></div>');
                    var preBlock = $('<pre></pre>').text(codeText);
                    codeBlock.append(preBlock);
                    descriptionBlock.append(codeBlock);

                    pos = end + 7;
                }
                if (pos>description.lastIndexOf('[/img]') && pos>description.lastIndexOf('[/code]')){

                    var tempText = description.substring(pos, description.length);
                    var textPos=0;
                    while ((textPos=tempText.indexOf('\n')) !=-1) {

                        var textBlock = $('<p></p>').text(tempText.substring(0,textPos));
                        tempText=tempText.substring(textPos+1);
                        descriptionBlock.append(textBlock);
                    }
                    pos = description.length + 1;
                } else {
                    text+=description.charAt(pos);
                    pos++;
                }
            }

            return descriptionBlock;

        }
});