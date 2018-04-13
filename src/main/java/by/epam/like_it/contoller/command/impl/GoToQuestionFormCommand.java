package by.epam.like_it.contoller.command.impl;

import by.epam.like_it.contoller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToQuestionFormCommand implements Command {

    private static final String ADDING_FORM_PATH = "/asking";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        try {

            response.sendRedirect(ADDING_FORM_PATH);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
