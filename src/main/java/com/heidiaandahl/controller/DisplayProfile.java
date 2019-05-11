package com.heidiaandahl.controller;

import com.heidiaandahl.entity.Story;
import com.heidiaandahl.entity.Survey;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A servlet that displays the profile of the current user for the session.
 *
 * @author Heidi Aandahl
 */
@WebServlet(
        name = "displayProfile",
        urlPatterns = { "/profile"}
)
public class DisplayProfile extends HttpServlet {
    /**
     * Gathers data about the current user, sets it to the session, and forwards to the profile page.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException servlet exception
     * @throws IOException io exception
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();

        // get the current user
        User currentUser = (User) httpSession.getAttribute("user");

        if (currentUser == null) {
            // Access the username of the person logged in
            // Resource https://grokbase.com/t/tomcat/users/063snnw95r/get-jdbcrealms-current-user
            String currentUsername = request.getRemoteUser();
            GenericDao userDao = new GenericDao(User.class);
            List<User> currentUsers = (List<User>)userDao.getByPropertyName("username", currentUsername);
            currentUser = currentUsers.get(0);
            httpSession.setAttribute("user", currentUser);
        }

         setSessionSurvey(httpSession, currentUser);

        // Access the current user's financial story, if there is one and it's meant to be visible
        GenericDao storyDao = new GenericDao(Story.class);

        Map<String, Object> storyDisplayProperties = new HashMap<>();
        storyDisplayProperties.put("profileUser", currentUser);
        storyDisplayProperties.put("isVisible", true);

        List<Story> storiesList = (List<Story>)storyDao.getByPropertyNames(storyDisplayProperties);

        if (storiesList.size() != 0) {
            Story profileStory = storiesList.get(0);
            httpSession.setAttribute("profileStory", profileStory);
        } else {
            httpSession.setAttribute("profileStory",null);
        }

        // forward to profile jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("/profile.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Gathers data about the current user, sets it to the session, and forwards to the profile page.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException servlet exception
     * @throws IOException io exception
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Note: this point should not be reachable without having already set the user, survey, and story to the session with doPost
        RequestDispatcher dispatcher = request.getRequestDispatcher("/profile.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Gets the survey for the current user and places it into the session.
     *
     * @param httpSession the session
     * @param currentUser the site user for the session
     */
    private void setSessionSurvey(HttpSession httpSession, User currentUser) {
        // Access the current user's financial survey
        // TODO - (future) - if future improvements allow annual surveys, revise this to retrieve only the most recent.
        GenericDao surveyDao = new GenericDao(Survey.class);

        Map<String, Object> surveyCriteria = new HashMap<>();
        surveyCriteria.put("participant", currentUser);
        List<Survey> currentSurveys = (List<Survey>)surveyDao.getByPropertyNames(surveyCriteria);

        if (currentSurveys.size() != 0) {
            Survey currentSurvey = currentSurveys.get(0);
            httpSession.setAttribute("survey", currentSurvey);
        } else {
            httpSession.setAttribute("survey", null);
        }
    }
}

