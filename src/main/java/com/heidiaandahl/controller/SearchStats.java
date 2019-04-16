package com.heidiaandahl.controller;

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

        // set up variables for validation, search, and display
        String careerName = "";
        double incomeToCheck = 0.0;
        long income = 0; // int causes conflict if user entered decimal
        int householdSize = 0;
        String incomeDisplay = ""; // todo lookup in intro java!
        ExperiencesSearch experiencesSearch = null;
        Properties properties = (Properties) getServletContext().getAttribute("incomeExperiencesProperties");
        String storedPercentDifferenceFromTarget = "";
        String nextUrl = "";
        List<Survey> matchingSurveys = null;

        // todo maybe store the double here and pass directly to ExperiencesSearch
        long percentDifference = 0; // int leads to possible lossy conversion later
        String percentDifferenceToDisplay = "";

        // validate enough input then set household size as integer
        if (householdSizeInput.equals("0") || (incomeInput == null && careerInput == null) || (incomeInput != null && careerInput != null)) {
            nextUrl = "/search.jsp";
             // TODO - do something to show error
        } else {
            householdSize = Integer.valueOf(householdSizeInput);
            experiencesSearch = new ExperiencesSearch(properties, householdSize);
            // I don't get ^^ for debugging.
         }

        // establish numeric income
        if (careerInput != null) {
            income = (long)experiencesSearch.getMedianWageFromBls(careerInput);
           // java.lang.NullPointerException
            //com.heidiaandahl.controller.SearchStats.doPost(SearchStats.java:69)
            careerName = properties.getProperty(careerInput + ".display.name");
            nextUrl = "/statsResult.jsp";
        } else if (incomeInput != null) {
             //Resource for getting number out of money string:
            // https://stackoverflow.com/questions/11973383/how-to-parse-number-string-containing-commas-into-an-integer-in-java
            try {
                incomeToCheck = Double.parseDouble((incomeInput.replaceAll(",", "")).replaceAll("$", ""));
                income = Math.round(incomeToCheck);

                // Search for surveys matching household size and within a narrow range of the target income
                storedPercentDifferenceFromTarget = properties.getProperty("search.income.percent");
                matchingSurveys = experiencesSearch.getSurveysNearlyMatchingIncome(income, storedPercentDifferenceFromTarget);

                // If no surveys matched, search again with a bigger income range
                if (matchingSurveys.isEmpty()) {
                    storedPercentDifferenceFromTarget = properties.getProperty("search.income.percent.alternate");
                    matchingSurveys = experiencesSearch.getSurveysNearlyMatchingIncome(income, storedPercentDifferenceFromTarget);
                }

                // Manipulate income percent range searched to display back to user
                percentDifference = Math.round(Double.parseDouble(storedPercentDifferenceFromTarget) * 100);
                percentDifferenceToDisplay = String.valueOf(percentDifference) + "%";

                nextUrl = "/statsResult.jsp";
            } catch (NumberFormatException numberFormatException) {
                nextUrl = "/search.jsp";
                // TODO - do something to show error
            } catch (Exception exception) {
                nextUrl = "/search.jsp";
                // TODO - do something to show error
            }
         }

        // TODO - conditional display of jsp text depending on which search was done
        // TODO - remove dump from jsp

        // set request attributes
        request.setAttribute("income", income);
        request.setAttribute("householdSize", householdSize);
        request.setAttribute("careerName", careerName);
        request.setAttribute("matchingSurveys", matchingSurveys);
        request.setAttribute("percentDifferenceSearched", percentDifferenceToDisplay);

        // forward to jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher(nextUrl);
        dispatcher.forward(request, response);
    }
}
