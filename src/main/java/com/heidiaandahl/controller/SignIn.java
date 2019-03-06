package com.heidiaandahl.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
    name = "signIn",
    urlPatterns = { "/sign-in"}
)
public class SignIn extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Create an object from request parameters

        // Check whether the object is complete and ok

        // Do something with the object or give feedback to the user

        // Choose forward or redirect. Reminder: forward hides new location

        // TODO verify need for thrown exceptions
    }
}



