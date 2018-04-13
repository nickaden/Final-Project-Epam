package by.epam.like_it.contoller.command.impl;

import by.epam.like_it.exception.ServiceException;
import by.epam.like_it.service.QuAnService;
import by.epam.like_it.service.ServiceFactory;
import by.epam.like_it.contoller.command.Command;
import by.epam.like_it.contoller.util.KeyHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SetSolutionCommand implements Command {

    private static final String NEW_QUESTION_PATH = "/start?action=question_details&id=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        ServiceFactory factory = ServiceFactory.getInstance();
        QuAnService service = factory.getQuAnService();

        try {

            service.setSolution(
                    Integer.parseInt(request.getParameter(KeyHolder.QUESTION_KEY)),
                    Integer.parseInt(request.getParameter(KeyHolder.ANSWER_KEY))
            );
            response.sendRedirect(NEW_QUESTION_PATH + request.getParameter(KeyHolder.QUESTION_KEY));

        } catch (IOException | ServiceException e) {
            e.printStackTrace();
        }
    }
}
