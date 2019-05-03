package com.heidiaandahl.controller;

import com.heidiaandahl.entity.Story;
import com.heidiaandahl.persistence.GenericDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         // get the problematic story
        String flaggedContent = request.getParameter("flaggable-story");

        GenericDao storyDao = new GenericDao(Story.class);
        List<Story> flaggedStories = (List<Story>) storyDao.getByPropertyName("storyContent", flaggedContent);

        ServletContext context = getServletContext();
        List<Story> flagOrigin = (List<Story>) context.getAttribute("textResult");

        // TODO refactor separate methods?

        // mark the story "unsuitable" in the database (includes stories with duplicate content if they exist)
        for (Story story : flaggedStories) {
            if (!story.isUnsuitable()) {
                story.setUnsuitable(true);
                storyDao.saveOrUpdate(story);
            }
        }

        // mark the story "unsuitable in the context list so that it is updated for the user's benefit
        for (Story story : flagOrigin) {
            if (story.getStoryContent().equals(flaggedContent)) {
                story.setUnsuitable(true);
            }
        }

        context.setAttribute("textResult", flagOrigin);

        // redirect back to jsp so user can view same results with flag option removed as appropriate
        String url = (String) context.getAttribute("returnUrl");

        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
}
