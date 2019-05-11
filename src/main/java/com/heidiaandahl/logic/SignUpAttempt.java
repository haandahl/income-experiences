package com.heidiaandahl.logic;

import com.heidiaandahl.entity.*;
import com.heidiaandahl.persistence.GenericDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * A class that processes the user's input when they attempt to sign up for the website. Used for validating the input,
 * providing feedback to the user, and committing the information to the database.
 *
 * @author Heidi Aandahl
 */
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

    /**
     * Instantiates a new Sign up attempt.
     */
    public SignUpAttempt() {
    }

    /**
     * Instantiates a new Sign up attempt.
     *
     * @param username      the username
     * @param password      the password
     * @param password2     the password 2
     * @param income        the income
     * @param householdSize the household size
     * @param needs         the needs
     * @param goals         the goals
     * @param incomeSkew    the income skew
     */
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

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets password 2.
     *
     * @return the password 2
     */
    public String getPassword2() {
        return password2;
    }

    /**
     * Sets password 2.
     *
     * @param password2 the password 2
     */
    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    /**
     * Gets income.
     *
     * @return the income
     */
    public String getIncome() {
        return income;
    }

    /**
     * Sets income.
     *
     * @param income the income
     */
    public void setIncome(String income) {
        this.income = income;
    }

    /**
     * Gets household size.
     *
     * @return the household size
     */
    public String getHouseholdSize() {
        return householdSize;
    }

    /**
     * Sets household size.
     *
     * @param householdSize the household size
     */
    public void setHouseholdSize(String householdSize) {
        this.householdSize = householdSize;
    }

    /**
     * Gets needs.
     *
     * @return the needs
     */
    public String getNeeds() {
        return needs;
    }

    /**
     * Sets needs.
     *
     * @param needs the needs
     */
    public void setNeeds(String needs) {
        this.needs = needs;
    }

    /**
     * Gets goals.
     *
     * @return the goals
     */
    public String getGoals() {
        return goals;
    }

    /**
     * Sets goals.
     *
     * @param goals the goals
     */
    public void setGoals(String goals) {
        this.goals = goals;
    }

    /**
     * Gets income skew.
     *
     * @return the income skew
     */
    public String getIncomeSkew() {
        return incomeSkew;
    }

    /**
     * Sets income skew.
     *
     * @param incomeSkew the income skew
     */
    public void setIncomeSkew(String incomeSkew) {
        this.incomeSkew = incomeSkew;
    }

    /**
     * Calls methods to check that user input is complete and valid.
     *
     * @return validation details to include in a message to the user
     */
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

        if (income.length() > 0 && !hasValidIncome()) {
            validationDetails += " The income must be a positive number without symbols. Example: 70000, not $70,000 or -70000.";
        }

        return validationDetails;
    }

    /**
     * Checks whether passwords match
     * @return boolean whether passwords match
     */
     private boolean hasMatchingPasswords() {
        if (password.equals(password2)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks whether input fields are complete
     * @return boolean whether input fields are complete
     */
    private boolean hasCompleteFields() {
        if (username.length() > 0 && password.length() > 0 && password2.length() > 0 && income.length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks whether items were selected from all input menus
     * @return boolean whether items were selected from all input menus
     */
    private boolean hasSelectionsFromAllMenus() {
        if (householdSize != null && needs != null && goals != null && incomeSkew != null ) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks whether the username is one that does not already exist in the database
     * @return boolean whether the username is one that does not already exist in the database
     */
    private boolean hasUniqueUsername() {
        GenericDao userDao = new GenericDao(User.class);
        List<User> usersWithUsername = (List<User>) userDao.getByPropertyName("username", username);
        if (usersWithUsername.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks whether the income is a positive number in the correct format
     * @return boolean whether the income is a positive number in the correct format
     */
    private boolean hasValidIncome() {
        try {
            int incomeInt = Integer.parseInt(income);
            if (incomeInt >= 0) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException numberFormatException) {
            return false;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * Adds a the new user to the database.
     *
     * @return how many users were added
     */
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

    /**
     * Adds the user's survey information to the database.
     */
    private void addNewSurvey(User newUser) {
        // get daos
        GenericDao needsDescriptionDao = new GenericDao(NeedsDescription.class);
        GenericDao goalsDescriptionDao = new GenericDao(GoalsDescription.class);
        GenericDao incomeSkewDao = new GenericDao(IncomeSkew.class);
        GenericDao surveyDao = new GenericDao(Survey.class);

        // convert survey info to integers
        int householdSizeInt = Integer.parseInt(householdSize);
        int incomeInt = Integer.parseInt(income);
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

    /**
     * Adds the user's read and write roles to the database.
     */
    private void addNewRoles(User newUser) {
        GenericDao roleDao = new GenericDao(Role.class);

        Role readRole = new Role ("read", newUser);
        int readRoleAdded = roleDao.insert(readRole);
        Role writeRole = new Role ("write", newUser);
        int writeRoleAdded = roleDao.insert(writeRole);

        if (readRoleAdded == 0 || writeRoleAdded == 0) {
            logger.error("A user was added but on or both of the read and write roles failed to add.");
        }

        // TODO - notify user if roles were not added.
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SignUpAttempt that = (SignUpAttempt) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(password2, that.password2) &&
                Objects.equals(income, that.income) &&
                Objects.equals(householdSize, that.householdSize) &&
                Objects.equals(needs, that.needs) &&
                Objects.equals(goals, that.goals) &&
                Objects.equals(incomeSkew, that.incomeSkew);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, password2, income, householdSize, needs, goals, incomeSkew);
    }

    @Override
    public String toString() {
        return "SignUpAttempt{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", password2='" + password2 + '\'' +
                ", income='" + income + '\'' +
                ", householdSize='" + householdSize + '\'' +
                ", needs='" + needs + '\'' +
                ", goals='" + goals + '\'' +
                ", incomeSkew='" + incomeSkew + '\'' +
                '}';
    }
}
