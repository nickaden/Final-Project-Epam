package by.epam.like_it.controller.filter;

import by.epam.like_it.controller.util.KeyHolder;
import by.epam.like_it.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminResponsibilityFilter implements Filter {

    private static final String ADMIN_MENU="/admin_menu";
    private static final String EDIT_USER="/edit_user";


    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        User user = (User) request.getSession(true).getAttribute(KeyHolder.USER_KEY);
        String uri=request.getRequestURI();

        if (uri.equals(EDIT_USER) || uri.equals(ADMIN_MENU)){

            if(user==null || user.getRole()!= User.Role.ADMIN){
                response.sendRedirect(KeyHolder.START_PATH);
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
