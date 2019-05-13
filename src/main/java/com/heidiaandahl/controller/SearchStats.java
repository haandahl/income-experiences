package com.heidiaandahl.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.heidiaandahl.entity.Story;
import com.heidiaandahl.logic.ChartData;
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

/**
 * A servlet that coordinates the search for survey and story data based on criteria chosen by the user. The
 * criteria can be career and household size or income and household size.
 *
 * @author Heidi Aandahl
 */
@WebServlet(
        name = "searchStats",
        urlPatterns = {"/search-stats"}
)
public class SearchStats extends HttpServlet {

    /**
     * Coordinates the search for survey and story data based on criteria chosen by the user. The
     * criteria can be career and household size or income and household size.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException servlet exception
     * @throws IOException io exception
     */
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

        // validate user input
        String validationDetails = experiencesSearch.getValidationDetails();

        // determine next steps based on validation results
        if (validationDetails.length() == 0) {
            nextUrl = "/statsResult.jsp";
            // complete the search
            String percentDifferenceSearched = experiencesSearch.setMatchingSurveys();

            // make data available so charts can be created
            addChartDataToSession(httpSession, experiencesSearch);

            // prepare info for user to view
            if (careerInput != "") {
                careerName = properties.getProperty(careerInput + ".display.name");
            }

            String incomeDisplay = String.format("$%d", Math.round(experiencesSearch.getTargetIncome()));

            long percentDifferenceSearchedLong = Math.round(Double.parseDouble(percentDifferenceSearched) * 100);
            String percentDifferenceToDisplay = String.valueOf(percentDifferenceSearchedLong) + "%";

            // get list of stories to display to user
            List<Story> matchingStories = experiencesSearch.getMatchingStories();

            // make search information available to display to user
            httpSession.setAttribute("income", incomeDisplay);
            httpSession.setAttribute("householdSize", householdSizeInput);
            httpSession.setAttribute("careerName", careerName);
            httpSession.setAttribute("matchingSurveys", experiencesSearch.getMatchingSurveys());
            httpSession.setAttribute("percentDifferenceSearched", percentDifferenceToDisplay);
            httpSession.setAttribute("storiesToDisplay", matchingStories);
            httpSession.setAttribute("returnUrl", nextUrl);

        } else {
            // add user input and validation info to the request to help user complete the form correctly
            validationMessage = "Please change your search as follows: " + validationDetails;
            request.setAttribute("validationMessage", validationMessage);
            request.setAttribute("incomeInput", incomeInput);
            request.setAttribute("householdSizeInput", householdSizeInput);
            request.setAttribute("careerInput", careerInput);
        }

        // forward to jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher(nextUrl);
        dispatcher.forward(request, response);
    }

    /**
     * Adds data to the session in json format to enable charts to be made.
     *
     * @param httpSession       the http session
     * @param experiencesSearch the experiences search
     * @throws JsonProcessingException the json processing exception
     */
    protected void addChartDataToSession(HttpSession httpSession, ExperiencesSearch experiencesSearch) throws JsonProcessingException {
        // get data as JSON to use in Chart.js
        ChartData chartData = new ChartData();
        String allResponsesJson = chartData.getChartData(experiencesSearch.getMatchingSurveys());
        httpSession.setAttribute("chartData", allResponsesJson);
    }
}
