package com.heidiaandahl.controller;

import com.heidiaandahl.entity.Story;
import com.heidiaandahl.entity.User;
import com.heidiaandahl.persistence.GenericDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;

/**
 * A servlet that adds a user's financial story and hides prior stories associated with the user.
 *
 * @author Heidi Aandahl
 */

@WebServlet(
        name = "addStory",
        urlPatterns = { "/add-story"}
)
public class AddStory extends HttpServlet {
    /**
     * Adds a user's financial story, hides prior stories associated with the user, and forwards to the profile page.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException servlet exception
     * @throws IOException io exception
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO  - (future)
        //  consider api to help find "bad words"
        //  https://www.neutrinoapi.com/api/bad-word-filter/ and flag stories for review as they are added

        // Get the new story
        String newStoryContent = request.getParameter("financial-story").trim();

        // Get the previous story
        HttpSession httpSession = request.getSession();
        Story priorProfileStory = (Story) httpSession.getAttribute("profileStory");

        GenericDao storyDao = new GenericDao(Story.class);

        // Mark old story invisible
        if (priorProfileStory != null) {
            priorProfileStory.setVisible(false);
            storyDao.saveOrUpdate(priorProfileStory);
        }

        // Add the story with the current user named as the profile user and editor
        User currentUser = (User) httpSession.getAttribute("user");
        Story newStory = new Story(newStoryContent, LocalDate.now(), true, currentUser, currentUser, false);
        storyDao.insert(newStory);

        // put new story in servlet httpSession
        httpSession.setAttribute("profileStory", newStory);

        // forward to jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("profile");
        dispatcher.forward(request, response);
    }
}

