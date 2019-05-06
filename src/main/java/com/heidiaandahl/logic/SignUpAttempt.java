package com.heidiaandahl.logic;

import com.heidiaandahl.entity.*;
import com.heidiaandahl.persistence.GenericDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.List;

public class SignUpAttempt {
    private String username;
    private String password;
    private String password2;
    private String income;
    private String householdSize;
    private String needs;
    private String goals;
    private String incomeSkew;

    private final Logger logger = LogManager.getLogger(this.getClass());


    // todo getters, setters, etc (do I need to ? it's not a bean...)
    // todo test methods, docs

    public SignUpAttempt() {
    }

    public SignUpAttempt(String username, String password, String password2, String income, String householdSize,
                         String needs, String goals, String incomeSkew) {
        this.username = username;
        this.password = password;
        this.password2 = password2;
        this.income = income;
        this.householdSize = householdSize;
        this.needs = needs;
        this.goals = goals;
        this.incomeSkew = incomeSkew;
    }

    public String getValidationDetails() {
        String validationDetails = "";

        if (!hasMatchingPasswords()) {
            validationDetails += " Passwords must match.";
        }

        if (!hasCompleteFields()) {
            validationDetails += " All fields must be complete.";
        }

        if (!hasSelectionsFromAllMenus()) {
            validationDetails += " An option must be selected from each menu.";
        }

        if (!hasUniqueUsername()) {
            validationDetails += " The username you selected is taken; please try another one.";
        }

        return validationDetails;
    }

    private boolean hasMatchingPasswords() {
        if (password.equals(password2)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean hasCompleteFields() {
        if (username.length() > 0 && password.length() > 0 && password2.length() > 0 && income.length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean hasSelectionsFromAllMenus() {
        if (householdSize != null && needs != null && goals != null && incomeSkew != null ) {
            return true;
        } else {
            return false;
        }
    }

    private boolean hasUniqueUsername() {
        GenericDao userDao = new GenericDao(User.class);
        List<User> usersWithUsername = (List<User>) userDao.getByPropertyName("username", username);
        if (usersWithUsername.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public int addNewUser() {
        User newUser = new User(username, password);
        GenericDao userDao = new GenericDao(User.class);
        int userAdded = userDao.insert(newUser);

        if (userAdded > 0) {
            addNewSurvey(newUser);
            addNewRoles(newUser);
        } else {
            logger.error("A user failed to be added after a valid sign-up attempt.");
        }

        return userAdded;
    }

    private void addNewSurvey(User newUser) {
        // get daos
        GenericDao needsDescriptionDao = new GenericDao(NeedsDescription.class);
        GenericDao goalsDescriptionDao = new GenericDao(GoalsDescription.class);
        GenericDao incomeSkewDao = new GenericDao(IncomeSkew.class);
        GenericDao surveyDao = new GenericDao(Survey.class);

        // convert survey info to integers
        int householdSizeInt = Integer.parseInt(householdSize);
        int incomeInt = Integer.parseInt(income); // todo make sure user can enter decimal but get rid of .00 for this
        int needsId = Integer.parseInt(needs);
        int goalsId = Integer.parseInt(goals);
        int incomeSkewId = Integer.parseInt(incomeSkew);

        // Add survey
        NeedsDescription needsDescription = (NeedsDescription) needsDescriptionDao.getById(needsId);
        GoalsDescription goalsDescription = (GoalsDescription) goalsDescriptionDao.getById(goalsId);
        IncomeSkew incomeSkewDescription = (IncomeSkew) incomeSkewDao.getById(incomeSkewId);

        Survey survey = new Survey(LocalDate.now(), householdSizeInt, incomeInt, newUser, needsDescription,
                goalsDescription, incomeSkewDescription);

        int surveyAdded = surveyDao.insert(survey);

        if (surveyAdded == 0) {
            logger.error("A user was added but the corresponding survey was not added to the database.");
        }
    }

    private void addNewRoles(User newUser) {
        GenericDao roleDao = new GenericDao(Role.class);

        Role readRole = new Role ("read", newUser);
        int readRoleAdded = roleDao.insert(readRole);
        Role writeRole = new Role ("write", newUser);
        int writeRoleAdded = roleDao.insert(writeRole);

        if (readRoleAdded == 0 || writeRoleAdded == 0) {
            logger.error("A user was added but on or both of the read and write roles failed to add.");
        }
    }
}
