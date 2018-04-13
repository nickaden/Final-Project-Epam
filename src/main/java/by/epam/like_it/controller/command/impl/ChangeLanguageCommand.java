package by.epam.like_it.controller.command.impl;

import by.epam.like_it.controller.command.Command;
import by.epam.like_it.controller.util.KeyHolder;
import by.epam.like_it.controller.util.ReferenceEditor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ChangeLanguageCommand implements Command{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session=request.getSession(false);
        session.setAttribute(KeyHolder.LANG_KEY,request.getParameter(KeyHolder.LANG_KEY));

        try {
            response.sendRedirect(ReferenceEditor.getReference(request));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
