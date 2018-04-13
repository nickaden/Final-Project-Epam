package by.epam.like_it.contoller.filter;

import by.epam.like_it.contoller.util.KeyHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class StartFilter implements Filter {

    private static final String HOME_URI="/home";
    private static final String START_URI="/start?action=question_view";
    private static final String EN_KEY="en";

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String uri=request.getRequestURI();
        HttpSession session=request.getSession(true);
        String lang= (String) session.getAttribute(KeyHolder.LANG_KEY);

        if (lang==null){
            lang=EN_KEY;
            session.setAttribute(KeyHolder.LANG_KEY,lang);
        }

        if(uri.equals(HOME_URI)){
            response.sendRedirect(KeyHolder.START_PATH);
        }
        else {
            chain.doFilter(req, resp);
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
