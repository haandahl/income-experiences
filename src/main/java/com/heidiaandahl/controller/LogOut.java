package com.heidiaandahl.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * A servlet that logs the user out by invalidating their session.
 *
 * @author Heidi Aandahl
 */
@WebServlet(
    name = "logOut",
    urlPatterns = { "/log-out"}
)
public class LogOut extends HttpServlet {
    /**
     * Logs the user out by invalidating their session.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException servlet exception
     * @throws IOException io exception
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        session.invalidate();

        response.sendRedirect("index.jsp");
    }
}



