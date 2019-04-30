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
        // Create an object from request parameters
        String newStoryContent = request.getParameter("financial-story").trim();
        //java.lang.NullPointerException
        //	com.heidiaandahl.controller.AddStory.doPost(AddStory.java:25)
        // todo why did it work when I first submitted it, but not for the revision??
        // I get the npe when I click edit... // ah I might have it
        // todo nope this is a mess... come back

        ServletContext context = getServletContext();
        Story priorProfileStory = (Story) context.getAttribute("profileStory");

        GenericDao storyDao = new GenericDao(Story.class);


        // todo start here - what should criteria be?  Show them where user enters stuff
        // Check whether the object is complete and ok

        // Mark old story invisible
        if (priorProfileStory != null) {
            priorProfileStory.setVisible(false);
            storyDao.saveOrUpdate(priorProfileStory);  // todo is this tested?
        }

        // Get the current user
        GenericDao userDao = new GenericDao(User.class);
        String currentUsername = request.getRemoteUser();
        List<User> currentUsers = (List<User>)userDao.getByPropertyName("username", currentUsername);

        User currentUser = currentUsers.get(0);
        request.setAttribute("user", currentUser); // todo - at some point should the current user be in teh context, not the rquest?

        // Add new story
        Story newStory = new Story(newStoryContent, LocalDate.now(), true, currentUser, currentUser, false);
        storyDao.insert(newStory);

        // put new story in servlet context
        context.setAttribute("profileStory", newStory);

        // forward to jsp
       RequestDispatcher dispatcher = request.getRequestDispatcher("profile");
       dispatcher.forward(request, response);
    }
}

