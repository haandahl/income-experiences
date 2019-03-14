package com.heidiaandahl.persistence;

import com.heidiaandahl.entity.*;
import com.heidiaandahl.test.util.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for <code style="color: gray; font-size: 0.8em;">GenericDao</code> used with the
 * <code style="color: gray; font-size: 0.8em;">User</code> class.
 *
 * @author Heidi Aandahl
 */
public class UserDaoTest {

    GenericDao genericDao;

    /**
     * Sets up a new <code style="color: gray; font-size: 0.8em;">GenericDao</code> and
     * refreshes the test database before each unit test.
     */
    @BeforeEach
    void setUp() {
        genericDao = new GenericDao(User.class);

        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
    }

    /**
     * TODO - revise after role entity is created and tested; may not belong in this test set.
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
    }
    */

    /**
     * Verifies that a new user can be added.
     */
    @Test
    void addSuccess() {
        User newUser = new User("anne", "password9");
        int id = genericDao.insert(newUser);
        assertNotEquals(0,id);
        User insertedUser = (User)genericDao.getById(id);
        assertEquals("anne", insertedUser.getUsername());
        assertEquals("password9", insertedUser.getPassword());
        assertEquals(9, insertedUser.getId());
     }

    /**
     * Verifies that a user can be added with a financial story version that they authored or edited.
     * TODO - I don't think this will be used b/c story only added after user is already added
     * TODO  - delete and apply same concept to role later (more applicable), AFTER Exercise 5 is graded
     */
    @Test
    void addWithProfileStorySuccess() {

        // Create new user with a profile story, self-authored
        User newUser = new User("mack", "password10");

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
        // TODO solve
        // org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role:
        // com.heidiaandahl.entity.User.storyVersionsForUserProfile, could not initialize proxy - no Session

        assertEquals(1, insertedUser.getStoryVersionsWithUserEdit().size());
        assertTrue(insertedUser.getStoryVersionsForUserProfile().contains(newStory));
        assertTrue(insertedUser.getStoryVersionsWithUserEdit().contains(newStory));
     }

    /**
     * Verifies that a user can be retrieved by Id.
     */
    @Test
    void getByIdSuccess() {
        User retrievedUser = (User)genericDao.getById(2);
        assertEquals("jean", retrievedUser.getUsername());
        assertEquals("password1", retrievedUser.getPassword());
    }

    /**
     * Verifies that a user can be retrieved by username.
     */
    @Test
    void getByPropertyNameSuccess() {
        List<User> testList = genericDao.getByPropertyName("username", "chris");
        assertEquals(1, testList.size());
        assertEquals(3, testList.get(0).getId());
        assertEquals("password2", testList.get(0).getPassword());
        assertEquals(3, testList.get(0).getId());
    }

    /**
     * Verifies that a user can be deleted.
     */
    @Test
    void deleteSuccess() {

        genericDao.delete(genericDao.getById(4));
        assertNull(genericDao.getById(4));
   }

    /**
     * Verifies that a user can be deleted, and their profile story versions will be deleted, too.
     */
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

    /**
     * Verifies that a user can be deleted, but stories they have only edited will not be deleted.
     */
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

}
