package com.heidiaandahl.controller;

import com.heidiaandahl.entity.Story;
import com.heidiaandahl.persistence.GenericDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@WebServlet(
        name = "flagContent",
        urlPatterns = { "/flag-content"}
)
public class FlagContent extends HttpServlet {

    private final Logger logger = LogManager.getLogger(this.getClass());

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         // get the problematic story
        String flaggedContent = request.getParameter("flaggable-story");

        GenericDao storyDao = new GenericDao(Story.class);
        List<Story> flaggedStories = (List<Story>) storyDao.getByPropertyName("storyContent", flaggedContent);

        ServletContext context = getServletContext();
        List<Story> flagOrigin = (List<Story>) context.getAttribute("textResult");

        // mark the story "unsuitable" in the database (includes stories with duplicate content if they exist)
        for (Story story : flaggedStories) {
            if (!story.isUnsuitable()) {
                story.setUnsuitable(true);
                storyDao.saveOrUpdate(story);
            }
        }

        // mark the story "unsuitable in the context list so that it is updated for the user's benefit
        for (Story story : flagOrigin) {
            if (story.getStoryContent().equals(flaggedContent)) {
                story.setUnsuitable(true);
            }
        }

        context.setAttribute("textResult", flagOrigin);

        String url = (String) context.getAttribute("returnUrl");

        // nextUrl = "/textResult.jsp"

        // redirect back to page
        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response)

        // TODO erase fun stuff that didn't work!

        // todo gather which page the flag was viewed on - can I do that?
        // todo do I actually need to rerun the search?
        // todo possible hack is a thank-you page... the search is lost.
        // todo may actually run a separate servlet for the other search?
        // for now hard-code
        //String nextUrl = "/textResult.jsp";

        // String badUrl = request.getAttribute("javax.servlet.forward.request_uri").toString(); // NPE
        // String nextUrl = request.getRequestURI();


        //type Status report
        //
        //message /incomeexperiences/incomeexperiences/flag-content
        //
        //description The requested resource is not available.

        // String nextUrl = request.getRequestURL().toString();
        //type Status report
        //
        //message /incomeexperiences/http%3A/localhost%3A8080/incomeexperiences/flag-content
        //
        //description The requested resource is not available.

        //StringBuffer nextUrlBuffer = request.getRequestURL();
        //http://localhost:8080/incomeexperiences/flag-content
        // how can I just get the relative url out of there?

        /*
            Resource for getting the referring page name:
            https://stackoverflow.com/questions/39718962/how-to-know-from-which-jsp-page-is-calling-the-servlet
            Answer by Afsun Khammadli
         */
        /*
        try {
            String referringPage = new URI(request.getHeader("referer")).getPath();
            String[] uriNames = referringPage.split("/");
            String referringPageName = uriNames[uriNames.length-1];

            nextUrl = "/" + referringPageName; // nextUrl "/search-topics"
        } catch (URISyntaxException uriSynaxException) {
            logger.error(uriSynaxException.getMessage());
        } catch (Exception exception) {
            logger.error(exception.getMessage());
            // todo figure out why this code seems to be working to identify the next URL,
            //  but it's catching an exception that has something to do with line 49 of TopicSearch???
            //  haha wrong url
        }

         */


;
    }
}
