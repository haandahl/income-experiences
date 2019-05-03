package com.heidiaandahl.logic;

import com.heidiaandahl.entity.Story;
import com.heidiaandahl.entity.User;
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

public class SearchTopicsTest {

    private final Logger logger = LogManager.getLogger(this.getClass());
    @BeforeEach
    void setUp() {
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
            // TODO - consider that the finally block was added to see if it helped later in the app.  No effect. Should it be here anyway?
            session.close();
            fullTextSession.close();
        }

    }

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

        List testUsers = testSearch.getSearchResults("great");

        assertEquals(1, testUsers.size());

    }
}
