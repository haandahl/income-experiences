package com.heidiaandahl.logic;

import com.heidiaandahl.entity.Story;
import com.heidiaandahl.persistence.GenericDao;
import com.heidiaandahl.persistence.SessionFactoryProvider;
import com.heidiaandahl.test.util.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.MassIndexer;
import org.hibernate.search.Search;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the TopicSearch class, which contains a method that searches
 * for visible stories by search term, using an "or" search.
 *
 * @author Heidi Aandahl
 */
public class SearchTopicsTest {

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Prepares the test database and lucene indexes before each test.
     */
    @BeforeEach
    void setUp() {
        // Set up database with test data
        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");

        // Make sure existing db info has lucene index
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);

        MassIndexer newMassIndexer = fullTextSession.createIndexer();

        try {
            newMassIndexer.startAndWait();
        } catch (InterruptedException interruptedException) {
            logger.error("The thread was interrupted while waiting.");
        } catch (Exception exception) {
            logger.error("There was an error making an index for the application.");
        } finally {
            session.close();
            fullTextSession.close();
        }

    }

    /**
     * Tests to be sure that the method getSearchResults returns only visible stories that contain the search
     * term.
     */
    @Test
    void getSearchByTopicsResultSuccess() {
        /*
            Test DB info
            -- financial_story (id, content, date, visible, profile_user, editor, unsuitable)
            INSERT into financial_story values (2, 'It was a great year. Please check out my pyramid scheme.', '2018-03-04', false, 8, 8, true);
            INSERT into financial_story values (3, 'It was a great year.', '2018-03-05', true, 8, 1, false);

            INSERT into USER values  (8, 'mary', 'password7');
         */

        GenericDao storyDao = new GenericDao(Story.class);
        Story expectedStory =  (Story) storyDao.getById(3);

        TopicSearch testSearch = new TopicSearch();

        List testStories = testSearch.getSearchResults("great");

        assertEquals(1, testStories.size());
        assertTrue(testStories.contains(expectedStory));
    }
}
