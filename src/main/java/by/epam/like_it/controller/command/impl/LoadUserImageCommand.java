package by.epam.like_it.controller.command.impl;

import by.epam.like_it.controller.command.Command;
import by.epam.like_it.controller.util.KeyHolder;
import by.epam.like_it.entity.User;
import by.epam.like_it.exception.ServiceException;
import by.epam.like_it.service.ServiceFactory;
import org.apache.log4j.Logger;

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

public class LoadUserImageCommand implements Command {



    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

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

            int id= Integer.parseInt(request.getParameter(KeyHolder.USER_KEY));
            User user = ServiceFactory.getInstance().getUserService().getUserById(id);

            ServiceFactory.getInstance().getUserService().setUserImage(file.getName(),user);

            User currentUser = (User) request.getSession(true).getAttribute(KeyHolder.USER_KEY);

            if (currentUser.getLogin().equals(user.getLogin())) {
                currentUser.setImageName(file.getName());
                request.getSession(true).setAttribute(KeyHolder.USER_KEY, currentUser);
            }

            PrintWriter writer=response.getWriter();
            writer.write(file.getName());

        } catch (ServiceException e) {
            Logger.getLogger(getClass()).error(e.getMessage());
        }
    }
}
