package by.epam.like_it.controller.command.impl;

import by.epam.like_it.controller.command.Command;
import by.epam.like_it.controller.util.KeyHolder;
import by.epam.like_it.controller.util.ReferenceEditor;
import by.epam.like_it.entity.*;
import by.epam.like_it.exception.ServiceException;
import by.epam.like_it.service.QuAnService;
import by.epam.like_it.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class AddMarkCommand implements Command {

    private static final String MARK_TYPE_KEY = "mark_type";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Mark mark = new Mark();
        User user = (User) request.getSession(true).getAttribute(KeyHolder.USER_KEY);
        String type= request.getParameter(KeyHolder.TYPE_KEY);
        List<Mark> marks;

        mark.setOwnerId(user.getId());
        mark.setType(Mark.Type.valueOf(request.getParameter(MARK_TYPE_KEY).toUpperCase()));

        ServiceFactory factory = ServiceFactory.getInstance();
        QuAnService service = factory.getQuAnService();


        try {

            service.addMark(mark,type, Integer.parseInt(request.getParameter(KeyHolder.ID_KEY)));

            if (type.equals(KeyHolder.QUESTION_KEY)){
                QuestionInfoBlock question=service.getQuestionInfoBlock(Integer.parseInt(request.getParameter(KeyHolder.ID_KEY)));
                marks=question.getMarks();
            } else {
                Answer answer=service.getAnswerById(Integer.parseInt(request.getParameter(KeyHolder.ID_KEY)));
                marks=answer.getMarks();
            }

            int rate=0;
            for(Mark rateMark:marks){
                if (rateMark.getType()==Mark.Type.UP){
                    rate++;
                } else if (rateMark.getType()==Mark.Type.DOWN){
                    rate--;
                }
            }

            PrintWriter writer=response.getWriter();
            writer.write(String.valueOf(rate));
            writer.close();

        } catch (ServiceException e) {
            Logger logger= Logger.getRootLogger();
            logger.error(e.getMessage());
            PrintWriter writer=response.getWriter();
            writer.write(KeyHolder.ERROR);
            writer.close();

        } finally  {
        }


    }
}
