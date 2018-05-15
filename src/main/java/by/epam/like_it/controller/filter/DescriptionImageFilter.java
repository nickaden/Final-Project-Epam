package by.epam.like_it.controller.filter;

import by.epam.like_it.controller.util.DescriptionImageParser;
import by.epam.like_it.controller.util.KeyHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class DescriptionImageFilter implements Filter {

    private List<String> editActions =new ArrayList<>();
    private String filePath;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        editActions.add("add_question");
        editActions.add("answer");
        editActions.add("edit_question");
        editActions.add("edit_answer");

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response= (HttpServletResponse) servletResponse;
        String description=request.getParameter(KeyHolder.DESCRIPTION_KEY);

        filePath=request.getServletContext().getInitParameter(KeyHolder.DESCRIPTION_IMAGE_PATH);
        List<String> images= (List<String>) request.getSession(true).getAttribute(KeyHolder.DESCRIPTION_IMAGES);

        if (description != null){

            if (images !=null  && editActions.contains(request.getParameter(KeyHolder.ACTION_KEY))){
                editImages(images,description);

            }

        }

        request.getSession(true).setAttribute(KeyHolder.DESCRIPTION_IMAGES,null);

        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }

    private void editImages(List<String> images, String description){

        for(String image: images){
            if (!description.contains(image)){
                new File(filePath + "\\" + image).delete();
            }
        }
    }
}
