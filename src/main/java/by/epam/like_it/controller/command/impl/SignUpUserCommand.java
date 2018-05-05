package by.epam.like_it.controller.command.impl;

import by.epam.like_it.entity.User;
import by.epam.like_it.exception.ServiceException;
import by.epam.like_it.service.ServiceFactory;
import by.epam.like_it.service.UserService;
import by.epam.like_it.controller.command.Command;
import by.epam.like_it.controller.util.KeyHolder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;


public class SignUpUserCommand implements Command {

    private static final String USER_KEY = "user";
    private static final String IS_ADDED_KEY = "isAdded";
    private static final String SIGN_UP_PATH = "/sign_up";
    private static final String START_PATH = "/start?action=question_view";
    private static final String SIGN_UP_ERROR="error";

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

        try {
            int userID = service.addUser(user);
            user.setId(userID);


            if (userID != -1) {
                HttpSession session = request.getSession(true);
                session.setAttribute(KeyHolder.USER_KEY, user);
            } else {
                PrintWriter writer=response.getWriter();
                writer.write(SIGN_UP_ERROR);
            }

        } catch (IOException |  ServiceException e) {
            e.printStackTrace();
        }
    }
}
