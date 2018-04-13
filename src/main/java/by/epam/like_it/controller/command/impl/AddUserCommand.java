package by.epam.like_it.controller.command.impl;

import by.epam.like_it.entity.User;
import by.epam.like_it.exception.ServiceException;
import by.epam.like_it.service.ServiceFactory;
import by.epam.like_it.service.UserService;
import by.epam.like_it.controller.command.Command;
import by.epam.like_it.controller.util.KeyHolder;
import by.epam.like_it.controller.util.ReferenceEditor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

public class AddUserCommand implements Command{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        ServiceFactory factory = ServiceFactory.getInstance();
        UserService service = factory.getUserService();

        User user = new User();
        user.setLogin(request.getParameter(KeyHolder.LOGIN_KEY));
        user.setPassword(request.getParameter(KeyHolder.PASSWORD_KEY));
        user.setRegDate(LocalDate.now());
        user.setName(request.getParameter(KeyHolder.NAME_KEY));
        user.setSurname(request.getParameter(KeyHolder.SURNAME_KEY));
        user.setEmail(request.getParameter(KeyHolder.EMAIL_KEY));
        user.setRole(User.Role.valueOf(request.getParameter(KeyHolder.ROLE_KEY)));

        try {

            int userID=service.addUser(user);

                response.sendRedirect(ReferenceEditor.getReference(request));

        } catch (ServiceException | IOException e) {
            e.printStackTrace();
        }


    }
}
