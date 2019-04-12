package com.heidiaandahl.controller;

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

        int income = 0;
        int householdSize = 0;

        ExperiencesSearch experiencesSearch = null;

        Properties properties = (Properties) getServletContext().getAttribute("incomeExperiencesProperties");

        // convert inputs to integers as needed
        if (householdSizeInput.equals("0")) {
            // TODO - do something to show error
        } else {
            householdSize = Integer.valueOf(householdSizeInput);
        }

        // construct a search based on inputs present
        if (careerInput != null) {
            experiencesSearch = new ExperiencesSearch(properties, careerInput, householdSize);
            // TODO soon- check this path through to display
            income = experiencesSearch.getMedianWageFromBls(careerInput);
        } else if (incomeInput != null) {
            income = Integer.valueOf(incomeInput);
            experiencesSearch = new ExperiencesSearch(properties, income, householdSize);
        } else {
            //TODO - do something ot show error
        }

        // set request attribute
        request.setAttribute("income", income);
        request.setAttribute("householdSize", householdSize);
        // TODO grab nice looking text instead of input value.
        request.setAttribute("careerInput", careerInput);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/statsResult.jsp");
        dispatcher.forward(request, response);
    }
}
