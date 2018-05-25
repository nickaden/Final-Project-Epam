package by.epam.like_it.controller.command.impl;

import by.epam.like_it.controller.command.Command;
import by.epam.like_it.controller.util.KeyHolder;
import by.epam.like_it.entity.*;
import by.epam.like_it.exception.ServiceException;
import by.epam.like_it.service.QuAnService;
import by.epam.like_it.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AddMarkCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        User user = (User) request.getSession(true).getAttribute(KeyHolder.USER_KEY);

        if (user==null){
            response.getWriter().write(KeyHolder.ERROR);
            return;
        }

        String type= request.getParameter(KeyHolder.TYPE_KEY);

        Mark mark = new Mark();
        mark.setOwnerId(user.getId());
        mark.setType(Mark.Type.valueOf(request.getParameter(KeyHolder.MARK_TYPE_KEY).toUpperCase()));

        ServiceFactory factory = ServiceFactory.getInstance();
        QuAnService service = factory.getQuAnService();


        try {

            service.addMark(mark,type, Integer.parseInt(request.getParameter(KeyHolder.ID_KEY)));
            int rate=service.getRate(type, Integer.parseInt(request.getParameter(KeyHolder.ID_KEY)));

            PrintWriter writer=response.getWriter();
            writer.write(String.valueOf(rate));
            writer.close();

        } catch (ServiceException e) {
            Logger logger= Logger.getRootLogger();
            logger.error(e.getMessage());
            response.setStatus(KeyHolder.INTERNAL_ERROR_CODE);
        }
    }
}
