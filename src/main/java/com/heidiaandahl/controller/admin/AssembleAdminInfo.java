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

        // get visible stories that have been flagged for review
        Map<String, Object> queryProperties = new HashMap<>();
        queryProperties.put("isVisible", true);
        queryProperties.put("unsuitable", true);

        List<Story> unsuitableVisibleStories = (List<Story>) storyDao.getByPropertyNames(queryProperties);

        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("itemsToReview", unsuitableVisibleStories);

        // todo - remember to notify profile user if admin has made their stuff invisble

        RequestDispatcher dispatcher = request.getRequestDispatcher("./admin.jsp");
        dispatcher.forward(request, response);
    }
}

