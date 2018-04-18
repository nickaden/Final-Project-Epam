package by.epam.like_it.controller.command.impl;

import by.epam.like_it.exception.ServiceException;
import by.epam.like_it.service.QuAnService;
import by.epam.like_it.service.ServiceFactory;
import by.epam.like_it.controller.command.Command;
import by.epam.like_it.controller.util.KeyHolder;
import by.epam.like_it.controller.util.ReferenceEditor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteQuestionCommand implements Command {

    private static final String START_PATH = "/start?action=question_view";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        ServiceFactory factory = ServiceFactory.getInstance();
        QuAnService service = factory.getQuAnService();

        boolean isDeleted = false;

        try {

            isDeleted = service.deleteQuestion(
                    Integer.parseInt(request.getParameter(KeyHolder.QUESTION_KEY)),
                    Integer.parseInt(request.getParameter(KeyHolder.USER_KEY))
            );

            if (isDeleted = true) {
                response.sendRedirect(START_PATH);
            } else {
                response.sendRedirect(ReferenceEditor.getReference(request));
            }

        } catch (IOException | ServiceException e) {
            e.printStackTrace();
        }
    }
}