package com.heidiaandahl.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heidiaandahl.entity.Story;
import com.heidiaandahl.entity.Survey;
import com.heidiaandahl.entity.User;
import com.heidiaandahl.logic.ExperiencesSearch;
import com.heidiaandahl.persistence.SessionFactoryProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet(
        name = "searchStats",
        urlPatterns = {"/search-stats"}
)
public class SearchStats extends HttpServlet {

    private final Logger logger = LogManager.getLogger(this.getClass());

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO - break up monster method

        // get search info from user
        String incomeInput = request.getParameter("income").trim();
        String householdSizeInput = request.getParameter("householdSize"); // todo see if empty income gives npe?  it doesn't - why not? it does in sign in
        String careerInput = request.getParameter("careerInput");

        // set up variables with placeholder values
        String nextUrl = "";
        String validationMessage = "";
        String careerName = "";
        String storedPercentDifferenceFromTarget = "";
        String percentDifferenceToDisplay = "";
        String incomeDisplay = "";

        boolean usingThisIncome = false;
        double incomeDouble = 0.0;
        long percentDifference = 0;
        RequestDispatcher dispatcher = null;
        List<Survey> matchingSurveys = null;

        ServletContext context = getServletContext();

        // set up search using properties for the application
        Properties properties = (Properties) context.getAttribute("incomeExperiencesProperties");
        ExperiencesSearch experiencesSearch = new ExperiencesSearch(properties);

        // validate combination of fields entered by user
        boolean hasCorrectFields = experiencesSearch.hasCorrectFields(incomeInput, householdSizeInput, careerInput);

        // create validation message if fields are incorrect or ExperiencesSearch could not set income as entered.
        if (!hasCorrectFields) {
            validationMessage = "Oops! Please check your search. You need a career or an income (not both) " +
                    "and must select a household size.";
        } else if (hasCorrectFields && incomeInput != "") {
            // if the user entered an income, set it now if possible
            usingThisIncome = experiencesSearch.usingThisIncome(incomeInput);
            if (!usingThisIncome) {
                validationMessage = "Please re-enter the income you are interested in. It must be a number.";
            }
        }

        // if there is a user error, display validation message on search page;
         if (validationMessage != "") {
            nextUrl = "/search.jsp";

            // set request attributes - todo see if these are used?  can user redo form with entered data?
            request.setAttribute("validationMessage", validationMessage);
            request.setAttribute("incomeInput", incomeInput);
            request.setAttribute("householdSizeInput", householdSizeInput);
            request.setAttribute("careerInput", careerInput);

            // forward to jsp
            dispatcher = request.getRequestDispatcher(nextUrl);
            dispatcher.forward(request, response);
            // todo - check to be sure flow can't pass beyond here?
        } else {
            // if there is no user error, clear chartData of previous results and set up forward to results display
            nextUrl = "/statsResult.jsp";
            context.removeAttribute("chartData");
        }

        // if proceeding with search, set family size
        experiencesSearch.setTargetFamilySize(Integer.parseInt(householdSizeInput));

        // if user is (correctly) searching by career, set income for search and career name for display
        if (careerInput != "") {
            incomeDouble = experiencesSearch.getMedianWageFromBls(careerInput);
            experiencesSearch.setIncome(incomeDouble);
            careerName = properties.getProperty(careerInput + ".display.name");
        }

        // try to get surveys matching family size and closely matching income
        storedPercentDifferenceFromTarget = properties.getProperty("search.income.percent");
        matchingSurveys = experiencesSearch.getSurveysNearlyMatchingIncome(storedPercentDifferenceFromTarget);


        // If no surveys matched, search again with a bigger income range
        if (matchingSurveys.isEmpty()) {
            storedPercentDifferenceFromTarget = properties.getProperty("search.income.percent.alternate");
            matchingSurveys = experiencesSearch.getSurveysNearlyMatchingIncome(storedPercentDifferenceFromTarget);
        }

        // prepare income and variation info to display to user
        incomeDisplay = String.format("$%d", Math.round(experiencesSearch.getIncome()));
        percentDifference = Math.round(Double.parseDouble(storedPercentDifferenceFromTarget) * 100);
        percentDifferenceToDisplay = String.valueOf(percentDifference) + "%";

        // get data as JSON to use in Chart.js
        String allResponsesJson = getChartData(matchingSurveys, experiencesSearch);

        // get list of stories to display to user
        // TODO - get user separately and provide link to profile (break up code in called method)
        List<Story> matchingStories = experiencesSearch.getMatchingStories(matchingSurveys);

        // Make data needed for charts available to the application
        context.setAttribute("chartData", allResponsesJson);

        // make search information available to display to user
        context.setAttribute("income", incomeDisplay);
        context.setAttribute("householdSize", householdSizeInput);
        context.setAttribute("careerName", careerName);
        context.setAttribute("matchingSurveys", matchingSurveys);
        context.setAttribute("percentDifferenceSearched", percentDifferenceToDisplay);
        context.setAttribute("storiesToDisplay", matchingStories);
        context.setAttribute("returnUrl", nextUrl);

        // forward to jsp
        dispatcher = request.getRequestDispatcher(nextUrl);
        dispatcher.forward(request, response);
    }

    // todo test and/or place method in logic file (ExperiencesSearch) if that makes more sense

    private String getChartData(List<Survey> matchingSurveys, ExperiencesSearch experiencesSearch) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        // organize survey statistics from matching surveys
        Map needsResponses = experiencesSearch.getNeedsResponses(matchingSurveys);
        Map goalsResponses = experiencesSearch.getGoalsResponses(matchingSurveys);
        Map incomeSkewResponses = experiencesSearch.getIncomeSkewResponses(matchingSurveys);

        // Put all the chart data in one map
        Map allResponses = new HashMap();
        allResponses.put("needs", needsResponses);
        allResponses.put("goals", goalsResponses);
        allResponses.put("incomeSkew", incomeSkewResponses);

        String allResponsesJson = mapper.writeValueAsString(allResponses);

        logger.debug(allResponsesJson);
        return allResponsesJson;
    }
}
