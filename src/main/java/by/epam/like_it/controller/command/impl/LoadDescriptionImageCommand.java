package by.epam.like_it.controller.command.impl;

import by.epam.like_it.controller.command.Command;
import by.epam.like_it.controller.util.KeyHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class LoadDescriptionImageCommand implements Command {

    private static final List<String> suffixes=new ArrayList<>();

    {
        suffixes.add(".jpg");
        suffixes.add(".jpeg");
        suffixes.add(".png");
        suffixes.add(".gif");
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {


            Part filePart = request.getPart(KeyHolder.UPFILE_KEY);
            String fileName = filePart.getSubmittedFileName();

            File upload = new File(request.getServletContext().getInitParameter(KeyHolder.DESCRIPTION_IMAGE_PATH));

            String prefix = fileName.substring(0, fileName.indexOf(KeyHolder.DOT_SEPARATOR));
            String suffix = fileName.substring(fileName.indexOf(KeyHolder.DOT_SEPARATOR));

            if (!suffixes.contains(suffix)) {
                return;
            }

            File file = File.createTempFile(prefix, suffix, upload);

            InputStream fileContent = filePart.getInputStream();
            Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            fileContent.close();

            PrintWriter writer=response.getWriter();
            writer.write(file.getName());
    }
}
