package com.heidiaandahl.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Makes data from application available for charts made using JavaScript.
 * Inspired by Joe at
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

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws java.io.IOException {

        ServletContext context = getServletContext();
        String chartData = (String) context.getAttribute("chartData");
        response.setContentType("text/html");
        response.getWriter().write(chartData);
    }
}
