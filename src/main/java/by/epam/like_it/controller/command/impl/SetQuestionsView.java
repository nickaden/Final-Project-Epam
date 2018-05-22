package by.epam.like_it.controller.command.impl;

import by.epam.like_it.controller.command.Command;
import by.epam.like_it.controller.util.KeyHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SetQuestionsView implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        request.getSession(true).setAttribute(
                KeyHolder.VIEW_KEY,
                request.getParameter(KeyHolder.VIEW_KEY));

        PrintWriter writer=response.getWriter();
        writer.write(KeyHolder.SUCCESS);
    }
}
