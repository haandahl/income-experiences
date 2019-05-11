package com.heidiaandahl.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Makes data from the session available for charts made using JavaScript.
 * Inspired by Joe (Joseph Kulandai) at
 * https://javapapers.com/ajax/getting-started-with-ajax-using-java/.
 *
 * @author Heidi Aandahl
 */
@WebServlet(
        name = "chartData",
        urlPatterns = { "/chart-data"}
)
public class TransferChartData extends HttpServlet {
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Makes data from the session available for charts made using JavaScript.
     *
     * @param request the request
     * @param response the response
     * @throws IOException io exception
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws java.io.IOException {

        HttpSession httpSession = request.getSession();
        String chartData = (String) httpSession.getAttribute("chartData");
        response.setContentType("text/html");
        response.getWriter().write(chartData);
    }
}
