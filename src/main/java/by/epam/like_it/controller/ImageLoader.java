package by.epam.like_it.controller;

import by.epam.like_it.controller.util.KeyHolder;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.Key;


@MultipartConfig
public class ImageLoader  extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String fileName=request.getParameter(KeyHolder.NAME_KEY);
        String imageType=request.getParameter(KeyHolder.TYPE_KEY);
        String filePath;

        if (imageType.equals(KeyHolder.USER_KEY)) {
            filePath = request.getServletContext().getInitParameter(KeyHolder.USER_IMAGE_PATH) + "\\" + fileName;
        } else {
            filePath = request.getServletContext().getInitParameter(KeyHolder.DESCRIPTION_IMAGE_PATH)+"\\"+fileName;
        }

        File file=new File(filePath);
        FileInputStream is=new FileInputStream(file);
        OutputStream os=response.getOutputStream();

        byte[] buf = new byte[1024];
        int count = 0;
        while ((count = is.read(buf)) >= 0) {
            os.write(buf, 0, count);
        }
        os.close();
        is.close();
    }

}
