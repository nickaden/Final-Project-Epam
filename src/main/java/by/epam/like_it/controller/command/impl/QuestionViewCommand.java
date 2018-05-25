package by.epam.like_it.controller.command.impl;

import by.epam.like_it.controller.command.Command;
import by.epam.like_it.controller.util.KeyHolder;
import by.epam.like_it.entity.QuestionInfoBlock;
import by.epam.like_it.exception.ServiceException;
import by.epam.like_it.service.QuAnService;
import by.epam.like_it.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class QuestionViewCommand implements Command {

    private static final String BLOCK_LIST_KEY="blocks";
    private static final String VIEW_KEY="view";
    private static final String ALL_VALUE="all";
    private static final String CURRENT_PAGE_KEY="currentPage";
    private static final String NO_OF_PAGES="noOfPages";
    private static final String START_PAGE_PATH="/WEB-INF/question_view.jsp";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            ServiceFactory factory=ServiceFactory.getInstance();
            QuAnService service=factory.getQuAnService();

            HttpSession session=request.getSession(true);
            String lang=null;
            int page=0;
            String view= (String) request.getSession().getAttribute(KeyHolder.VIEW_KEY);

            if(request.getParameter(KeyHolder.PAGE_KEY)==null){
                page=1;
            } else {
                page = Integer.parseInt(request.getParameter(KeyHolder.PAGE_KEY));
            }

            if (view.equals(KeyHolder.LANG_VALUE)) {
                lang = (String) session.getAttribute(KeyHolder.LANG_KEY);
            }

            List<QuestionInfoBlock> blockList=service.getQuestions(lang,page);
            int noOfPages=service.getPageCount(lang);

            request.setAttribute(BLOCK_LIST_KEY,blockList);
            request.setAttribute(NO_OF_PAGES,noOfPages);
            request.setAttribute(CURRENT_PAGE_KEY,page);
            RequestDispatcher dispatcher=request.getRequestDispatcher(START_PAGE_PATH);

            dispatcher.forward(request,response);

        } catch (ServiceException e) {
            Logger.getLogger(getClass()).error(e.getMessage());
        }
    }
}
