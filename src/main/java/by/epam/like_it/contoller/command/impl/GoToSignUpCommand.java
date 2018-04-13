package by.epam.like_it.contoller.command.impl;

import by.epam.like_it.contoller.command.Command;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToSignUpCommand implements Command {

    private static final String SIGN_UP_PATH="/sign_up";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        try {

            RequestDispatcher dispatcher=request.getRequestDispatcher(SIGN_UP_PATH);
            dispatcher.forward(request,response);

        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
