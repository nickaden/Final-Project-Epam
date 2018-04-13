package by.epam.like_it.contoller.command.impl;

import by.epam.like_it.contoller.command.Command;
import by.epam.like_it.contoller.util.KeyHolder;
import by.epam.like_it.entity.Answer;
import by.epam.like_it.entity.User;
import by.epam.like_it.exception.ServiceException;
import by.epam.like_it.service.QuAnService;
import by.epam.like_it.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddAnswerCommand implements Command {

    private static final String NEW_QUESTION_PATH = "/start?action=question_details&id=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        Answer answer=new Answer();
        User user=(User) request.getSession(true).getAttribute(KeyHolder.USER_KEY);
        answer.setOwner(user);
        answer.setDescription(request.getParameter(KeyHolder.DESCRIPTION_KEY));

        ServiceFactory factory=ServiceFactory.getInstance();
        QuAnService service=factory.getQuAnService();

        try {
            service.addAnswer(answer,Integer.parseInt(request.getParameter(KeyHolder.QUESTION_KEY)));


                response.sendRedirect(NEW_QUESTION_PATH+request.getParameter(KeyHolder.QUESTION_KEY));


        } catch (ServiceException | IOException e) {
            e.printStackTrace();
        }


    }
}
