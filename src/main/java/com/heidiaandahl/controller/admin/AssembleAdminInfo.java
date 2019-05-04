package com.heidiaandahl.controller.admin;

import com.heidiaandahl.entity.Story;
import com.heidiaandahl.entity.User;
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
import java.util.*;


/**
 * A servlet that gathers information for the site administrator to review when making decisions about users and content.
 *
 * @author Heidi Aandahl
 */
@WebServlet(
        name = "assembleAdminInfo",
        urlPatterns = { "/admin"}
)
public class AssembleAdminInfo extends HttpServlet {
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Finds financial stories that have been flagged unsuitable, tallies the associated user's history of
     * unsuitable stories, places the information into the session, and forwards to the admin page.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        GenericDao storyDao = new GenericDao(Story.class);

        // TODO - refactor and test single purpose methods

        // get visible stories that have been flagged for review
        Map<String, Object> queryProperties = new HashMap<>();
        queryProperties.put("isVisible", true);
        queryProperties.put("unsuitable", true);

        List<Story> unsuitableVisibleStories = (List<Story>) storyDao.getByPropertyNames(queryProperties);

        // todo - nice to do - try to order by history.

        // get the tally of the associated user's past stories that were flagged unsuitable
        Set<Map<String, Object>> historiesToReview = new TreeSet<>();

        for (Story story : unsuitableVisibleStories) {
            User profileUser = story.getProfileUser();

            Map<String, Object> tallyCriteria = new HashMap<>();
            tallyCriteria.put("profileUser", profileUser);
            tallyCriteria.put("isVisible", false);
            tallyCriteria.put("unsuitable", true);

            Long archivedUnsuitableStories = storyDao.getTallyByPropertyNames(tallyCriteria);

            Map<String, Object> historyData = new HashMap<>();
            historyData.put("currentStory", story);
            historyData.put("archivedUnsuitableStories", archivedUnsuitableStories);

            historiesToReview.add(historyData);
        }

        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("historiesToReview", historiesToReview);

        // todo - remember to notify profile user if admin has made their stuff invisble

        RequestDispatcher dispatcher = request.getRequestDispatcher("./admin.jsp");
        dispatcher.forward(request, response);
    }
}

