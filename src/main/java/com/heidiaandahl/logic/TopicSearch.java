package com.heidiaandahl.logic;

import com.heidiaandahl.entity.User;
import com.heidiaandahl.persistence.SessionFactoryProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import java.util.List;

public class TopicSearch {

    private final Logger logger = LogManager.getLogger(this.getClass());

    public List getSearchResults(String searchString) {
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

        // fullTextQuery.enableFullTextFilter("visibleStory");

        // execute search
        List result = fullTextQuery.list();

        transaction.commit();
        session.close();
        return result;
    }

}
