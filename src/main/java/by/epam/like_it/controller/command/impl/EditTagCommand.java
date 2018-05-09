package by.epam.like_it.controller.command.impl;

import by.epam.like_it.entity.Tag;
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

public class EditTagCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        Tag tag = new Tag();

        tag.setId(Integer.parseInt(request.getParameter(KeyHolder.ID_KEY)));
        tag.setTitle(request.getParameter(KeyHolder.TITLE_KEY));

        ServiceFactory factory = ServiceFactory.getInstance();
        QuAnService service = factory.getQuAnService();

        try {

            service.editTag(tag);
            response.sendRedirect(ReferenceEditor.getReference(request));

        } catch (ServiceException | IOException e) {
            Logger logger= Logger.getRootLogger();
            logger.error(e.getMessage());

        }
    }
}
