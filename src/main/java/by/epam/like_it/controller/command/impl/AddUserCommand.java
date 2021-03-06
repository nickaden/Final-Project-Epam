package by.epam.like_it.controller.command.impl;

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
import java.time.LocalDate;

public class AddUserCommand implements Command{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

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

           service.addUser(user);

        } catch (ServiceException e) {
            Logger logger= Logger.getRootLogger();
            logger.error(e.getMessage());

        } finally {
            response.sendRedirect(ReferenceEditor.getReference(request));
        }


    }
}
