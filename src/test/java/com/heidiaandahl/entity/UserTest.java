package com.heidiaandahl.entity;

import com.heidiaandahl.entity.Story;
import com.heidiaandahl.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ejb.Local;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for <code style="color: gray; font-size: 0.8em;">User</code>
 *
 * @author Heidi Aandahl
 */

public class UserTest {

    User testProfileUser;
    User testEditor;
    Story testStory;
    Story setupStory;
    LocalDate setupStoryDate;
    Set<Story> setupStorySet;

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Sets up conditions for testing.
     * Instantiates a story, a user whose profile has the story, a user who edits the story,
     * and the appropriate lists to hold the story. The story is added to the appropriate lists.
     * The setup is unrealistic in that if there is only one story associated with a user's
     * profile, that user would also act as the editor for that story version.  However,
     * this is a simple way to test that the methods work correctly if the profile user
     * and editor are different.
     */
    @BeforeEach
    void setUp() {
        testProfileUser = new User();
        testEditor = new User();
        setupStoryDate = LocalDate.parse("2018-12-27");
        setupStory = new Story("We saved up for a wonderful holiday season this year.",
                setupStoryDate, true, testProfileUser, testEditor, true);
        setupStorySet = new HashSet<>();
        setupStorySet.add(setupStory);

        testProfileUser.setStoryVersionsForUserProfile(setupStorySet);
        testEditor.setStoryVersionsWithUserEdit(setupStorySet);
    }

    /**
     * Verifies that a story can be added to a user's set associated with their profile.
     */
    @Test
    public void addStoryForProfileSuccess() {
        LocalDate testDate = LocalDate.parse("2016-05-20");
        testStory = new Story("It was ok.", testDate, true, testProfileUser, testEditor, true);
        testProfileUser.addStoryForProfile(testStory);

        assertTrue(testProfileUser.getStoryVersionsForUserProfile().contains(testStory));
    }

    /**
     * Verifies that a story can be added to a user's set associated with their edits.
     */
    @Test
    public void addStoryToEditListSuccess() {
        LocalDate testDate = LocalDate.parse("2016-05-20");
        testStory = new Story("It was ok.", testDate, true, testProfileUser, testEditor, true);
        testEditor.addStoryToEditList(testStory);

        assertTrue(testEditor.getStoryVersionsWithUserEdit().contains(testStory));
    }

    /**
     * Verifies that a story can be removed from a user's set associated with their profile.
     */
    @Test
    public void removeStoryForProfileSuccess() {
        testProfileUser.removeStoryForProfile(setupStory);
        assertFalse(testProfileUser.getStoryVersionsForUserProfile().contains(setupStory));
    }

    /**
     * Verifies that a story can be removed from a user's set associated with their edits.
     */
    @Test
    public void removeStoryFromEditListSuccess() {
        testEditor.removeStoryFromEditList(setupStory);
        assertFalse(testEditor.getStoryVersionsWithUserEdit().contains(setupStory));
    }

    /**
     * Verifies that a user's tally of past stories marked "unsuitable" can be retrieved.
     */
    @Test
    public void getArchivedUnsuitableStoriesSuccess() {
        LocalDate testBadEntriesDate = LocalDate.parse("2017-05-04");

        Story testArchivedStory1 = new Story("I have a bridge to sell you.",
                testBadEntriesDate, false, testProfileUser, testProfileUser, true);
        Story testArchivedStory2 = new Story("Why won't the idiot moderators let me sell stuff?",
                testBadEntriesDate, false, testProfileUser, testProfileUser, true);
        Story testArchivedStory3 = new Story("We tried an online business but it didn't work.",
                testBadEntriesDate, false, testProfileUser, testProfileUser, false);

        setupStorySet.add(testArchivedStory1);
        setupStorySet.add(testArchivedStory2);
        setupStorySet.add(testArchivedStory3);

        int testTally = testProfileUser.getArchivedUnsuitableStories();

        assertEquals(2, testTally);
    }

    /**
     * Verifies that a user's role can be removed by role name.
     */
    @Test
    public void removeRoleSuccess() {
        // create read and write roles
        Set<Role> testRoleSet = new HashSet<>();
        Role readRole = new Role("read", testProfileUser);
        Role writeRole = new Role ("write", testProfileUser);
        testRoleSet.add(readRole);
        testRoleSet.add(writeRole);

        // assign the roles to the user
        testProfileUser.setUserRoles(testRoleSet);

        // remove the write role
        testProfileUser.removeRole("write");

        assertEquals(1, testProfileUser.getUserRoles().size());
        assertFalse(testProfileUser.getUserRoles().contains(writeRole));
     }

    /**
     * Verifies that a user's profile story versions can be made invisible.
     */
    @Test
    public void hideProfileStoriesSuccess() {
        testProfileUser.hideProfileStories();

        Story expectedStory = new Story("We saved up for a wonderful holiday season this year.",
                setupStoryDate, false, testProfileUser, testEditor, true);

        Set<Story> expectedStorySet = new HashSet<>();
        expectedStorySet.add(expectedStory);

        assertEquals(1, testProfileUser.getStoryVersionsForUserProfile().size());
        assertEquals(expectedStorySet, testProfileUser.getStoryVersionsForUserProfile());
    }

}
