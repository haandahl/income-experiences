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
        name = "addStory",
        urlPatterns = { "/add-story"}
)
public class AddStory extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO  - (future) - consider api to help find "bad words"
        //  https://www.neutrinoapi.com/api/bad-word-filter/ and flag stories for review as they are added

        // Create an object from request parameters
        String newStoryContent = request.getParameter("financial-story").trim();

        ServletContext context = getServletContext();
        Story priorProfileStory = (Story) context.getAttribute("profileStory");

        GenericDao storyDao = new GenericDao(Story.class);

        // todo start here - what should criteria be?  Show them where user enters stuff
        // Check whether the object is complete and ok
        // 100 characters minimum
        // I should then allow a user to not-display their story if they don't want to replace it with something.
        // Also test what happens with really long story.


        // Mark old story invisible
        if (priorProfileStory != null) {
            priorProfileStory.setVisible(false);
            storyDao.saveOrUpdate(priorProfileStory);  // todo is this tested?
        }

        // Add the story with the current user named as the profile user and editor
        // TODO - (future) - update code to separately identify profile user and editor
        User currentUser = (User) context.getAttribute("user");
        Story newStory = new Story(newStoryContent, LocalDate.now(), true, currentUser, currentUser, false);
        storyDao.insert(newStory);

        // put new story in servlet context
        context.setAttribute("profileStory", newStory);

        // forward to jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("profile");
        dispatcher.forward(request, response);
    }
}

