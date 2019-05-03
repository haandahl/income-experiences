package com.heidiaandahl.controller;

import com.heidiaandahl.entity.Story;
import com.heidiaandahl.entity.User;
import com.heidiaandahl.logic.TopicSearch;
import com.heidiaandahl.persistence.SessionFactoryProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.search.*;
import org.hibernate.search.query.dsl.QueryBuilder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(
        name = "searchExperiences",
        urlPatterns = {"/search-topics"}
)
public class SearchTopics extends HttpServlet {

    private final Logger logger = LogManager.getLogger(this.getClass());

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // get search string
        String searchString = request.getParameter("topic");
        // TODO - (future) - modify search string to add ~2 at the end of each token for a fuzzy search
        TopicSearch search = new TopicSearch();

        List result = search.getSearchResults(searchString);

        // send info to results page
        if (result.size() != 0) {
            request.setAttribute("textResult", result);
        } else {
            request.setAttribute("textResult", null);
        }

        request.setAttribute("topic", searchString);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/textResult.jsp");
        dispatcher.forward(request, response);
    }
}
