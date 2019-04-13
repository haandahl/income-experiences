package com.heidiaandahl.persistence;

import com.heidiaandahl.entity.*;
import com.heidiaandahl.test.util.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for <code style="color: gray; font-size: 0.8em;">GenericDao</code> used with the
 * <code style="color: gray; font-size: 0.8em;">Survey</code> class.
 *
 * @author Heidi Aandahl
 */

public class SurveyDaoTest {
    GenericDao surveyDao;
    GenericDao userDao;
    GenericDao goalsDescritpionDao;
    GenericDao needsDescriptionDao;
    GenericDao goalsUnmetDao;
    GenericDao needsUnmetDao;
    GenericDao incomeSkewDao;


    /**
     * Sets up a new <code style="color: gray; font-size: 0.8em;">GenericDao</code> and
     * refreshes the test database before each unit test.
     */
    @BeforeEach
    void setUp() {
        surveyDao = new GenericDao(Survey.class);
        userDao = new GenericDao(User.class);
        goalsDescritpionDao = new GenericDao(GoalsDescription.class);
        needsDescriptionDao = new GenericDao(NeedsDescription.class);
        goalsUnmetDao = new GenericDao(GoalsUnmet.class);
        needsUnmetDao = new GenericDao(NeedsUnmet.class);
        incomeSkewDao = new GenericDao(IncomeSkew.class);

        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
    }

    /**
     * Verifies that a new survey can be added.
     */
    @Test
    void addSuccess() {
        // TODO erase PASSED 3/13
        User testUser = (User) userDao.getById(3);

        // TODO test getById on lookup entities directly
        GenericDao needsDescriptionDao = new GenericDao(NeedsDescription.class);
        GenericDao goalsDescriptionDao = new GenericDao(GoalsDescription.class);
        GenericDao incomeSkewDao = new GenericDao(IncomeSkew.class);

        LocalDate testDate = LocalDate.parse("2018-07-07");
        int testFamily = 1;
        int testIncome = 30000;
        NeedsDescription testNeeds = (NeedsDescription) needsDescriptionDao.getById(3);
        GoalsDescription testGoals = (GoalsDescription) goalsDescriptionDao.getById(2);
        IncomeSkew testSkew = (IncomeSkew) incomeSkewDao.getById(1);

        Survey newSurvey = new Survey(testDate, testFamily, testIncome, testUser, testNeeds, testGoals, testSkew);

        int id = surveyDao.insert(newSurvey);

        assertNotEquals(0, id);
        assertEquals(5, id);
        Survey insertedSurvey = (Survey) surveyDao.getById(id);

        assertEquals(newSurvey, insertedSurvey);
    }

     /**
     * Verifies that a Survey can be deleted without checking associated data.
     * TODO - delete?  or is this worth keeping as a separate test?
     */
    @Test
    void simpleDeleteSuccess() {
        // TODO erase PASSED 3/14

        surveyDao.delete(surveyDao.getById(4));
        assertNull(surveyDao.getById(4));
    }

    /**
     * Verifies that a Survey can be deleted with only appropriate changes to other records.
     * The lookup table records should not be deleted, nor should the user.
     * The records in the details tables should be deleted (needs_unmet and goals_unmet)
     */
    @Test
    void deleteSuccess() {
        // TODO erase PASSED 3/14

        /* Test DB Info
            survey (id, date, family size, income, user_id, needs_description_id, goals_description_id, income_skew_id)
            INSERT into survey values
                    (4, '2017-12-12', 2,    90000,     2,        5,                     3,                   1);
            INSERT into needs_unmet (4, false, false, false, false, false, false, false, false, 4);
            INSERT into goals_unmet (4, false, true, false, false, false, false, true, false, 4);
         */

        surveyDao.delete(surveyDao.getById(4));

        // Test for items that should be deleted
        assertNull(surveyDao.getById(4));
        assertNull(goalsUnmetDao.getById(4));
        assertNull(needsUnmetDao.getById(4));

        // Test for associated items that should not be deleted
        assertNotNull(userDao.getById(2));
        assertNotNull(needsDescriptionDao.getById(5));
        assertNotNull(goalsDescritpionDao.getById(3));
        assertNotNull(incomeSkewDao.getById(1));
    }

    /**
     *  Verifies that a survey can be retrieved by Id.
     */
    @Test
    void getByIdSuccess() {
         Survey retrievedSurvey = (Survey)surveyDao.getById(3);
         User expectedUser = (User)userDao.getById(1);
         NeedsDescription expectedNeedsDescription = (NeedsDescription) needsDescriptionDao.getById(3);
         GoalsDescription expectedGoalsDescription = (GoalsDescription) goalsDescritpionDao.getById(1);
         IncomeSkew expectedIncomeSkew = (IncomeSkew) incomeSkewDao.getById(2);

         /*
            Test DB info:
            survey (id, date,   family size, income, user_id, needs_description_id, goals_description_id, income_skew_id)
            INSERT into survey values
                    (3, '2019-01-15', 3,    50000,      1,          3,                  1,                      2);
         */

         LocalDate expectedLocalDate = LocalDate.parse("2019-01-15");

         // Test for correct data
         assertEquals(3, retrievedSurvey.getId());
         assertEquals(expectedLocalDate, retrievedSurvey.getSurveyDate());
         assertEquals(3, retrievedSurvey.getFamilySize());
         assertEquals(50000, retrievedSurvey.getIncome());

         // Test for correct associations
         assertEquals(expectedUser, retrievedSurvey.getParticipant());
         assertEquals(expectedNeedsDescription, retrievedSurvey.getNeedsDescription());
         assertEquals(expectedGoalsDescription, retrievedSurvey.getGoalsDescription());
         assertEquals(expectedIncomeSkew, retrievedSurvey.getIncomeSkew());

         // NOTE: TODO test retrieval of details in separate test class.
     }

