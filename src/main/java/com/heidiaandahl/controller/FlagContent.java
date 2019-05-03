package com.heidiaandahl.controller;

import com.heidiaandahl.entity.Story;
import com.heidiaandahl.persistence.GenericDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(
        name = "flagContent",
        urlPatterns = { "/flag-content"}
)
public class FlagContent extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         // get the problematic story
        String flaggedContent = request.getParameter("flaggable-story");

        GenericDao storyDao = new GenericDao(Story.class);
        List<Story> flaggedStories = (List<Story>) storyDao.getByPropertyName("storyContent", flaggedContent);

        // mark the story "unsuitable" in the database (includes stories with duplicate content if they exist)
        for (Story story : flaggedStories) {
            if (!story.isUnsuitable()) {
                story.setUnsuitable(true);
                storyDao.saveOrUpdate(story);
            }
        }


        // todo gather which page the flag was viewed on - can I do that?
        // todo do I actually need to rerun the search?
        // todo possible hack is a thank-you page... the search is lost.
        // for now hard-code
        String nextUrl = "/textResult.jsp";

        // redirect back to page
        RequestDispatcher dispatcher = request.getRequestDispatcher(nextUrl);
        dispatcher.forward(request, response);
    }
}
