package com.heidiaandahl.persistence;

import com.heidiaandahl.entity.*;
import com.heidiaandahl.test.util.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for <code style="color: gray; font-size: 0.8em;">StoryDao</code>
 *
 * @author Heidi Aandahl
 */
public class StoryDaoTest {

    StoryDao dao;

    /**
     * Sets up a new <code style="color: gray; font-size: 0.8em;">StoryDao</code> and
     * refreshes the test database before each unit test.
     */
    @BeforeEach
    void setUp() {
        dao = new StoryDao();

        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
    }

    /**
     * Verifies that a story can be updated with a new visibility setting.
     * TODO ERASE PASS
     */
    @Test
    void updateSuccess() {
        boolean isVisible = false;

        Story storyToUpdate = dao.getById(1);
        storyToUpdate.setVisible(isVisible);

        dao.saveOrUpdate(storyToUpdate);
        Story retrievedStory = dao.getById(1);

        assertEquals(isVisible, retrievedStory.isVisible());
    }

    /**
     * Verifies that a new story can be added.
     * TODO ERASE PASS
     */
    @Test
    void addSuccess() {

        // Set up parameters for new Story
        String testStoryContent = "We had triplets this year; it was crazy.";
        LocalDate testEditDate = LocalDate.parse("2018-05-30");
        boolean testVisibility = true;

        UserDao userDao = new UserDao();
        User testProfileUser = userDao.getById(7);
        User testEditor = userDao.getById(7);

        // Instantiate new story
        Story newStory = new Story(testStoryContent, testEditDate, testVisibility, testProfileUser, testEditor);

        // Insert new story to database
        int id = dao.insert(newStory);

        // Test results
        assertNotEquals(0,id);
        Story insertedStory = dao.getById(id);

        assertEquals(4, insertedStory.getId());
        assertEquals(testStoryContent, insertedStory.getStoryContent());
        assertEquals(testEditDate, insertedStory.getEditDate());
        assertEquals(testVisibility, insertedStory.isVisible());

        assertEquals(7, insertedStory.getProfileUser().getId());
        assertEquals(7, insertedStory.getEditor().getId());
    }

     /**
     * Verifies that a story can be retrieved by Id.
      * TODO ERASE PASS
     */
    @Test
    void getByIdSuccess() {

        LocalDate expectedDate = LocalDate.parse("2018-01-01");

        Story retrievedStory = dao.getById(1);
        assertEquals("We made it work in our 4-generation home.", retrievedStory.getStoryContent());
        assertEquals(expectedDate, retrievedStory.getEditDate());
        assertEquals(true, retrievedStory.isVisible());
        assertEquals(4, retrievedStory.getProfileUser().getId());
        assertEquals(4, retrievedStory.getEditor().getId());
    }

    /**
     * Verifies that a story version can be retrieved by editor.  Not needed - this is now a getter in the User.
     */
    @Test
    void getByPropertyNameSuccess() {

        UserDao userDao = new UserDao();
        User testEditor = userDao.getById(1);
        LocalDate testDate = LocalDate.parse("2018-03-05");

        List<Story> testList = (List<Story>) dao.getByPropertyName("editor", testEditor);
        //Error:(111, 78) java: incompatible types: com.heidiaandahl.entity.User cannot be converted to java.lang.String
        // TODO resolve above
        // getByPPropertyName takes a string as a second parameter... which isn't what I want in this case
        // I notice that PW's generic dao doesn't even include the getByProperty function

        assertEquals(1, testList.size());
        assertEquals(3, testList.get(0).getId());
        assertEquals("It was a great year.", testList.get(0).getStoryContent());
        assertEquals(testDate, testList.get(0).getEditDate());
        assertEquals(true, testList.get(0).isVisible());
        assertEquals(8, testList.get(0).getProfileUser().getId());
        assertEquals(1, testList.get(0).getEditor().getId());
    }
    */

    /**
     * Verfies that a story can be deleted.
     */
    @Test
    void deleteSuccess() {
        dao.delete(dao.getById(2));
        assertNull(dao.getById(2));
    }

}
