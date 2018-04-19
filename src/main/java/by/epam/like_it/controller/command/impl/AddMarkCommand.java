package by.epam.like_it.controller.command.impl;

import by.epam.like_it.controller.command.Command;
import by.epam.like_it.controller.util.KeyHolder;
import by.epam.like_it.controller.util.ReferenceEditor;
import by.epam.like_it.entity.Mark;
import by.epam.like_it.entity.User;
import by.epam.like_it.exception.ServiceException;
import by.epam.like_it.service.QuAnService;
import by.epam.like_it.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddMarkCommand implements Command {

    private static final String MARK_TYPE_KEY = "mark_type";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Mark mark = new Mark();
        User user = (User) request.getSession(true).getAttribute(KeyHolder.USER_KEY);

        mark.setOwnerId(user.getId());
        mark.setType(Mark.Type.valueOf(request.getParameter(MARK_TYPE_KEY).toUpperCase()));

        ServiceFactory factory = ServiceFactory.getInstance();
        QuAnService service = factory.getQuAnService();


        try {

            service.addMark(mark, request.getParameter(KeyHolder.TYPE_KEY), Integer.parseInt(request.getParameter(KeyHolder.ID_KEY)));


        } catch (ServiceException e) {
            Logger logger= Logger.getRootLogger();
            logger.error(e.getMessage());

        } finally {
            response.sendRedirect(ReferenceEditor.getReference(request));
        }


    }
}
