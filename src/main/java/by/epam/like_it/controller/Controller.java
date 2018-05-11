package by.epam.like_it.controller;

import by.epam.like_it.controller.command.Command;
import by.epam.like_it.controller.command.CommandFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@MultipartConfig
public class Controller extends HttpServlet {

    private static final String ACTION_KEY="action";
    private static final String LOG_PROPERTIES = "log.properties";
    private static final String EMPTY_STRING = "";

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
        PropertyConfigurator.configure(path + LOG_PROPERTIES);

        Logger log = Logger.getLogger(getClass());
        log.info("Start Servlet");

        String action=req.getParameter(ACTION_KEY);
        CommandFactory commandFactory=CommandFactory.getInstance();
        Command command=commandFactory.getCommand(action);
        command.execute(req,resp);

    }
}
