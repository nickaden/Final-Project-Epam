package by.epam.like_it.contoller.command.impl;

import by.epam.like_it.contoller.command.Command;
import by.epam.like_it.contoller.util.KeyHolder;
import by.epam.like_it.contoller.util.ReferenceEditor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SignOutCommand implements Command {


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        try {

            HttpSession session=request.getSession(true);
            session.removeAttribute(KeyHolder.USER_KEY);
            response.sendRedirect(ReferenceEditor.getReference(request));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
