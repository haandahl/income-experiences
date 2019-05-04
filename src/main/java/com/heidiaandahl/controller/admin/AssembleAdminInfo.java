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
import java.io.IOException;
import java.util.*;


@WebServlet(
        name = "assembleAdminInfo",
        urlPatterns = { "/admin"}
)
public class AssembleAdminInfo extends HttpServlet {
    private final Logger logger = LogManager.getLogger(this.getClass());

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // TODO - improve functionality, Get all the users with edits/removals associated with their stories
        GenericDao storyDao = new GenericDao(Story.class);

        // get visible stories that have been flagged for review
        Map<String, Object> queryProperties = new HashMap<>();
        queryProperties.put("isVisible", true);
        queryProperties.put("unsuitable", true);

        List<Story> unsuitableVisibleStories = (List<Story>) storyDao.getByPropertyNames(queryProperties);

        // for each unsuitable visible story - can this happen at jsp?... nah
            // get the user
            // get the tally of their invisible unsuitable stories

        // create x to hold for each "record" - content, tally (User can be gotten as property)
            // todo - try to order by history.  I want to access for each "thing" tally, Story, maybe duuplicat tallies no duplicate stories, so I want a TreeSet of maps I think

        //Set<Map<int, Object>> historiesToReview = new TreeSet<>();
        //Error:(44, 17) java: unexpected type
        //  required: reference
        //  found:    int

        for (Story story : unsuitableVisibleStories) {
            User profileUser = story.getProfileUser();
            int archivedUnsuitableStories = 0; // try get count by properties in dao?
        }

        // todo - pick up in this area.  see also steps listed on admin.jsp


        // Idea - display flag button to all users, so anyone can mark a story unsuitable.  Admin reviews those that
        // are unsuitable and visible.  Admin can edit story to "invisible" (should put message on user's profile page
        // explaining why no story is there).  Admin can block or remove user too.


        /*
        List<Story> unsuitableStories = (List<Story>)storyDao.getByPropertyName("isUnsuitable", true);


        if (unsuitableStories.size() !=0) {
            request.setAttribute("unsuitableStories", unsuitableStories);
        } else {
            request.setAttribute("unsuitableStories", null);
        }
        */
        RequestDispatcher dispatcher = request.getRequestDispatcher("./admin.jsp");
        dispatcher.forward(request, response);
    }
}

