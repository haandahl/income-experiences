package com.heidiaandahl.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heidiaandahl.entity.Story;
import com.heidiaandahl.entity.Survey;
import com.heidiaandahl.logic.ExperiencesSearch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@WebServlet(
        name = "searchStats",
        urlPatterns = {"/search-stats"}
)
public class SearchStats extends HttpServlet {

    private final Logger logger = LogManager.getLogger(this.getClass());

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();

        // set up for user's next step
        String nextUrl = "/search.jsp"; // default to error
        String validationMessage = "";
        String careerName = "";

        // get search info from user
        String incomeInput = request.getParameter("income").trim();
        String householdSizeInput = request.getParameter("householdSize");
        String careerInput = request.getParameter("careerInput");

        // instantiate an ExperiencesSearch
        ServletContext context = getServletContext();
        Properties properties = (Properties) context.getAttribute("incomeExperiencesProperties");
        ExperiencesSearch experiencesSearch = new ExperiencesSearch(properties, incomeInput, householdSizeInput, careerInput);

        String validationDetails = experiencesSearch.getValidationDetails();

        if (validationDetails.length() == 0) {
            nextUrl = "/statsResult.jsp";
            // complete the search
            String percentDifferenceSearched = experiencesSearch.setMatchingSurveys();

            // prepare info for user to view
            if (careerInput != "") {
                careerName = properties.getProperty(careerInput + ".display.name");
            }
            // todo change - income is now long not double, maybe it doesn't matter?
            String incomeDisplay = String.format("$%d", Math.round(experiencesSearch.getTargetIncome()));

            long percentDifferenceSearchedLong = Math.round(Double.parseDouble(percentDifferenceSearched) * 100);
            String percentDifferenceToDisplay = String.valueOf(percentDifferenceSearchedLong) + "%";

            // get data as JSON to use in Chart.js
            String allResponsesJson = experiencesSearch.getChartData();

            // get list of stories to display to user
            List<Story> matchingStories = experiencesSearch.getMatchingStories();

            // Make data needed for charts available to the application
            httpSession.setAttribute("chartData", allResponsesJson);

            // todo put hte experiencesSearch to the session and reduce number of things here
            // make search information available to display to user
            httpSession.setAttribute("income", incomeDisplay);
            httpSession.setAttribute("householdSize", householdSizeInput);
            httpSession.setAttribute("careerName", careerName);
            httpSession.setAttribute("matchingSurveys", experiencesSearch.getMatchingSurveys());
            httpSession.setAttribute("percentDifferenceSearched", percentDifferenceToDisplay);
            httpSession.setAttribute("storiesToDisplay", matchingStories);
            httpSession.setAttribute("returnUrl", nextUrl);

        } else {
            validationMessage = "Please change your search as follows: " + validationDetails;
        }

         // if there is a user error, display validation message on search page;
         if (validationMessage != "") {

            // set request attributes -
             // TODO - apply these in JSP so values are retained if there is an error
            request.setAttribute("validationMessage", validationMessage);
            request.setAttribute("incomeInput", incomeInput);
            request.setAttribute("householdSizeInput", householdSizeInput);
            request.setAttribute("careerInput", careerInput);
       }

       // forward to jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher(nextUrl);
        dispatcher.forward(request, response);
    }

 }
