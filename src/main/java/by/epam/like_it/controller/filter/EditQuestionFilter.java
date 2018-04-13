package by.epam.like_it.controller.filter;

import by.epam.like_it.controller.util.KeyHolder;
import by.epam.like_it.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditQuestionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        User user = (User) request.getSession(true).getAttribute(KeyHolder.USER_KEY);

        if (user == null) {
            response.sendRedirect(KeyHolder.START_PATH);
        } else {
            filterChain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {

    }
}
