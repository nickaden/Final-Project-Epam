package by.epam.like_it.contoller.command.impl;

import by.epam.like_it.entity.Question;
import by.epam.like_it.entity.User;
import by.epam.like_it.exception.ServiceException;
import by.epam.like_it.service.QuAnService;
import by.epam.like_it.service.ServiceFactory;
import by.epam.like_it.contoller.command.Command;
import by.epam.like_it.contoller.util.KeyHolder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToUserDetailsCommand implements Command {

    private static final String USER_DETAILS_PATH = "/user";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        ServiceFactory factory = ServiceFactory.getInstance();
        QuAnService service = factory.getQuAnService();

        User user = (User) request.getSession(true).getAttribute(KeyHolder.USER_KEY);

        try {

            if (user != null) {

                List<Question> answeredQuestionList = service.getAnsweredQuestionsByUser(user);
                List<Question> questionList = service.getQuestionsByUser(user);
                request.setAttribute(KeyHolder.ANSWERED_QUESTION_KEY, answeredQuestionList);
                request.setAttribute(KeyHolder.QUESTIONS_KEY, questionList);
                RequestDispatcher dispatcher = request.getRequestDispatcher(USER_DETAILS_PATH);

                dispatcher.forward(request, response);

            }

        } catch (ServletException | IOException | ServiceException e) {
            e.printStackTrace();

        }
    }
}
