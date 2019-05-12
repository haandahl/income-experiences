package com.heidiaandahl.controller;

import com.heidiaandahl.entity.Story;
import com.heidiaandahl.persistence.GenericDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * A servlet that marks a financial story "unsuitable" based on user input. The user is directed back to the page
 * where they flagged the content and should see no changes except for cues pertaining to their flagging action.
 *
 * @author Heidi Aandahl
 */
@WebServlet(
        name = "flagContent",
        urlPatterns = { "/flag-content"}
)
public class FlagContent extends HttpServlet {

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Marks a financial story "unsuitable" based on user input. Directs the user back to the page where they flagged
     * the content without changing anything that does not pertain to their flagging action.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException servlet exception
     * @throws IOException io exception
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         // match the user-flagged story to the story in the database
        String flaggedStoryId = request.getParameter("flaggable-story");
        int flaggedStoryIdInt = Integer.parseInt(flaggedStoryId);
        GenericDao storyDao = new GenericDao(Story.class);
        Story problemStory = (Story) storyDao.getById(flaggedStoryIdInt);

        // mark the story "unsuitable" in the database
        if (!problemStory.isUnsuitable()) {
            problemStory.setUnsuitable(true);
            storyDao.saveOrUpdate(problemStory);
        }

        // update the httpSession list so the user can see that the story will be reviewed
        HttpSession httpSession = request.getSession();
        List<Story> flagOrigin = (List<Story>) httpSession.getAttribute("storiesToDisplay");

        for (Story story : flagOrigin) {
            if (story.getId() == problemStory.getId()) {
                story.setUnsuitable(true);
            }
        }

        httpSession.setAttribute("storiesToDisplay", flagOrigin);

        // redirect back to jsp so user can view same results with flag option removed as appropriate
        String url = (String) httpSession.getAttribute("returnUrl");

        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
}
