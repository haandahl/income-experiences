package com.heidiaandahl.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The purpose of this servlet is to trigger authentication. It is intended to have restricted access and be referenced
 * as a login option on the nav menu. Using this servlet in this manner avoids the 400 error that is triggered
 * when accessing the login jsp directly. The sole action of this servlet is to redirect to the home page.
 *
 * @author Heidi Aandahl
 */
@WebServlet(
    name = "logIn",
    urlPatterns = { "/login"}
)
public class LogIn extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO - consider whether this works for all scenarios.  Login should only show up on index page, so probably ok??
        response.sendRedirect("index.jsp");
    }
}



