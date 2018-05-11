package by.epam.like_it.controller.command.impl;

import by.epam.like_it.controller.command.Command;
import by.epam.like_it.controller.util.KeyHolder;
import by.epam.like_it.entity.Question;
import by.epam.like_it.entity.QuestionInfoBlock;
import by.epam.like_it.exception.ServiceException;
import by.epam.like_it.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class GoToQuestionFormCommand implements Command {

    private static final String ADDING_FORM_PATH = "/asking";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            QuestionInfoBlock question;
            String id=request.getParameter(KeyHolder.ID_KEY);
            if (id==null || id.equals("")){
                response.sendRedirect(ADDING_FORM_PATH);
            } else {

                question=ServiceFactory.getInstance().getQuAnService().getQuestionInfoBlock(Integer.parseInt(id));

                request.setAttribute(KeyHolder.BLOCK_KEY,question);
                RequestDispatcher dispatcher=request.getRequestDispatcher(ADDING_FORM_PATH);
                dispatcher.forward(request,response);
            }

        } catch (ServiceException e) {
            Logger logger= Logger.getLogger(this.getClass());
            logger.error(e.getMessage());
        }
    }
}
