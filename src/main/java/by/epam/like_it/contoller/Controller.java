package by.epam.like_it.contoller;

import by.epam.like_it.contoller.command.Command;
import by.epam.like_it.contoller.command.CommandFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {

    private static final String ACTION_KEY="action";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req,resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp){

        String action=req.getParameter(ACTION_KEY);
        CommandFactory commandFactory=CommandFactory.getInstance();
        Command command=commandFactory.getCommand(action);
        command.execute(req,resp);
    }
}
