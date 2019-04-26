package com.heidiaandahl.controller;

import com.heidiaandahl.entity.User;
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

        // TODO - nice to do - modify search string to add ~2 at the end of each token

        // Adapted from: https://docs.jboss.org/hibernate/search/5.9/reference/en-US/pdf/hibernate_search_reference.pdf
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        Transaction transaction = fullTextSession.beginTransaction();

        // create native Lucene query using the query DSL (recommended)
        QueryBuilder queryBuilder = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(User.class).get();

        // Do an "or" search that will be fuzzy if incoming words end in ~2
        org.apache.lucene.search.Query query = queryBuilder
                .simpleQueryString()
                .onFields("storyVersionsForUserProfile.storyContent")
                .matching(searchString)
                .createQuery();

         // wrap Lucene query in a org.hibernate.Query
        org.hibernate.Query fullTextQuery = fullTextSession.createFullTextQuery(query, User.class);

        // execute search
        List result = fullTextQuery.list();
        transaction.commit();
        session.close();

        // send info to results page
        if (result.size() !=0) {
            request.setAttribute("textResult", result);
        } else {
            request.setAttribute("textResult", null);
        }

        request.setAttribute("topic", searchString);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/textResult.jsp");
        dispatcher.forward(request, response);
    }
}
