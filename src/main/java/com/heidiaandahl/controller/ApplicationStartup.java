package com.heidiaandahl.controller;

import com.heidiaandahl.persistence.SessionFactoryProvider;
import com.heidiaandahl.utility.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.MassIndexer;
import org.hibernate.search.Search;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.util.Properties;

/**
 * Initializer for the Income Experiences application.
 * Rebuilds the index for Hibernate Search, running code specified in
 * https://docs.jboss.org/hibernate/search/5.9/reference/en-US/pdf/hibernate_search_reference.pdf .
 * Makes a properties file available to the application.
 *
 * @author Heidi Aandahl
 */

@WebServlet(
        name = "applicationStartup",
        urlPatterns = { "/startup" },
        loadOnStartup = 1
)
public class ApplicationStartup extends HttpServlet implements PropertiesLoader {

    private final Logger logger = LogManager.getLogger(this.getClass());

    public void init() {
        // Make sure existing db info has lucene index
        logger.debug("in init of ApplicationStartup");
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        logger.debug("just opened a session in ApplicationStartup: " + session);
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

        // Load properties
        Properties incomeExperiencesProperties = new Properties();

        try {
            incomeExperiencesProperties = loadProperties("/incomeexperiences.properties");
            // appears to be loading, debug shows size 31
        } catch (IOException ioException) {
            log("An IOException is occurring while attempting to load the properties file.");
        } catch (Exception exception) {
            log("An Exception is occurring while attempting to load the "
                    + "properties file.");
        }

        ServletContext context = getServletContext();
        context.setAttribute("incomeExperiencesProperties", incomeExperiencesProperties);

        // TODO decide how to get the properties where they are needed
        // code below is from adv java where I guess one employee directory was set for the whole application
        // does something like that make sense here?
                // TODO get properties available in
                // Search JSP - so I don't have to store exact jobs
                // Search Results JSP - for formatted career
                // Search Logic java - to access codes, maybe to get career so I don't have to put properties in Results JSP

        // EmployeeDirectory employeeDirectory = new EmployeeDirectory(incomeExperiencesProperties);

        // context.setAttribute("employeeDirectory", employeeDirectory);

     }
}

