package com.heidiaandahl.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A servlet that makes a user's prior story available as a starting point when they want to edit their story.
 *
 * @author Heidi Aandahl
 */
@WebServlet(
        name = "editStory",
        urlPatterns = { "/edit-story"}
)
public class EditStory extends HttpServlet {
    /**
     * Makes a user's prior story available as a starting point when they want to edit their story.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException servlet exception
     * @throws IOException io exception
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // pass the old story to the profile servlet so the user can have it as a starting point for a revision
        String oldStoryContent = request.getParameter("old-story").trim();
        request.setAttribute("oldStory", oldStoryContent);

        // forward to the profile servlet
        RequestDispatcher dispatcher = request.getRequestDispatcher("profile");
        dispatcher.forward(request, response);
    }
}

