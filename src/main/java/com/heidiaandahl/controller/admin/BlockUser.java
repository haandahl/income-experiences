package com.heidiaandahl.controller.admin;

import com.heidiaandahl.entity.Role;
import com.heidiaandahl.entity.Story;
import com.heidiaandahl.entity.User;
import com.heidiaandahl.persistence.GenericDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // get the user to block
        int userId = Integer.parseInt(request.getParameter("user-to-block"));
        GenericDao userDao = new GenericDao(User.class);
        User userToBlock = (User) userDao.getById(userId);

        // remove the user's write privilege
        userToBlock.removeRole("write");


        // ensure none of the user's stories are visible - todo - move to user entity
        Set<Story> storiesToHide = userToBlock.getStoryVersionsForUserProfile();

        for (Story story : storiesToHide) {
            if (story.isVisible()) {
                story.setVisible(false);
            }
        }

        userDao.saveOrUpdate(userToBlock);


        // Choose forward or redirect. Reminder: forward hides new location

        // TODO - place an indicator on the profile page when a user has lost write privileges
    }
}



