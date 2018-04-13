package by.epam.like_it.contoller.command.impl;

import by.epam.like_it.entity.Answer;
import by.epam.like_it.entity.User;
import by.epam.like_it.exception.ServiceException;
import by.epam.like_it.service.QuAnService;
import by.epam.like_it.service.ServiceFactory;
import by.epam.like_it.contoller.command.Command;
import by.epam.like_it.contoller.util.KeyHolder;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditAnswerCommand implements Command {



    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        ServiceFactory factory=ServiceFactory.getInstance();
        QuAnService service=factory.getQuAnService();

        try {
            Answer answer = service.getAnswerById(Integer.parseInt(request.getParameter(KeyHolder.ANSWER_KEY)));
            answer.setDescription(request.getParameter(KeyHolder.DESCRIPTION_KEY));

            User user = (User) request.getSession(true).getAttribute(KeyHolder.USER_KEY);

            if (user != null) {
                service.editAnswer(answer, user.getId());
            }

            response.sendRedirect(request.getParameter(KeyHolder.PATH_KEY));

        } catch (ServiceException | IOException e) {
            e.printStackTrace();
        }

    }
}
