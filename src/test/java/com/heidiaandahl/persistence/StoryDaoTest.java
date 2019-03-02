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

        assertEquals(5, insertedStory.getId());
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
     * Verifies that a story version can be retrieved by search term.
     */
    @Test
    void getByPropertyLikeSuccess() {

        String testSearchTerm = "generation";
        Story expectedStory = dao.getById(1);

        List<Story> testList = dao.getByPropertyLike("storyContent", testSearchTerm);

        assertEquals(1, testList.size());
        assertEquals(expectedStory, testList.get(0));
    }

    /**
     * Verifies that story versions can be retrieved by visibility.
     */
    @Test
    void getByBooleanPropertySuccess() {

        boolean testVisibility = true;
        Story firstExpectedStory = dao.getById(1);
        Story secondExpectedStory = dao.getById(3);
        Story thirdExpectedStory = dao.getById(4);


        List<Story> testList = dao.getByBooleanProperty("isVisible", testVisibility);

        assertEquals(3, testList.size());
        assertEquals(firstExpectedStory, testList.get(0));
        assertEquals(secondExpectedStory, testList.get(1));
        assertEquals(thirdExpectedStory, testList.get(2));
    }

    /**
     * Verfies that a story can be deleted.  TODO verify that users are not deleted.
     */
    @Test
    void deleteSuccess() {
        dao.delete(dao.getById(2));
        assertNull(dao.getById(2));
    }

}
