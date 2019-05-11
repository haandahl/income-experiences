package com.heidiaandahl.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A servlet that redirects the user to the home page.
 *
 * @author Heidi Aandahl
 */
@WebServlet(
        name = "redirector",
        urlPatterns = { "/redirect"}
)
public class Redirector extends HttpServlet{
    /**
     * Redirects the user to the home page.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException servlet exception
     * @throws IOException io exception
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("./index.jsp");
    }
}
