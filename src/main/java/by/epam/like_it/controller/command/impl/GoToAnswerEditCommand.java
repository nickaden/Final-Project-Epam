package by.epam.like_it.controller.command.impl;

import by.epam.like_it.entity.Answer;
import by.epam.like_it.exception.ServiceException;
import by.epam.like_it.service.QuAnService;
import by.epam.like_it.service.ServiceFactory;
import by.epam.like_it.controller.command.Command;
import by.epam.like_it.controller.util.KeyHolder;
import by.epam.like_it.controller.util.ReferenceEditor;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToAnswerEditCommand implements Command{

    private static final String EDIT_FORM_PATH="/edit_answer";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {

            ServiceFactory factory=ServiceFactory.getInstance();
            QuAnService service=factory.getQuAnService();

            Answer answer=service.getAnswerById(Integer.parseInt(request.getParameter(KeyHolder.ANSWER_KEY)));
            request.setAttribute(KeyHolder.ANSWER_KEY, answer);
            request.setAttribute(KeyHolder.PATH_KEY, ReferenceEditor.getReference(request));

            RequestDispatcher dispatcher=request.getRequestDispatcher(EDIT_FORM_PATH);

            dispatcher.forward(request,response);


        } catch (ServletException | ServiceException | IOException e) {

            Logger logger= Logger.getRootLogger();
            logger.error(e.getMessage());
        }
    }
}
