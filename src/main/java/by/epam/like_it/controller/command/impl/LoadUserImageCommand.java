package by.epam.like_it.controller.command.impl;

import by.epam.like_it.controller.command.Command;
import by.epam.like_it.controller.util.KeyHolder;
import by.epam.like_it.controller.util.ReferenceEditor;
import by.epam.like_it.entity.User;
import by.epam.like_it.exception.ServiceException;
import by.epam.like_it.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class LoadUserImageCommand implements Command {



    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        try {
            Part filePart = request.getPart(KeyHolder.UPFILE_KEY);
            String fileName = filePart.getSubmittedFileName();

            File upload = new File(request.getServletContext().getInitParameter(KeyHolder.USER_IMAGE_PATH));

            String prefix = fileName.substring(0, fileName.indexOf(KeyHolder.DOT_SEPARATOR));
            String suffix = fileName.substring(fileName.indexOf(KeyHolder.DOT_SEPARATOR));
            File file = File.createTempFile(prefix, suffix, upload);

            InputStream fileContent = filePart.getInputStream();
            Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            fileContent.close();

            User user = (User) request.getSession(true).getAttribute(KeyHolder.USER_KEY);

            ServiceFactory.getInstance().getUserService().setUserImage(file.getName(),user);

            user.setImageName(file.getName());
            request.getSession(true).setAttribute(KeyHolder.USER_KEY, user);
            response.sendRedirect(ReferenceEditor.getReference(request));

        } catch (IOException | ServletException | ServiceException e) {
            e.printStackTrace();
        }
    }
}