    /**
     *  Verifies that a survey can be retrieved by Id.
     */
    @Test
    void getByIdWithAssociatedDescriptionsSuccess() {
        Survey retrievedSurvey = (Survey) surveyDao.getById(3);

        NeedsDescription expectedNeedsDescription = (NeedsDescription) needsDescriptionDao.getById(3);
        GoalsDescription expectedGoalsDescription = (GoalsDescription) goalsDescritpionDao.getById(1);
        IncomeSkew expectedIncomeSkew = (IncomeSkew) incomeSkewDao.getById(2);

         /*
            Test DB info:
            survey (id, date,   family size, income, user_id, needs_description_id, goals_description_id, income_skew_id)
            INSERT into survey values
                    (3, '2019-01-15', 3,    50000,      1,          3,                  1,                      2);
         INSERT INTO needs_description (id, description) VALUES (3, 'Unmet needs caused discomfort.');
         INSERT INTO goals_description (id, description) VALUES (1, 'Unmet goals caused insecurity or high stress.');
         INSERT INTO income_skew (id, description) VALUES (2, 'some impact');
         */

        // Test for correct associations
        // TODO - ideally these would move to new classes - I am really testing getById
        // However, I think I would always access these via user or survey, not directly

        assertEquals("Unmet needs caused discomfort.", retrievedSurvey.getNeedsDescription().getDescription());
        assertEquals("Unmet goals caused insecurity or high stress.", retrievedSurvey.getGoalsDescription().getDescription());
        assertEquals("some impact", retrievedSurvey.getIncomeSkew().getDescription());

        // NOTE: TODO test retrieval of booleans in separate test class.
    }

        /**
         * Verifies that a survey can be retrieved by user.
         */
    @Test
    void getByUserSuccess() {
        /*
            Test DB info
            -- ------------------------------------------------------------------------------------------------
            -- survey (id, date, family size, income, user_id, needs_description_id, goals_description_id, income_skew_id)
            INSERT into survey values (3, '2019-01-15', 3, 50000, 1, 3, 1, 2);
            -- ------------------------------------------------------------------------------------------------
            -- needs_unmet(id, food, housing, utilities, health_care, clothing, transportation, child_care, other, survey_id)
            INSERT into needs_unmet values (3, true, false, true, true, true, false, false, false, 3);
            -- ------------------------------------------------------------------------------------------------
            -- goals_unmet(id, savings, career_ed, needs_quality, donations, recreation, travel, services, other, survey_id)
            INSERT into goals_unmet values (3, true, true, true, true, true, true, true, true, 3);
         */
        User testUser = (User)userDao.getById(1);

        // TODO - make a method that gets the most recent survey and then test it.
        // This one should work knowing there is currently only one survey per user.

        // Map the search criteria
        Map<String, Object> testMap = new HashMap<>();
        testMap.put("participant", testUser);
        List<Survey> retrievedSurveys = (List<Survey>)surveyDao.getByPropertyNames(testMap);

        Survey retrievedSurvey = retrievedSurveys.get(0);

        assertEquals(retrievedSurvey, (Survey)surveyDao.getById(3));
     }

    /**
     * Verifies that surveys can be retrieved by family size.
     */
    @Test
    void getSurveyByFamilySizeSuccess() {
        List<Survey> testList = surveyDao.getByPropertyName("familySize", 6);

        Survey expectedSurvey = (Survey)surveyDao.getById(1);

        assertEquals(1, testList.size());
        assertEquals(expectedSurvey, testList.get(0));
     }

    /**
     * Verifies that surveys can be retrieved by a range of incomes (inclusive).
     */
    @Test
    void getSurveysByIncomeRange() {
        /*  TEST DB
            -- ------------------------------------------------------------------------------------------------
            -- survey (id, date, family size, income, user_id, needs_description_id, goals_description_id, income_skew_id)
            -- ------------------------------------------------------------------------------------------------
            INSERT into survey values (1, '2018-01-01', 6, 65000, 4, 3, 2, 3);
            INSERT into survey values (2, '2018-03-04', 1, 150000, 8, 5, 5, 2);
            INSERT into survey values (3, '2019-01-15', 3, 50000, 1, 3, 1, 2);
            INSERT into survey values (4, '2017-12-12', 2, 90000, 2, 5, 3, 1);
         */
        List<Survey> testList = surveyDao.getByPropertyRange("income", 50000, 65000);

        Survey expectedSurvey1 = (Survey)surveyDao.getById(3);
        Survey expectedSurvey2 = (Survey)surveyDao.getById(1);

        assertEquals(true, testList.contains(expectedSurvey1));
        assertEquals(true, testList.contains(expectedSurvey2));
        assertEquals(2, testList.size());
    }

    //TODO note/erase after implementation -
    // I am skipping update for now because I think surveys will be permanent once entered.
    //User will have a chance to check survey before committing to database, but I think that will just
    // be using servlets without persistence.
}
