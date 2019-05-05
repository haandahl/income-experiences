package com.heidiaandahl.controller.admin;

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
    name = "removeUser",
    urlPatterns = { "/remove-user"}
)
public class RemoveUser extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // get the user selected for removal
        int userId = Integer.parseInt(request.getParameter("user-to-remove"));
        GenericDao userDao = new GenericDao(User.class);
        User userToRemove = (User) userDao.getById(userId);

        // Prepare feedback for the admin
        String deleteUserMessage = "The site user " + userToRemove.getUsername() + " was removed.";

        // delete the user from the database  todo confirm testing?
        userDao.delete(userToRemove);

        request.setAttribute("adminFeedbackMessage", deleteUserMessage);

        RequestDispatcher dispatcher = request.getRequestDispatcher("admin");
        dispatcher.forward(request, response);

    }
}



