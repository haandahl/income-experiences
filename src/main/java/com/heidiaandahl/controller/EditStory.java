package com.heidiaandahl.controller;

import com.heidiaandahl.entity.Story;
import com.heidiaandahl.entity.User;
import com.heidiaandahl.persistence.GenericDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(
        name = "editStory",
        urlPatterns = { "/edit-story"}
)
public class EditStory extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // pass the old story to the profile servlet so the user can have it as a starting point for a revision
        String oldStoryContent = request.getParameter("old-story").trim();
        request.setAttribute("oldStory", oldStoryContent);

        // forward to jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("profile");
        dispatcher.forward(request, response);
    }
}

