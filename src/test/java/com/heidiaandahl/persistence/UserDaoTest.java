package com.heidiaandahl.persistence;

import com.heidiaandahl.entity.*;
import com.heidiaandahl.test.util.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;


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
