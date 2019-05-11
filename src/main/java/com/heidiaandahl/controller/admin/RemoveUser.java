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

/**
 * A servlet that removes a user from the database and returns the administrator back to the admin page,
 * where they will see a message indicating that the removal occurred.
 *
 * @author Heidi Aandahl
 */
@WebServlet(
    name = "removeUser",
    urlPatterns = { "/remove-user"}
)
public class RemoveUser extends HttpServlet {
    /**
     * Removes a user from the database and returns the administrator back to the admin page,
     * where they will see a message indicating that the removal occurred.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException servlet exception
     * @throws IOException io exception
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // get the user selected for removal
        int userId = Integer.parseInt(request.getParameter("user-to-remove"));
        GenericDao userDao = new GenericDao(User.class);
        User userToRemove = (User) userDao.getById(userId);

        // delete the user from the database
        userDao.delete(userToRemove);

        // Prepare feedback for the admin
        String deleteUserMessage = "The site user " + userToRemove.getUsername() + " was removed.";
        request.setAttribute("adminFeedbackMessage", deleteUserMessage);

        RequestDispatcher dispatcher = request.getRequestDispatcher("admin");
        dispatcher.forward(request, response);
    }
}



