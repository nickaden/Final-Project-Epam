package by.epam.like_it.controller.command.impl;

import by.epam.like_it.controller.util.DescriptionImageParser;
import by.epam.like_it.controller.util.ImageRemover;
import by.epam.like_it.entity.Answer;
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

public class DeleteAnswerCommand implements Command {


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ServiceFactory factory = ServiceFactory.getInstance();
        QuAnService service = factory.getQuAnService();
        boolean isDeleted=false;

        try {

            Answer answer=service.getAnswerById(Integer.parseInt(request.getParameter(KeyHolder.ANSWER_KEY)));

            isDeleted=service.deleteAnswer(
                    Integer.parseInt(request.getParameter(KeyHolder.ANSWER_KEY)),
                    Integer.parseInt(request.getParameter(KeyHolder.USER_KEY))
            );

            if (isDeleted) {
                ImageRemover.deleteImages(
                        request.getServletContext().getInitParameter(KeyHolder.DESCRIPTION_IMAGE_PATH),
                        DescriptionImageParser.parseImages(answer.getDescription())
                );
            }

        } catch (ServiceException e) {

            Logger logger= Logger.getRootLogger();
            logger.error(e.getMessage());

        } finally {
            response.sendRedirect(ReferenceEditor.getReference(request));
        }


    }
}
