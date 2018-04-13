package by.epam.like_it.contoller.command.impl;

import by.epam.like_it.entity.Tag;
import by.epam.like_it.entity.User;
import by.epam.like_it.exception.ServiceException;
import by.epam.like_it.service.QuAnService;
import by.epam.like_it.service.ServiceFactory;
import by.epam.like_it.service.UserService;
import by.epam.like_it.contoller.command.Command;
import by.epam.like_it.contoller.util.KeyHolder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToAdminMenuCommand implements Command {

    private static final String ADMIN_MENU_PATH = "/admin_menu";
    private static final String USERS_KEY = "users";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        ServiceFactory factory = ServiceFactory.getInstance();
        UserService userService = factory.getUserService();
        QuAnService quAnService = factory.getQuAnService();


        try {

            List<User> users = userService.getUsers();
            List<Tag> tags = quAnService.getTags();


            request.setAttribute(USERS_KEY, users);
            request.setAttribute(KeyHolder.TAGS_KEY, tags);
            RequestDispatcher dispatcher = request.getRequestDispatcher(ADMIN_MENU_PATH);
            dispatcher.forward(request, response);


        } catch (ServletException | IOException | ServiceException e) {
            e.printStackTrace();
        }
    }
}
