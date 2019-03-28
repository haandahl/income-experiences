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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(
        name = "displayProfile",
        urlPatterns = { "/profile"}
)
public class DisplayProfile extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Access the username of the person logged in
        // Resource https://grokbase.com/t/tomcat/users/063snnw95r/get-jdbcrealms-current-user
        String currentUsername = request.getRemoteUser();  //username??
        
        GenericDao userDao = new GenericDao(User.class);

        List<User> currentUsers = (List<User>)userDao.getByPropertyName("username", currentUsername);
        
        User currentUser = currentUsers.get(0);
        request.setAttribute("user", currentUser);


        // Access the current user's financial story, if there is one and it's meant to be visible
        GenericDao storyDao = new GenericDao(Story.class);

        Map<String, Object> storyDisplayProperties = new HashMap<>();
        storyDisplayProperties.put("profileUser", currentUser);
        storyDisplayProperties.put("isVisible", true);

        List<Story> storiesList = (List<Story>)storyDao.getByPropertyNames(storyDisplayProperties);

        if (storiesList.size() != 0) {
            Story profileStory = storiesList.get(0);
            request.setAttribute("profileStory", profileStory);
        } else {
            request.setAttribute("profileStory", null);
        }

        // forward to profile jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("/profile.jsp");
        dispatcher.forward(request, response);
    }
}

