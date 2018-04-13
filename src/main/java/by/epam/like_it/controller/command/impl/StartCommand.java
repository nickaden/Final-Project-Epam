package by.epam.like_it.controller.command.impl;

import by.epam.like_it.controller.command.Command;
import by.epam.like_it.controller.util.KeyHolder;
import by.epam.like_it.entity.QuestionInfoBlock;
import by.epam.like_it.exception.ServiceException;
import by.epam.like_it.service.QuAnService;
import by.epam.like_it.service.ServiceFactory;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class StartCommand implements Command {

    private static final String BLOCK_LIST_KEY="blocks";
    private static final String START_PAGE_PATH="/WEB-INF/question_view.jsp";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        try {

            ServiceFactory factory=ServiceFactory.getInstance();
            QuAnService service=factory.getQuAnService();

            HttpSession session=request.getSession(true);
            String lang = (String) session.getAttribute(KeyHolder.LANG_KEY);

            List<QuestionInfoBlock> blockList=service.getQuestions(lang);
            request.setAttribute(BLOCK_LIST_KEY,blockList);
            RequestDispatcher dispatcher=request.getRequestDispatcher(START_PAGE_PATH);

            dispatcher.forward(request,response);

        } catch (ServletException | IOException | ServiceException e) {
            e.printStackTrace();
        }
    }
}
