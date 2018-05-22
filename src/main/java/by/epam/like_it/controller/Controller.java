package by.epam.like_it.controller;

import by.epam.like_it.controller.command.Command;
import by.epam.like_it.controller.command.CommandFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;

@MultipartConfig
public class Controller extends HttpServlet {

    private static final String ACTION_KEY="action";
    private static final String LOG_PROPERTIES = "log.properties";
    private static final String EMPTY_STRING = "";

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req,resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String path = getServletContext().getRealPath(EMPTY_STRING);
        URL logResource = Thread.currentThread().getContextClassLoader().getResource(LOG_PROPERTIES);
        PropertyConfigurator.configure(logResource);

        String action=req.getParameter(ACTION_KEY);
        CommandFactory commandFactory=CommandFactory.getInstance();
        commandFactory.loadCommands();
        Command command=commandFactory.getCommand(action);
        command.execute(req,resp);

    }
}
