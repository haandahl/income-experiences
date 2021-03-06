package com.heidiaandahl.logic;

import com.heidiaandahl.entity.Story;
import com.heidiaandahl.persistence.SessionFactoryProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.search.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import java.util.List;

/**
 * A search through the financial stories to find those containing the search term(s), excluding stories
 * previously displayed that are no longer marked "visible".
 *
 * @author Heidi Aandahl
 */
public class TopicSearch {

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Gets financial stories matching the search term(s) using an "or" search, including only those
     * categorized as "visible" (available for display on the site).
     *
     * @param searchString the search term(s)
     * @return the search results
     */
    public List getSearchResults(String searchString) {
        // Adapted from: https://docs.jboss.org/hibernate/search/5.9/reference/en-US/pdf/hibernate_search_reference.pdf
        Session session = SessionFactoryProvider.getSessionFactory().openSession();

        FullTextSession fullTextSession = Search.getFullTextSession(session);
        Transaction transaction = fullTextSession.beginTransaction();

        // create native Lucene query using the query DSL
        QueryBuilder queryBuilder = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(Story.class).get();

        // Do an "or" search on the story content
        Query query = queryBuilder
                .simpleQueryString()
                .onFields("storyContent")
                .matching(searchString)
                .createQuery();

        // wrap Lucene query in a hibernate FullTextQuery
        FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(query, Story.class);

        // apply filter so only visible stories are returned
        fullTextQuery.enableFullTextFilter("visibleStory");

        // execute search
        List result = fullTextQuery.list();

        transaction.commit();
        session.close();
        return result;
    }

}
