package by.epam.like_it.contoller.command.impl;

import by.epam.like_it.entity.Tag;
import by.epam.like_it.exception.ServiceException;
import by.epam.like_it.service.QuAnService;
import by.epam.like_it.service.ServiceFactory;
import by.epam.like_it.contoller.command.Command;
import by.epam.like_it.contoller.util.KeyHolder;
import by.epam.like_it.contoller.util.ReferenceEditor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteTagCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        Tag tag=new Tag();
        tag.setId(Integer.parseInt(request.getParameter(KeyHolder.ID_KEY)));
        tag.setTitle(request.getParameter(KeyHolder.TITLE_KEY));

        ServiceFactory factory=ServiceFactory.getInstance();
        QuAnService service=factory.getQuAnService();

        try {

            service.deleteTag(tag);

            response.sendRedirect(ReferenceEditor.getReference(request));

        } catch (ServiceException | IOException e) {
            e.printStackTrace();
        }


    }
}
