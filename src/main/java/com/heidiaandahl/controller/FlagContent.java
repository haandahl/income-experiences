package com.heidiaandahl.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "flagContent",
        urlPatterns = { "/flag-content"}
)
public class FlagContent extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // todo - complete this idea if time allows, to allow users to flag content
        // gather which story is problematic

        // gather which page the flag was viewed on - can I do that?

        // change story to unsuitable

        // create indicator for user

        // redirect back to page
    }
}
