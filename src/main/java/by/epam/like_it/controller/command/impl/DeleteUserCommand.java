package by.epam.like_it.controller.command.impl;


import by.epam.like_it.controller.util.ImageRemover;
import by.epam.like_it.entity.User;
import by.epam.like_it.exception.ServiceException;
import by.epam.like_it.service.ServiceFactory;
import by.epam.like_it.service.UserService;
import by.epam.like_it.controller.command.Command;
import by.epam.like_it.controller.util.KeyHolder;
import by.epam.like_it.controller.util.ReferenceEditor;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteUserCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        UserService service = ServiceFactory.getInstance().getUserService();

        int userID = Integer.parseInt(request.getParameter(KeyHolder.ID_KEY));

        try {

            User user=service.getUserById(userID);

            if (service.deleteUser(userID)){
                ImageRemover.deleteImage(
                        request.getServletContext().getInitParameter(KeyHolder.USER_IMAGE_PATH),
                        user.getImageName());
            }

        } catch (ServiceException e) {
            Logger logger= Logger.getRootLogger();
            logger.error(e.getMessage());
        } finally {
            response.sendRedirect(ReferenceEditor.getReference(request));
        }


    }
}
