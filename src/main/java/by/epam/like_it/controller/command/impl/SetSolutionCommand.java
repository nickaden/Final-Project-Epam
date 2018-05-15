package by.epam.like_it.controller.command.impl;

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
import java.io.PrintWriter;


public class SetSolutionCommand implements Command {

    private static final String NEW_QUESTION_PATH = "/start?action=question_details&id=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ServiceFactory factory = ServiceFactory.getInstance();
        QuAnService service = factory.getQuAnService();
        PrintWriter writer=response.getWriter();

        try {

            User user= (User) request.getSession(true).getAttribute(KeyHolder.USER_KEY);

            if (user == null){
                response.getWriter().write(KeyHolder.ERROR);
                return;
            }

            service.setSolution(
                    Integer.parseInt(request.getParameter(KeyHolder.QUESTION_KEY)),
                    Integer.parseInt(request.getParameter(KeyHolder.ANSWER_KEY)),
                    user
            );

            writer=response.getWriter();
            writer.write(KeyHolder.SUCCESS);

        } catch (ServiceException e) {
            Logger.getLogger(getClass()).error(e.getMessage());
            writer.write(KeyHolder.ERROR);
        } finally {
            writer.close();
        }
    }
}
