package com.heidiaandahl.controller.admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "assembleAdminInfo",
        urlPatterns = { "/admin"}
)
public class AssembleAdminInfo extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get all the users with edits/removals associated with their stories

        // Choose forward or redirect. Reminder: forward hides new location

        // TODO verify need for thrown exceptions
    }
}
