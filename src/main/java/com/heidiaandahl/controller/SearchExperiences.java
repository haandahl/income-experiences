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

        // TODO investigate why href link to this page is a "get" needing doGet instead of doPost
        // Create an object from request parameters

        // Check whether the object is complete and ok

        // Do something with the object or give feedback to the user

        backfillIndex();

        // TODO move this search somewhere else probably
        // MY FIRST HIBERNATE SEARCH
        // Adapted from: https://docs.jboss.org/hibernate/search/5.9/reference/en-US/pdf/hibernate_search_reference.pdf


        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);

        Transaction transaction = fullTextSession.beginTransaction();
        // create native Lucene query using the query DSL
        // alternatively you can write the Lucene query using the Lucene queryparser
        // or the Lucene programmatic API. The Hibernate Search DSL is recommended though
        QueryBuilder queryBuilder = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(User.class).get();
        /* AS-IS returns no results.
                maybe b/c exact search match or
                maybe b/c indexes not set up for existing data
                or maybe the second TermMatchingContent is set up wrong
        org.apache.lucene.search.Query query = queryBuilder
                .keyword()
                .onFields("username","storyVersionsForUserProfile.storyContent")
                .matching("poverty")
                .createQuery();*/
        /* ALSO returns no results.
                 maybe b/c indexes not set up for existing data
        org.apache.lucene.search.Query query = queryBuilder
                .keyword()
                .onFields("username")
                .matching("fedpoverty1")
                .createQuery(); */

        // TODO - does not work even after refreshing database.  Investigate!
        // [WARN ] 2019-03-23 22:06:28.665 [http-nio-8080-exec-3]
        // LuceneEmbeddedAnalyzerStrategy - HSEARCH000075: Configuration setting hibernate.search.lucene_version
        // was not specified: using LUCENE_CURRENT.
        // [DEBUG] 2019-03-23 22:06:29.165 [http-nio-8080-exec-3] fulltext_query - HSEARCH000274:
        // Executing Lucene query 'username:fedpoverty1'

        org.apache.lucene.search.Query query = queryBuilder
                .keyword()
                .onFields("username")
                .matching("fedpoverty1")
                .createQuery();



        // wrap Lucene query in a org.hibernate.Query
        org.hibernate.Query fullTextQuery = fullTextSession.createFullTextQuery(query, User.class);

        // execute search
        List result = fullTextQuery.list();
        transaction.commit();
        session.close();

        // figure out what's in the result:

        if (result.size() !=0) {
            request.setAttribute("textResult", result);
        } else {
            request.setAttribute("textResult", null);
        }

         // Choose forward or redirect. Reminder: forward hides new location
        RequestDispatcher dispatcher = request.getRequestDispatcher("/textResult.jsp");
        dispatcher.forward(request, response);
        // TODO fix:
        //com.heidiaandahl.controller.SearchExperiences.doGet(SearchExperiences.java:103)
        //org.apache.jasper.JasperException: javax.el.ELException:
        //Cannot convert [User{id=2, username='fedpoverty1', password='SunWet77BRANCHES999'}] of type class java.util.Collections$SingletonList
        // to class java.lang.Boolean

    }

    private void backfillIndex() {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);

        // code to build index on existing db.  probably shouldn't run for every search but...
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
