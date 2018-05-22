package by.epam.like_it.controller.command.impl;

import by.epam.like_it.controller.util.ReferenceEditor;
import by.epam.like_it.entity.User;
import by.epam.like_it.exception.ServiceException;
import by.epam.like_it.service.ServiceFactory;
import by.epam.like_it.service.UserService;
import by.epam.like_it.controller.command.Command;
import by.epam.like_it.controller.util.KeyHolder;
import org.apache.log4j.Logger;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class EditUserCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ServiceFactory factory=ServiceFactory.getInstance();
        UserService service=factory.getUserService();

        String path=ReferenceEditor.getReference(request);

        User user = new User();

        user.setId(Integer.parseInt(request.getParameter(KeyHolder.ID_KEY)));
        user.setLogin(request.getParameter(KeyHolder.LOGIN_KEY));
        user.setPassword(request.getParameter(KeyHolder.PASSWORD_KEY));
        user.setName(request.getParameter(KeyHolder.NAME_KEY));
        user.setSurname(request.getParameter(KeyHolder.SURNAME_KEY));
        user.setRole(User.Role.valueOf(request.getParameter(KeyHolder.ROLE_KEY)));
        user.setEmail(request.getParameter(KeyHolder.EMAIL_KEY));
        user.setImageName(request.getParameter(KeyHolder.IMAGE_NAME_KEY));

        try {

            service.editUser(user);

            HttpSession session=request.getSession(true);
            User currentUser= (User) session.getAttribute(KeyHolder.USER_KEY);
            if(currentUser != null && currentUser.getId()== user.getId()){
                session.setAttribute(KeyHolder.USER_KEY,user);
            }

        } catch (ServiceException e) {

            Logger logger= Logger.getRootLogger();
            logger.error(e.getMessage());

        } finally {
            response.sendRedirect(path);
        }


    }
}
