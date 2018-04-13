package by.epam.like_it.contoller.command.impl;

import by.epam.like_it.entity.QuestionInfoBlock;
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

public class GoToQuestionEditCommand implements Command {

    private static final String INFOBLOCK_KEY = "infoblock";
    private static final String EDIT_FORM_PATH = "/edit_question";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {


        ServiceFactory factory = ServiceFactory.getInstance();
        QuAnService service = factory.getQuAnService();

        try {

            QuestionInfoBlock questionInfo = service.getQuestionInfoBlock(Integer.parseInt(request.getParameter(KeyHolder.QUESTION_KEY)));

            request.setAttribute(INFOBLOCK_KEY, questionInfo);

            RequestDispatcher dispatcher = request.getRequestDispatcher(EDIT_FORM_PATH);

            dispatcher.forward(request, response);

        } catch (ServletException | ServiceException | IOException e) {
            e.printStackTrace();
        }
    }
}
