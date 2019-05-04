package com.heidiaandahl.controller.admin;

import com.heidiaandahl.entity.Story;
import com.heidiaandahl.entity.User;
import com.heidiaandahl.persistence.GenericDao;
import com.heidiaandahl.persistence.SessionFactoryProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

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


        // Idea - display flag button to all users, so anyone can mark a story unsuitable.  Admin reviews those that
        // are unsuitable and visible.  Admin can edit story to "invisible" (should put message on user's profile page
        // explaining why no story is there).  Admin can block or remove user too.

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

