package by.epam.like_it.controller.command.impl;

import by.epam.like_it.entity.Answer;
import by.epam.like_it.entity.QuestionInfoBlock;
import by.epam.like_it.exception.ServiceException;
import by.epam.like_it.service.QuAnService;
import by.epam.like_it.service.ServiceFactory;
import by.epam.like_it.controller.command.Command;
import by.epam.like_it.controller.util.KeyHolder;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class QuestionDetailsCommand implements Command {

    private static final String INFO_BLOCK_KEY ="block";
    private static final String QUESTION_DETAILS_PATH="/WEB-INF/question_details.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ServiceFactory factory=ServiceFactory.getInstance();
        QuAnService service=factory.getQuAnService();

        try {

            QuestionInfoBlock questionBlock=service.getQuestionInfoBlock(Integer.parseInt(request.getParameter(KeyHolder.ID_KEY)));
            request.setAttribute(INFO_BLOCK_KEY,questionBlock);

            List<Answer> answers=service.getAnswersByQuestion(questionBlock.getQuestion());
            request.setAttribute(KeyHolder.ANSWERS_KEY,answers);

            RequestDispatcher dispatcher=request.getRequestDispatcher(QUESTION_DETAILS_PATH);
            dispatcher.forward(request,response);

        } catch (ServiceException e) {
            Logger.getLogger(getClass()).error(e.getMessage());
        }
    }
}
