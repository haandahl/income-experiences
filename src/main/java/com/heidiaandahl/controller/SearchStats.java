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

        // get search info from user
        String incomeInput = request.getParameter("income");
        String householdSizeInput = request.getParameter("householdSize");
        String careerInput = request.getParameter("careerInput");

        // TODO - decide, is the servlet doing too much?  place code in separate class?

        // set up variables for validation, search, and display
        String careerName = "";
        int income = 0;
        int householdSize = 0;
        String incomeDisplay = ""; // todo lookup in intro java!
        ExperiencesSearch experiencesSearch = null;
        Properties properties = (Properties) getServletContext().getAttribute("incomeExperiencesProperties");

        // validate household size
        if (householdSizeInput.equals("0")) {
            // TODO - do something to show error
        } else {
            householdSize = Integer.valueOf(householdSizeInput);
            experiencesSearch = new ExperiencesSearch(properties, householdSize);
        }

        // validate other inputs and establish numeric income
        if (careerInput != null) {
            income = experiencesSearch.getMedianWageFromBls(careerInput);
            careerName = properties.getProperty(careerInput + ".display.name");
        } else if (incomeInput != null) {
            income = Integer.valueOf(incomeInput);
            // TODO - deal with non-numeric entries
        } else {
            //TODO - do something ot show error
        }


        // TODO - move floor and ceiling stuff to here to display on results page
        // TODO - conditional display of jsp text depending on which search was done
        // TODO - remove dump from jsp
       List<Survey> matchingSurveys = experiencesSearch.getSurveysNearlyMatchingIncome(income);





        // set request attribute
        request.setAttribute("income", income);
        request.setAttribute("householdSize", householdSize);
        request.setAttribute("careerName", careerName);
        request.setAttribute("matchingSurveys", matchingSurveys);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/statsResult.jsp");
        dispatcher.forward(request, response);
    }
}
