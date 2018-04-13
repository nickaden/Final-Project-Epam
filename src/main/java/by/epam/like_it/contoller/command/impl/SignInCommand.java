package by.epam.like_it.contoller.command.impl;

import by.epam.like_it.entity.User;
import by.epam.like_it.exception.ServiceException;
import by.epam.like_it.service.ServiceFactory;
import by.epam.like_it.service.UserService;
import by.epam.like_it.contoller.command.Command;
import by.epam.like_it.contoller.util.KeyHolder;
import by.epam.like_it.contoller.util.ReferenceEditor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SignInCommand implements Command {

    private static final String ERROR_REFER="/sign_in_error";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        try {

            ServiceFactory factory=ServiceFactory.getInstance();
            UserService service=factory.getUserService();

            User user=service.getUser(request.getParameter(KeyHolder.LOGIN_KEY),request.getParameter(KeyHolder.PASSWORD_KEY));
            HttpSession session=request.getSession();
            if (user!=null){
                session.setAttribute(KeyHolder.USER_KEY,user);
                response.sendRedirect(ReferenceEditor.getReference(request));
            } else {
                response.sendRedirect(ERROR_REFER);
            }

        } catch (IOException | ServiceException e) {
            e.printStackTrace();
        }
    }
}
