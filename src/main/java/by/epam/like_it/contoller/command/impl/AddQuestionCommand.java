package by.epam.like_it.contoller.command.impl;


import by.epam.like_it.entity.Question;
import by.epam.like_it.entity.User;
import by.epam.like_it.exception.ServiceException;
import by.epam.like_it.service.QuAnService;
import by.epam.like_it.service.ServiceFactory;
import by.epam.like_it.contoller.command.Command;
import by.epam.like_it.contoller.util.KeyHolder;
import by.epam.like_it.contoller.util.ReferenceEditor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddQuestionCommand implements Command {

    private static final String TAGS_KEY = "tags";
    private static final String NEW_QUESTION_PATH = "/start?action=question_details&id=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        Question question = new Question();

        try {

            question.setTitle(request.getParameter(KeyHolder.TITLE_KEY));
            question.setDescription(request.getParameter(KeyHolder.DESCRIPTION_KEY));

            ServiceFactory factory = ServiceFactory.getInstance();
            QuAnService service = factory.getQuAnService();
            User owner = (User) request.getSession(true).getAttribute(KeyHolder.USER_KEY);
            String lang=(String) request.getSession(true).getAttribute(KeyHolder.LANG_KEY);

            int questionID = service.addQuestion(question, owner, lang, request.getParameter(KeyHolder.TAGS_KEY));

            if (questionID != -1) {
                response.sendRedirect(NEW_QUESTION_PATH + String.valueOf(questionID));
            } else {
                response.sendRedirect(ReferenceEditor.getReference(request));
            }

        } catch (IOException | ServiceException e) {
            e.printStackTrace();
        }
    }
}
