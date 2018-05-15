package by.epam.like_it.controller.util;

import java.util.ArrayList;
import java.util.List;

public class DescriptionImageParser {

    private static final String IMG_OPEN_TAG="[img]";
    private static final String IMG_CLOSE_TAG="[/img]";

    public static List<String> parseImages(String description){

        List<String> images=new ArrayList<>();
        StringBuffer buff=new StringBuffer(description);
        int start=0;
        int end=0;


        while ((start=buff.indexOf(IMG_OPEN_TAG)) != -1){

            end=buff.indexOf(IMG_CLOSE_TAG,start);
            String image=buff.substring(start+5,end);
            images.add(image);
            buff.delete(0,end+6);

        }

        return images;
    }
}
