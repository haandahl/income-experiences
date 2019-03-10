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
import java.io.IOException;

@WebServlet(
        name = "displayProfile",
        urlPatterns = { "/profile"}
)
public class DisplayProfile extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // changed doPost to doGet b/c it said this url does not support get... <--that worked.
        GenericDao userDao = new GenericDao(User.class);
        User exampleUser = (User)userDao.getById(1);
        request.setAttribute("user", exampleUser);

        GenericDao storyDao = new GenericDao(Story.class);


        //TODO
        // How do I get the story using a join?


        RequestDispatcher dispatcher = request.getRequestDispatcher("/profile.jsp");
        dispatcher.forward(request, response);
    }
}

