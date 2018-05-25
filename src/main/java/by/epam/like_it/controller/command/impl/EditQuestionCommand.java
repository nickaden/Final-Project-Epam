package by.epam.like_it.controller.command.impl;

import by.epam.like_it.entity.Question;
import by.epam.like_it.entity.User;
import by.epam.like_it.exception.ServiceException;
import by.epam.like_it.service.QuAnService;
import by.epam.like_it.service.ServiceFactory;
import by.epam.like_it.controller.command.Command;
import by.epam.like_it.controller.util.KeyHolder;
import org.apache.log4j.Logger;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditQuestionCommand implements Command{

    private static final String NEW_QUESTION_PATH = "/start?action=question_details&id=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Question question=new Question();
        question.setId(Integer.parseInt(request.getParameter(KeyHolder.ID_KEY)));
        question.setTitle(request.getParameter(KeyHolder.TITLE_KEY));
        question.setDescription(request.getParameter(KeyHolder.DESCRIPTION_KEY));

        User user= (User) request.getSession(true).getAttribute(KeyHolder.USER_KEY);

        ServiceFactory factory=ServiceFactory.getInstance();
        QuAnService service=factory.getQuAnService();

        String lang=(String) request.getSession(true).getAttribute(KeyHolder.LANG_KEY);

        try {

            service.editQuestion(question,request.getParameter(KeyHolder.TAGS_KEY),lang,user.getId());

        } catch (ServiceException  e) {
            Logger logger= Logger.getRootLogger();
            logger.error(e.getMessage());

        } finally {
            response.sendRedirect( NEW_QUESTION_PATH+request.getParameter(KeyHolder.ID_KEY));
        }


    }
}
