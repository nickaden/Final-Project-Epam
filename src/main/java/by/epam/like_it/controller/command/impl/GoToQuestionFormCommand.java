package by.epam.like_it.controller.command.impl;

import by.epam.like_it.controller.command.Command;
import by.epam.like_it.controller.util.DescriptionImageParser;
import by.epam.like_it.controller.util.KeyHolder;
import by.epam.like_it.entity.QuestionInfoBlock;
import by.epam.like_it.exception.ServiceException;
import by.epam.like_it.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class GoToQuestionFormCommand implements Command {

    private static final String FORM_PATH = "/asking";
    private static final String EMPTY_STRING = "";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            QuestionInfoBlock questionInfoBlock;
            String id=request.getParameter(KeyHolder.ID_KEY);
            if (id==null || id.equals(EMPTY_STRING)){
                response.sendRedirect(FORM_PATH);
            } else {

                questionInfoBlock=ServiceFactory.getInstance().getQuAnService().getQuestionInfoBlock(Integer.parseInt(id));

                List<String> images=DescriptionImageParser.parseImages(questionInfoBlock.getQuestion().getDescription());
                request.getSession(true).setAttribute(KeyHolder.DESCRIPTION_IMAGES,images);

                request.setAttribute(KeyHolder.BLOCK_KEY,questionInfoBlock);
                RequestDispatcher dispatcher=request.getRequestDispatcher(FORM_PATH);
                dispatcher.forward(request,response);
            }

        } catch (ServiceException e) {
            Logger logger= Logger.getLogger(this.getClass());
            logger.error(e.getMessage());
        }
    }
}
