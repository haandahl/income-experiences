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
 * Unit tests for <code style="color: gray; font-size: 0.8em;">UserDao</code>
 *
 * @author Heidi Aandahl
 */
public class UserDaoTest {

    UserDao dao;

    /**
     * Sets up a new <code style="color: gray; font-size: 0.8em;">UserDao</code> and
     * refreshes the test database before each unit test.
     */
    @BeforeEach
    void setUp() {
        dao = new UserDao();

        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
    }

    /**
     * Verifies that a user can be updated with a new role.
     */
    @Test
    void updateSuccess() {
        int newRole = 3;  //cleandb sets original role to 4

        User userToUpdate = dao.getById(8);
        userToUpdate.setRole(newRole);

        dao.saveOrUpdate(userToUpdate);
        User retrievedUser = dao.getById(8);

        assertEquals(newRole, retrievedUser.getRole());
    }

    /**
     * Verifies that a new user can be added.
     */
    @Test
    void addSuccess() {
        User newUser = new User("anne", "password9", 2);
        int id = dao.insert(newUser);
        assertNotEquals(0,id);
        User insertedUser = dao.getById(id);
        assertEquals("anne", insertedUser.getUsername());
        assertEquals("password9", insertedUser.getPassword());
        assertEquals(2, insertedUser.getRole());
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
        User newUser = new User("mack", "password10", 4);

        String storyContent = "I won the lottery.";
        LocalDate editDate = LocalDate.parse("2017-05-20");
        boolean isVisible = true;

        Story newStory = new Story(storyContent, editDate, isVisible, newUser, newUser);

        newUser.addStoryForProfile(newStory);
        newUser.addStoryToEditList(newStory);

        // Add new user with profile story
        int id = dao.insert(newUser);

        // Test results
        assertNotEquals(0,id);
        User insertedUser = dao.getById(id);
        assertEquals(newUser, insertedUser);
        assertEquals(1, insertedUser.getStoryVersionsForUserProfile().size());
        assertEquals(1, insertedUser.getStoryVersionsWithUserEdit().size());
        assertTrue(insertedUser.getStoryVersionsForUserProfile().contains(newStory));
        assertTrue(insertedUser.getStoryVersionsWithUserEdit().contains(newStory));
     }

    /**
     * Verifies that a user can be retrieved by Id.
     */
    @Test
    void getByIdSuccess() {
        User retrievedUser = dao.getById(2);
        assertEquals("jean", retrievedUser.getUsername());
        assertEquals("password1", retrievedUser.getPassword());
        assertEquals(2, retrievedUser.getRole());
    }

    /**
     * Verifies that a user can be retrieved by username.
     */
    @Test
    void getByPropertyNameSuccess() {
        List<User> testList = dao.getByPropertyName("username", "chris");
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

        dao.delete(dao.getById(4));
        assertNull(dao.getById(4));
   }

    /**
     * Verifies that a user can be deleted, and their profile story versions will be deleted, too.
     */
    @Test
    void deleteWithProfileStoriesSuccess() {
        StoryDao storyDao = new StoryDao();

        // Identify user with both a profile story and an edit on somebody else's story
        int idOfUserToDelete = 1;

        // Identify story version that should delete (User's profile story)
        int idOfProfileStory = 4;

        dao.delete(dao.getById(idOfUserToDelete));
        assertNull(dao.getById(idOfUserToDelete));
        assertNull(storyDao.getById(idOfProfileStory));
    }

    /**
     * Verifies that a user can be deleted, but stories they have only edited will not be deleted.
     */
    @Test
    void deleteKeepEditsSuccess() {
        StoryDao storyDao = new StoryDao();

        // Identify user with both a profile story and an edit on somebody else's story
        int idOfUserToDelete = 1;

        // Identify story version that should not delete, because the user only edited it
        int idOfEditedStory = 3;

        dao.delete(dao.getById(idOfUserToDelete));
        assertNull(dao.getById(idOfUserToDelete));
        assertNotNull(storyDao.getById(idOfEditedStory));
    }

}
