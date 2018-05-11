package by.epam.like_it.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ErrorHandler")
public class ErrorHandler extends HttpServlet {

    private static final String ERROR_PAGE_PATH="WEB-INF/error.jsp";
    private static final String EXCEPTION_ATTR="javax.servlet.error.exception";
    private static final String STATUS_CODE_ATTR="javax.servlet.error.status_code";
    private static final String SERVLET_NAME_ATTR="javax.servlet.error.servlet_name";
    private static final String UKNOWN_SERVLET="Unknown";
    private static final String REQUEST_URI_ATTR="javax.servlet.error.request_uri";
    private static final String ERROR_KEY="error";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request,response);
    }

    private void process(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

        Throwable throwable = (Throwable) request
                .getAttribute(EXCEPTION_ATTR);
        Integer statusCode = (Integer) request
                .getAttribute(STATUS_CODE_ATTR);
        String servletName = (String) request
                .getAttribute(SERVLET_NAME_ATTR);
        if (servletName == null) {
            servletName = UKNOWN_SERVLET;
        }
        String requestUri = (String) request
                .getAttribute(REQUEST_URI_ATTR);
        if (requestUri == null) {
            requestUri = UKNOWN_SERVLET;
        }

        String errorMessage="["+statusCode+"] Servlet " + servletName +
                " has thrown an exception " + throwable.getClass().getName() +
                " : " + throwable.getMessage();

        request.setAttribute(ERROR_KEY, errorMessage);
        request.getRequestDispatcher(ERROR_PAGE_PATH).forward(request, response);
    }
}
