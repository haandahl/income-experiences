package com.heidiaandahl.controller.admin;

import com.heidiaandahl.entity.Story;
import com.heidiaandahl.persistence.GenericDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A servlet that sets a story's "unsuitable" property to false and returns the administrator back to the admin page,
 * where they will see a message indicating that the change occurred.
 *
 * @author Heidi Aandahl
 */
@WebServlet(
        name = "unflagStory",
        urlPatterns = { "/unflag"}
)
public class UnflagStory extends HttpServlet {
    /**
     * Sets a story's "unsuitable" property to false and returns the administrator back to the admin page,
     * where they will see a message indicating that the change occurred.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException servlet exception
     * @throws IOException io exception
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // get the selected story
        int storyId = Integer.parseInt(request.getParameter("story-to-unflag"));
        GenericDao storyDao = new GenericDao(Story.class);
        Story storyToUnflag = (Story) storyDao.getById(storyId);

        // update the story
        storyToUnflag.setUnsuitable(false);
        storyDao.saveOrUpdate(storyToUnflag);

        // Prepare feedback for the admin
        String flagRemovalMessage = "The \"unsuitable\" flag was removed from the following story: " + storyToUnflag.getStoryContent();
        request.setAttribute("adminFeedbackMessage", flagRemovalMessage);

        RequestDispatcher dispatcher = request.getRequestDispatcher("admin");
        dispatcher.forward(request, response);
    }
}
