package by.epam.like_it.contoller.command.impl;

import by.epam.like_it.exception.ServiceException;
import by.epam.like_it.service.ServiceFactory;
import by.epam.like_it.service.UserService;
import by.epam.like_it.contoller.command.Command;
import by.epam.like_it.contoller.util.KeyHolder;
import by.epam.like_it.contoller.util.ReferenceEditor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteUserCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        UserService service = ServiceFactory.getInstance().getUserService();

        int userID = Integer.parseInt(request.getParameter(KeyHolder.ID_KEY));

        try {

            service.deleteUser(userID);
            response.sendRedirect(ReferenceEditor.getReference(request));

        } catch (ServiceException | IOException e) {
            e.printStackTrace();
        }


    }
}
