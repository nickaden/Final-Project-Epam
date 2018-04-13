package by.epam.like_it.contoller.command.impl;

import by.epam.like_it.entity.Question;
import by.epam.like_it.exception.ServiceException;
import by.epam.like_it.service.QuAnService;
import by.epam.like_it.service.ServiceFactory;
import by.epam.like_it.contoller.command.Command;
import by.epam.like_it.contoller.util.KeyHolder;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditQuestionCommand implements Command{

    private static final String NEW_QUESTION_PATH = "/start?action=question_details&id=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        Question question=new Question();
        question.setId(Integer.parseInt(request.getParameter(KeyHolder.ID_KEY)));
        question.setTitle(request.getParameter(KeyHolder.TITLE_KEY));
        question.setDescription(request.getParameter(KeyHolder.DESCRIPTION_KEY));

        ServiceFactory factory=ServiceFactory.getInstance();
        QuAnService service=factory.getQuAnService();

        String lang=(String) request.getSession(true).getAttribute(KeyHolder.LANG_KEY);

        try {

            service.editQuestion(question,request.getParameter(KeyHolder.TAGS_KEY),lang);
            response.sendRedirect( NEW_QUESTION_PATH+request.getParameter(KeyHolder.ID_KEY));

        } catch (ServiceException | IOException e) {
            e.printStackTrace();
        }


    }
}
