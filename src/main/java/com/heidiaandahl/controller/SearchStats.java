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

        int income = Integer.valueOf(incomeInput);
        int householdSize = Integer.valueOf(householdSizeInput);

        // execute search
        ExperiencesSearch experiencesSearch = new ExperiencesSearch(income, householdSize);
        // TODO - build and use ExperiencesSearch.java to get more info

        // TODO something with API.  See project data folder for API key.

        // TODO looks like I'm going to have to hard-code a selection of careers

        // TODO NOT THIS ONE: example series NCU5500001722900  <-- This one does not return results
                            // NCU5306633300003   <-- BLS-provided example
                // NC = prefix for national compensation survey
                // U = does NOT adjust data to eliminate the effect of intrayear variation (unajusted)
                // 55 = State = Wisconsin
                // 0000 = Area = Wisconsin (as opposed to specific metro areas)
                // 17229 = code for computer programmers (I think??)
                        // stuff for computer programmers
                        // 17	229	+006.02	Computer programmers  Y	5
                // 00 = overall occupation average

        // TODO USE this one: https://www.bls.gov/oes/oes_emp.htm  -- iT WOOOOOOOORKS!!
        // https://www.bls.gov/help/hlpforma.htm#OE
                // Their example Series Id: OEUN000000011100011000001
                // Mine: OEUN000000000000015113413
                    // OE = prefix
                    // U = does NOT adjust data to eliminate the effect of intrayear variation (unajusted)
                    // N = area type = national
                    // 0000000 = area code national
                    // 000000 = Cross-industry, Private, Federal, State, and Local Government	0	T	0
                    // 151134 = Web Developers	3	T	90
                    // 13 = Annual median wage


        // set request attribute
        request.setAttribute("income", income);
        request.setAttribute("householdSize", householdSize);


        RequestDispatcher dispatcher = request.getRequestDispatcher("/statsResult.jsp");
        dispatcher.forward(request, response);
    }
}
