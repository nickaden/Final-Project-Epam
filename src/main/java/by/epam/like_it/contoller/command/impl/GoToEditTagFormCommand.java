package by.epam.like_it.contoller.command.impl;

import by.epam.like_it.entity.Tag;
import by.epam.like_it.contoller.command.Command;
import by.epam.like_it.contoller.util.KeyHolder;
import by.epam.like_it.contoller.util.ReferenceEditor;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToEditTagFormCommand implements Command{

    private static final String EDIT_TAG_FORM_PATH="/edit_tag";
    private static final String TAG_KEY="tag";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        Tag tag=new Tag();

        tag.setId(Integer.parseInt(request.getParameter(KeyHolder.ID_KEY)));
        tag.setTitle(request.getParameter(KeyHolder.TITLE_KEY));

        request.setAttribute(TAG_KEY,tag);
        request.setAttribute(KeyHolder.PATH_KEY, ReferenceEditor.getReference(request));

        RequestDispatcher dispatcher=request.getRequestDispatcher(EDIT_TAG_FORM_PATH);

        try {

            dispatcher.forward(request,response);

        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }

    }
}
