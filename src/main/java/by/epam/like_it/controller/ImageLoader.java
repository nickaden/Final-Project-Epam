package by.epam.like_it.controller;

import by.epam.like_it.controller.util.KeyHolder;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


@MultipartConfig
public class ImageLoader extends HttpServlet {

    private static final String DEFAULT_USER_IMAGE = "default.png";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        String fileName = request.getParameter(KeyHolder.NAME_KEY);
        String imageType = request.getParameter(KeyHolder.TYPE_KEY);
        String filePath;

        if (imageType.equals(KeyHolder.USER_KEY)) {
            if (fileName.equals("")) {
                fileName = DEFAULT_USER_IMAGE;
            }
            filePath = request.getServletContext().getInitParameter(KeyHolder.USER_IMAGE_PATH) + "\\";
        } else {
            filePath = request.getServletContext().getInitParameter(KeyHolder.DESCRIPTION_IMAGE_PATH) + "\\";
        }

        try {
            File file = new File(filePath + fileName);
            if (!file.exists()){
                file=new File(request.getServletContext().getInitParameter(KeyHolder.ERROR_IMAGE_PATH));
            }
            FileInputStream is = new FileInputStream(file);
            OutputStream os = response.getOutputStream();

            byte[] buf = new byte[1024];
            int count = 0;
            while ((count = is.read(buf)) >= 0) {
                os.write(buf, 0, count);
            }
            os.close();
            is.close();

        } catch (IOException e) {
            Logger logger=Logger.getLogger(getClass());
            logger.warn(e.getMessage());
        }

    }

}
