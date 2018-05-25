package by.epam.like_it.controller.command.impl;

import by.epam.like_it.entity.QuestionInfoBlock;
import by.epam.like_it.entity.User;
import by.epam.like_it.exception.ServiceException;
import by.epam.like_it.service.QuAnService;
import by.epam.like_it.service.ServiceFactory;
import by.epam.like_it.controller.command.Command;
import by.epam.like_it.controller.util.KeyHolder;
import by.epam.like_it.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToUserDetailsCommand implements Command {

    private static final String USER_DETAILS_PATH = "/user";
    private static final String RATE_KEY="rate";
    private static final String TAGS_KEY="tags";
    private static final String USERS_KEY="users";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ServiceFactory factory = ServiceFactory.getInstance();
        QuAnService quAnService = factory.getQuAnService();
        UserService userService=factory.getUserService();

        int userId= Integer.parseInt(request.getParameter(KeyHolder.USER_KEY));

        try {

            User user = userService.getUserById(userId);
            User  currentUser= (User) request.getSession(true).getAttribute(KeyHolder.USER_KEY);

            if (user != null) {

                List<QuestionInfoBlock> answeredQuestionList = quAnService.getAnsweredQuestionsByUser(user);
                List<QuestionInfoBlock> questionList = quAnService.getQuestionsByUser(user);
                int rate=userService.getUserRate(user);

                request.setAttribute(KeyHolder.USER_KEY,user);
                request.setAttribute(KeyHolder.ANSWERED_QUESTION_KEY, answeredQuestionList);
                request.setAttribute(KeyHolder.QUESTIONS_KEY, questionList);
                request.setAttribute(RATE_KEY, rate);

                if ((currentUser!=null) && (user.getLogin().equals(currentUser.getLogin()) || user.getRole()==User.Role.ADMIN)){
                    request.setAttribute(TAGS_KEY,quAnService.getTags());
                    request.setAttribute(USERS_KEY,userService.getUsers());
                }

                RequestDispatcher dispatcher = request.getRequestDispatcher(USER_DETAILS_PATH);
                dispatcher.forward(request, response);

            }

        } catch (ServiceException e) {
            Logger.getLogger(getClass()).error(e.getMessage());
        }
    }
}
