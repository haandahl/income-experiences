package com.heidiaandahl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

@WebServlet(
        name = "searchStats",
        urlPatterns = { "/search-stats"}
)
public class SearchStats extends HttpServlet {

    private final Logger logger = LogManager.getLogger(this.getClass());

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO - decide, is the servlet doing too much?  place code in separate class?

        // get search info from user
        String incomeInput = request.getParameter("income").trim();
        String householdSizeInput = request.getParameter("householdSize");
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

        // set up search using properties for the application
        Properties properties = (Properties) getServletContext().getAttribute("incomeExperiencesProperties");
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

        // if there is a user error, display validation message on search page; otherwise, set nextUrl to results page
        if (validationMessage != "") {
            nextUrl = "/search.jsp";

            // set request attributes
            request.setAttribute("validationMessage", validationMessage);
            request.setAttribute("incomeInput", incomeInput);
            request.setAttribute("householdSizeInput", householdSizeInput);
            request.setAttribute("careerInput", careerInput);

            // forward to jsp
            dispatcher = request.getRequestDispatcher(nextUrl);
            dispatcher.forward(request, response);
            // todo - check to be sure flow can't pass beyond here?
        } else {
            nextUrl = "/statsResult.jsp";
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

        // TODO - remove dump from jsp

        // TODO - declare elswhere for refactor
        // issue - ultimately i do want these sorted by needs id...
        ObjectMapper mapper = new ObjectMapper();
        Map needsResponses = experiencesSearch.getNeedsResponses(matchingSurveys);

        String needsResponsesJson = mapper.writeValueAsString(needsResponses);



        // get a map of needs descriptions to number of responses

        // get a map of goals descriptions to number of responses

        // get a map of income skew descriptions to number of responses


        // make the above data available to Chart.js

        // set request attributes for happy path
        request.setAttribute("needsResponses", needsResponsesJson);
        request.setAttribute("income", incomeDisplay);
        request.setAttribute("householdSize", householdSizeInput);
        request.setAttribute("careerName", careerName);
        request.setAttribute("matchingSurveys", matchingSurveys); // todo delete if not used
        request.setAttribute("percentDifferenceSearched", percentDifferenceToDisplay);

        // forward to jsp
        dispatcher = request.getRequestDispatcher(nextUrl);
        dispatcher.forward(request, response);
    }
}
