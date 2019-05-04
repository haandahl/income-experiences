package com.heidiaandahl.controller;

import com.heidiaandahl.entity.Story;
import com.heidiaandahl.logic.TopicSearch;
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
 * A servlet that gets financial stories based on a user's search terms and coordinates their display back to the user.
 *
 * @author Heidi Aandahl
 */
@WebServlet(
        name = "searchExperiences",
        urlPatterns = {"/search-topics"}
)
public class SearchTopics extends HttpServlet {

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Gets financial stories based on a user's search terms and coordinates their display back to the user.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();

        // get search string
        String searchString = request.getParameter("topic");
        // TODO - (future) - modify search string to add ~2 at the end of each token for a fuzzy search

        TopicSearch search = new TopicSearch();

        List<Story> result = search.getSearchResults(searchString);

        // make info available to application so that the results page can process user input with another servlet
        if (result.size() != 0) {
            context.setAttribute("storiesToDisplay", result);
        } else {
            context.setAttribute("storiesToDisplay", null);
        }
        context.setAttribute("topic", searchString);
        context.setAttribute("returnUrl", "textResult.jsp");

        // forward info for user to view
        RequestDispatcher dispatcher = request.getRequestDispatcher("/textResult.jsp");
        dispatcher.forward(request, response);
    }
}
