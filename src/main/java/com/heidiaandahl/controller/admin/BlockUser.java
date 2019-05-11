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
 * A servlet that removes a user's "write" privilege, removes their financial story from display, and returns
 * the administrator back to the admin page, where they will see a message indicating that the removal occurred.
 *
 * @author Heidi Aandahl
 */
@WebServlet(
    name = "blockUser",
    urlPatterns = { "/block-user"}
)
public class BlockUser extends HttpServlet {
    /**
     * Removes a user's "write" privilege, removes their financial story from display, and returns
     * the administrator back to the admin page, where they will see a message indicating that the removal occurred.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException servlet exception
     * @throws IOException io exception
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // get the user to block
        int userId = Integer.parseInt(request.getParameter("user-to-block"));
        GenericDao userDao = new GenericDao(User.class);
        User userToBlock = (User) userDao.getById(userId);

        // remove write privilege and hide profile story
        userToBlock.removeRole("write");
        userToBlock.hideProfileStories();
        userDao.saveOrUpdate(userToBlock);

        // Prepare feedback for the admin
        String blockUserMessage = "Write privileges for " + userToBlock.getUsername() + " were removed and their story was hidden.";
        request.setAttribute("adminFeedbackMessage", blockUserMessage);

        RequestDispatcher dispatcher = request.getRequestDispatcher("admin");
        dispatcher.forward(request, response);
    }
}



