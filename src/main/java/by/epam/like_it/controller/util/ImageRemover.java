package by.epam.like_it.controller.util;

import java.io.File;
import java.util.List;

public class ImageRemover {

    public static void deleteImages(String path, List<String> images){

        for(String image:images){
            new File(path+"\\"+image).delete();
        }
    }

    public static void deleteImage(String path,String image){

        new File(path+"\\"+image).delete();
    }
}
