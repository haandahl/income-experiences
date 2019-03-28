package com.heidiaandahl.controller;

import com.heidiaandahl.entity.User;
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

@WebServlet(
        name = "searchStats",
        urlPatterns = { "/search-stats"}
)
public class SearchStats extends HttpServlet {

    private final Logger logger = LogManager.getLogger(this.getClass());

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // TODO - get search info from user, etc
        int income = 0;
        int householdSize = 0;

        // execute search


        // set request attribute
        request.setAttribute("incomeSearch", income);
        request.setAttribute("householdSize", householdSize);


        RequestDispatcher dispatcher = request.getRequestDispatcher("/statsResult.jsp");
        dispatcher.forward(request, response);
    }
}
