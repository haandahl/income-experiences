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
import java.util.List;
import java.util.Set;

@WebServlet(
        name = "assembleAdminInfo",
        urlPatterns = { "/admin"}
)
public class AssembleAdminInfo extends HttpServlet {
    private final Logger logger = LogManager.getLogger(this.getClass());

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO - improve functionality, Get all the users with edits/removals associated with their stories

        logger.debug("stuff is actually happening in the admin servlet");

        GenericDao storyDao = new GenericDao(Story.class);
        List<Story> unsuitableStories = (List<Story>)storyDao.getByPropertyName("isUnsuitable", true);

        if (unsuitableStories.size() !=0) {
            request.setAttribute("unsuitableStories", unsuitableStories);
        } else {
            request.setAttribute("unsuitableStories", null);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("./admin.jsp");
        dispatcher.forward(request, response);
    }
}
