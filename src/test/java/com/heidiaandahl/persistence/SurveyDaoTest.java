package com.heidiaandahl.persistence;

import com.heidiaandahl.entity.*;
import com.heidiaandahl.test.util.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

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

    @Test
    void getByPropertyNameSuccess() {

    }

    //TODO note/erase after implementation -
    // I am skipping update for now because I think surveys will be permanent once entered.
    //User will have a chance to check survey before committing to database, but I think that will just
    // be using servlets without persistence.

}


    // //////////////////////BELOW IS JUST COPY PASTE////////////////////////////////////////////////////////////

    /**
     * Verifies that a user can be added with a financial story version that they authored or edited.
     * TODO - I don't think this will be used b/c story only added after user is already added
     * TODO  - delete and apply same concept to role later (more applicable), AFTER Exercise 5 is graded
     *
    @Test
    void addWithProfileStorySuccess() {

        // Create new user with a profile story, self-authored
        User newUser = new User("mack", "password10", 4);

        String storyContent = "I won the lottery.";
        LocalDate editDate = LocalDate.parse("2017-05-20");
        boolean isVisible = true;
        boolean isUnsuitable = false;

        Story newStory = new Story(storyContent, editDate, isVisible, newUser, newUser, isUnsuitable);

        newUser.addStoryForProfile(newStory);
        newUser.addStoryToEditList(newStory);

        // Add new user with profile story
        int id = genericDao.insert(newUser);

        // Test results
        assertNotEquals(0,id);
        User insertedUser = (User)genericDao.getById(id);
        assertEquals(newUser, insertedUser);
        assertEquals(1, insertedUser.getStoryVersionsForUserProfile().size());
        assertEquals(1, insertedUser.getStoryVersionsWithUserEdit().size());
        assertTrue(insertedUser.getStoryVersionsForUserProfile().contains(newStory));
        assertTrue(insertedUser.getStoryVersionsWithUserEdit().contains(newStory));
    }
    */

    /**
     * Verifies that a user can be retrieved by Id.
     *
    @Test
    void getByIdSuccess() {
        User retrievedUser = (User)genericDao.getById(2);
        assertEquals("jean", retrievedUser.getUsername());
        assertEquals("password1", retrievedUser.getPassword());
        assertEquals(2, retrievedUser.getRole());
    }
    */

    /**
     * Verifies that a user can be retrieved by username.
     *
    @Test
    void getByPropertyNameSuccess() {
        List<User> testList = genericDao.getByPropertyName("username", "chris");
        assertEquals(1, testList.size());
        assertEquals(3, testList.get(0).getId());
        assertEquals("password2", testList.get(0).getPassword());
        assertEquals(3, testList.get(0).getId());
    }
    */


    /**
     * Verifies that a user can be deleted, and their profile story versions will be deleted, too.
     *
    @Test
    void deleteWithProfileStoriesSuccess() {
        GenericDao storyDao = new GenericDao(Story.class);

        // Identify user with both a profile story and an edit on somebody else's story
        int idOfUserToDelete = 1;

        // Identify story version that should delete (User's profile story)
        int idOfProfileStory = 4;

        genericDao.delete(genericDao.getById(idOfUserToDelete));
        assertNull(genericDao.getById(idOfUserToDelete));
        assertNull(storyDao.getById(idOfProfileStory));
    }
    */

    /**
     * Verifies that a user can be deleted, but stories they have only edited will not be deleted.
     *
    @Test
    void deleteKeepEditsSuccess() {
        GenericDao storyDao = new GenericDao(Story.class);

        // Identify user with both a profile story and an edit on somebody else's story
        int idOfUserToDelete = 1;

        // Identify story version that should not delete, because the user only edited it
        int idOfEditedStory = 3;

        genericDao.delete(genericDao.getById(idOfUserToDelete));
        assertNull(genericDao.getById(idOfUserToDelete));
        assertNotNull(storyDao.getById(idOfEditedStory));
    }
    */
    // TODO - test update?  If so revise copy/paste here:

    /**
     * Verifies that a user can be updated with a new role.
     *
     @Test
     void updateSuccess() {
     int newRole = 3;  //cleandb sets original role to 4

     User userToUpdate = (User)genericDao.getById(8);
     userToUpdate.setRole(newRole);

     genericDao.saveOrUpdate(userToUpdate);
     User retrievedUser = (User)genericDao.getById(8);

     assertEquals(newRole, retrievedUser.getRole());
     } */

