package com.heidiaandahl.controller;

import com.heidiaandahl.logic.SignUpAttempt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A servlet that signs a new user up.
 *
 * @author Heidi Aandahl
 */
@WebServlet(
    name = "signUp",
    urlPatterns = {"/sign-up"}
)
public class SignUp extends HttpServlet {

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Instantiates a signup attempt entity and uses it to validate the user's input and add them to the database
     * or give them a validation message.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException servlet exception
     * @throws IOException io exception
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // set up for user's next step
        String nextUrl = "/signup.jsp";
        String validationMessage = "";
        int userAdded = 0;

        // get info from user
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
        String password2 = request.getParameter("password2").trim();
        String income = request.getParameter("surveyIncome").trim();
        String householdSize = request.getParameter("householdSizeInput");
        String needs = request.getParameter("surveyNeeds");
        String goals = request.getParameter("surveyNeeds");
        String incomeSkew = request.getParameter("surveyIncomeSkew");

        // instantiate a sign-up attempt
        SignUpAttempt signUpAttempt = new SignUpAttempt(username, password, password2, income, householdSize, needs,
                goals, incomeSkew);

        // validate user's input
        String validationDetails = signUpAttempt.getValidationDetails();

        // add the user to the database if input was valid; otherwise build message for the user
        if (validationDetails.length() == 0) {
            userAdded = signUpAttempt.addNewUser();
        } else {
            validationMessage = "Please change your sign-up as follows: " + validationDetails;
        }

        // if the user input was valid, check whether they were added to the database
        if (userAdded > 0) {
            nextUrl = "/welcome.jsp";
        } else if (validationDetails.length() == 0 && userAdded == 0) {
            validationMessage = "Sorry, there was a problem signing you up. Please try again later.";
        }

        // make necessary info available to the user on the next screen
        request.setAttribute("username", username);
        request.setAttribute("signUpAttempt", signUpAttempt);

        if (nextUrl.equals("/signup.jsp")) {
            request.setAttribute("validationMessage", validationMessage);
        }

        // forward to jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher(nextUrl);
        dispatcher.forward(request, response);
    }
}



