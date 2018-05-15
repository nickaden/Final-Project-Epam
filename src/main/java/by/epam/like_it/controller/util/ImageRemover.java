package by.epam.like_it.controller.util;

import java.io.File;
import java.util.List;

public class ImageRemover {

    public static void deleteImages(String path, List<String> images){

        for(String image:images){
            new File(path+"\\"+image).delete();
        }
    }
}
