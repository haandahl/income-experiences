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
        urlPatterns = { "/search-experiences"}
)
public class SearchExperiences extends HttpServlet {

    private final Logger logger = LogManager.getLogger(this.getClass());

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // get search string

        // change query to just search stories, not usernames

        // TODO - move this method so it is not executed with every search
        backfillIndex();

        // Adapted from: https://docs.jboss.org/hibernate/search/5.9/reference/en-US/pdf/hibernate_search_reference.pdf
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        Transaction transaction = fullTextSession.beginTransaction();

        // create native Lucene query using the query DSL (recommended)
        QueryBuilder queryBuilder = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(User.class).get();

        // TODO - consider username/story search for admin page but remove username from search field for users exploring topics
        // Fuzzy simpleQueryString search defaults to "or" and allows some misspelling
        org.apache.lucene.search.Query query = queryBuilder
                .simpleQueryString()
                .onFields("username","storyVersionsForUserProfile.storyContent")
                .matching("opvertey~2 Medicaid~2")
                .createQuery();

        /*
        Notes: I'm not sure how loose the ~2 fuzzy search is.  When you do a keyword search instead, you can define that.
        These return results:  povertley, opverty
        Do not: uberpovertly, uberpoverty
        Possible alternative would be combined keyword searches with fuzziness better defined
         */

        // wrap Lucene query in a org.hibernate.Query
        org.hibernate.Query fullTextQuery = fullTextSession.createFullTextQuery(query, User.class);

        // execute search
        List result = fullTextQuery.list();
        transaction.commit();
        session.close();

         if (result.size() !=0) {
            request.setAttribute("textResult", result);
        } else {
            request.setAttribute("textResult", null);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/textResult.jsp");
        dispatcher.forward(request, response);
    }

    private void backfillIndex() {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);

         try {
            fullTextSession.createIndexer().startAndWait();
        } catch (InterruptedException interrupted) {
            logger.error("There was an interruption while attempting to build an index on the existing db.");
        } catch (Exception exeption) {
            logger.error("There was an error while attempting to build an index on the existing db.");
        } finally {
            session.close();
        }
    }
}
