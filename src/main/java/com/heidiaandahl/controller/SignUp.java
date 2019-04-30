package com.heidiaandahl.controller;

import com.heidiaandahl.entity.*;
import com.heidiaandahl.persistence.GenericDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(
    name = "signUp",
    urlPatterns = {"/sign-up"}
)
public class SignUp extends HttpServlet {

    private final Logger logger = LogManager.getLogger(this.getClass());

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // get info from user
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
        String password2 = request.getParameter("password2").trim();
        String income = request.getParameter("surveyIncome").trim();
        String householdSize = request.getParameter("householdSizeInput");
        String needs = request.getParameter("surveyNeeds");
        String goals = request.getParameter("surveyNeeds");
        String incomeSkew = request.getParameter("surveyIncomeSkew");

        // set up validation variables
        boolean hasUserError = true;
        boolean passwordsMatch = false;
        boolean hasAllInputs = false;
        boolean hasAllSelections = false;
        boolean hasUniqueUsername = false;
        String validationMessage = "";

        // set up for user's next step
        String nextUrl = "";
        RequestDispatcher dispatcher = null;


       // Check whether the object is complete and ok
        if (password.equals(password2)) {
            passwordsMatch = true;
        }

        if (username.length() > 0 && password.length() > 0 && password2.length() > 0 && income.length() > 0) {
            hasAllInputs = true;
        }

        if (householdSize != null && needs != null && goals != null && incomeSkew != null ) {
            hasAllSelections = true;
        }

        GenericDao userDao = new GenericDao(User.class);
        List<User> usersWithUsername = (List<User>) userDao.getByPropertyName("username", username);
        if (usersWithUsername.size() == 0) {
            hasUniqueUsername = true;
        }

        // Do something with the object or give feedback to the user

        // set up urls
        if (passwordsMatch && hasAllInputs && hasAllSelections && hasUniqueUsername) {
            hasUserError = false;
            nextUrl = "/welcome.jsp"; // todo improve validation feedback and then make stuff required on front-end

        } else {
            nextUrl = "/signup.jsp";
            validationMessage = "oops not done";
        }

        // add the user and survey todo decide whether additional testing is needed?  i suppose?
        if (!hasUserError) {
            // get daos
            // todo add userdao if refactoring
            GenericDao needsDescriptionDao = new GenericDao(NeedsDescription.class);
            GenericDao goalsDescriptionDao = new GenericDao(GoalsDescription.class);
            GenericDao incomeSkewDao = new GenericDao(IncomeSkew.class);
            GenericDao surveyDao = new GenericDao(Survey.class);
            GenericDao roleDao = new GenericDao(Role.class);

            // add new user
            User newUser = new User(username, password);

            int userAdded = userDao.insert(newUser);

            // get survey data integers
            int householdSizeInt = Integer.parseInt(householdSize);

            int incomeInt = Integer.parseInt(income); // todo make sure user can enter decimal but get rid of .00 for this

            int needsId = Integer.parseInt(needs);
            NeedsDescription needsDescription = (NeedsDescription) needsDescriptionDao.getById(needsId);

            int goalsId = Integer.parseInt(goals);
            GoalsDescription goalsDescription = (GoalsDescription) goalsDescriptionDao.getById(goalsId);

            int incomeSkewId = Integer.parseInt(incomeSkew);
            IncomeSkew incomeSkewDescription = (IncomeSkew) incomeSkewDao.getById(incomeSkewId);

            Survey survey = new Survey(LocalDate.now(), householdSizeInt, incomeInt, newUser, needsDescription, goalsDescription, incomeSkewDescription);

            int surveyAdded = surveyDao.insert(survey);

            Role readRole = new Role ("read", newUser);
            int readRoleAdded = roleDao.insert(readRole);
            Role writeRole = new Role ("write", newUser);
            int writeRoleAdded = roleDao.insert(writeRole);




            // make sure user was added and provide feedback
            if (userAdded > 0 && readRoleAdded > 0 && writeRoleAdded > 0) {
                request.setAttribute("username", username);
            } else {
                validationMessage = "Sorry, there was a problem adding your account. Please try signing up again.";
                nextUrl = "/signup.jsp";
            }

            // log error if user is added without a survey, but allow user to continue
            if (userAdded > 0 && surveyAdded == 0) {
                logger.error("A user was added without a survey");
            }

        }


        request.setAttribute("validationMessage", validationMessage);

        // forward to jsp
        dispatcher = request.getRequestDispatcher(nextUrl);
        dispatcher.forward(request, response);
    }
}



