package by.epam.like_it.controller.command.impl;

import by.epam.like_it.entity.User;
import by.epam.like_it.exception.ServiceException;
import by.epam.like_it.service.ServiceFactory;
import by.epam.like_it.service.UserService;
import by.epam.like_it.controller.command.Command;
import by.epam.like_it.controller.util.KeyHolder;
import org.apache.log4j.Logger;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditUserCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        ServiceFactory factory=ServiceFactory.getInstance();
        UserService service=factory.getUserService();

        String path=request.getParameter(KeyHolder.PATH_KEY);

        User user = new User();

        user.setId(Integer.parseInt(request.getParameter(KeyHolder.ID_KEY)));
        user.setLogin(request.getParameter(KeyHolder.LOGIN_KEY));
        user.setPassword(request.getParameter(KeyHolder.PASSWORD_KEY));
        user.setName(request.getParameter(KeyHolder.NAME_KEY));
        user.setSurname(request.getParameter(KeyHolder.SURNAME_KEY));
        user.setRole(User.Role.valueOf(request.getParameter(KeyHolder.ROLE_KEY)));
        user.setEmail(request.getParameter(KeyHolder.EMAIL_KEY));

        try {

            service.editUser(user);
            response.sendRedirect(path);

        } catch (ServiceException | IOException e) {

            Logger logger= Logger.getRootLogger();
            logger.error(e.getMessage());
        }


    }
}
