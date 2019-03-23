package com.heidiaandahl.controller;

import com.heidiaandahl.entity.Story;
import com.heidiaandahl.entity.User;
import com.heidiaandahl.persistence.SessionFactoryProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.MassIndexer;
import org.hibernate.search.Search;

import javax.servlet.annotation.WebServlet;

/**
 * Initializer for the Income Experiences application.
 * Rebuilds the index for Hibernate Search, running code specified in
 * https://docs.jboss.org/hibernate/search/5.9/reference/en-US/pdf/hibernate_search_reference.pdf .
 *
 * @author Heidi Aandahl
 */

@WebServlet(
        name = "applicationStartup",
        urlPatterns = { "/startup" },
        loadOnStartup = 1
)
public class ApplicationStartup {

    private final Logger logger = LogManager.getLogger(this.getClass());

    public void init() {
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
}
