package by.epam.like_it.controller.command.impl;

import by.epam.like_it.controller.util.DescriptionImageParser;
import by.epam.like_it.controller.util.ImageRemover;
import by.epam.like_it.entity.Question;
import by.epam.like_it.exception.CommandException;
import by.epam.like_it.exception.ServiceException;
import by.epam.like_it.service.QuAnService;
import by.epam.like_it.service.ServiceFactory;
import by.epam.like_it.controller.command.Command;
import by.epam.like_it.controller.util.KeyHolder;
import by.epam.like_it.controller.util.ReferenceEditor;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DeleteQuestionCommand implements Command {

    private static final String START_PATH = "/start?action=question_view";
    private static final String NOT_DELETED = "Question is not deleted";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ServiceFactory factory = ServiceFactory.getInstance();
        QuAnService service = factory.getQuAnService();

        boolean isDeleted = false;



        try {

            Question question = service.getQuestion(Integer.parseInt(request.getParameter(KeyHolder.QUESTION_KEY)));

            isDeleted = service.deleteQuestion(
                    Integer.parseInt(request.getParameter(KeyHolder.QUESTION_KEY)),
                    Integer.parseInt(request.getParameter(KeyHolder.USER_KEY))
            );

            if (isDeleted){
                ImageRemover.deleteImages(
                        request.getServletContext().getInitParameter(KeyHolder.DESCRIPTION_IMAGE_PATH),
                        DescriptionImageParser.parseImages(question.getDescription())
                );
            }

            response.sendRedirect(START_PATH);

        } catch (ServiceException e) {

            Logger logger = Logger.getRootLogger();
            logger.error(e.getMessage());

            response.sendRedirect(ReferenceEditor.getReference(request));
        }
    }
}
