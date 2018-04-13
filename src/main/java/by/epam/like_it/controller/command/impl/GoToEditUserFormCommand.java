package by.epam.like_it.controller.command.impl;

import by.epam.like_it.entity.User;
import by.epam.like_it.controller.command.Command;
import by.epam.like_it.controller.util.KeyHolder;
import by.epam.like_it.controller.util.ReferenceEditor;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToEditUserFormCommand implements Command {

    private static final String EDIT_FORM_PATH="/edit_user";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        User user=new User();

        user.setId(Integer.parseInt(request.getParameter(KeyHolder.ID_KEY)));
        user.setLogin(request.getParameter(KeyHolder.LOGIN_KEY));
        user.setPassword(request.getParameter(KeyHolder.PASSWORD_KEY));
        user.setName(request.getParameter(KeyHolder.NAME_KEY));
        user.setSurname(request.getParameter(KeyHolder.SURNAME_KEY));
        user.setRole(User.Role.valueOf(request.getParameter(KeyHolder.ROLE_KEY)));
        user.setEmail(request.getParameter(KeyHolder.EMAIL_KEY));

        request.setAttribute(KeyHolder.USER_KEY,user);
        request.setAttribute(KeyHolder.REFERER_KEY, ReferenceEditor.getReference(request));

        try {

            RequestDispatcher dispatcher=request.getRequestDispatcher(EDIT_FORM_PATH);
            dispatcher.forward(request,response);

        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
