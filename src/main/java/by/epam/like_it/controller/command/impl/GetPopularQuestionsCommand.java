package by.epam.like_it.controller.command.impl;

import by.epam.like_it.controller.command.Command;
import by.epam.like_it.controller.util.KeyHolder;
import by.epam.like_it.entity.QuestionRateInfo;
import by.epam.like_it.exception.ServiceException;
import by.epam.like_it.service.QuAnService;
import by.epam.like_it.service.ServiceFactory;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

public class GetPopularQuestionsCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        ServiceFactory factory=ServiceFactory.getInstance();
        QuAnService service=factory.getQuAnService();

        HttpSession session=request.getSession(true);
        String lang=null;
        String view= (String) request.getSession().getAttribute(KeyHolder.VIEW_KEY);

        if (view.equals(KeyHolder.LANG_VALUE)) {
            lang = (String) session.getAttribute(KeyHolder.LANG_KEY);
        }

        try {

            List<QuestionRateInfo> questionRates=service.getPopularQuestions(lang);
            Collections.reverse(questionRates);

            Gson gson=new Gson();
            String json=gson.toJson(questionRates);

            PrintWriter writer=response.getWriter();
            writer.write(json);

        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}
