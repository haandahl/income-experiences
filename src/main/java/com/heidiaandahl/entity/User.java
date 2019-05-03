package com.heidiaandahl.entity;

import com.heidiaandahl.persistence.VisibleStoryFilterFactory;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FullTextFilterDef;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * User of the web application.
 *
 * @author Heidi Aandahl
 */
@Indexed
@Entity(name = "User")
@Table(name = "user")
//@FullTextFilterDef(name = "visibleStory", impl = VisibleStoryFilterFactory.class)
public class User implements Serializable {
    /*
        Resource for choice to implement Serializable:
        https://stackoverflow.com/questions/4525186/cannot-be-cast-to-java-io-serializable
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

    @Field
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    // TODO - consider cascading and fetching, keep or change?  Also hash set or tree??

    // TODO - try application with Lazy loading - defers load until needed.
    // https://www.baeldung.com/hibernate-lazy-eager-loading
    // Note from above resource "In some cases you need to handle lazily-initialized objects with a special care or you might end up with an exception"

    // TODO - take feedback: PW: . I think the decision here is around whether you want these sorted when they come back.
    //  If you do, then you will need to implement the comparable interface in Story and decided what kind of ordering you want (by date, name, etc.).

    @IndexedEmbedded
    @OneToMany(mappedBy = "profileUser", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Story> storyVersionsForUserProfile = new HashSet<>();

    @OneToMany(mappedBy = "editor", orphanRemoval = false, fetch = FetchType.EAGER)
    private Set<Story> storyVersionsWithUserEdit = new HashSet<>();

    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Survey> userSurveys = new HashSet<>();

    @OneToMany(mappedBy = "username", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Role> userRoles = new HashSet<>();

    /**
     * Instantiates a new User.
     */
    public User() {
    }

    /**
     * Instantiates a new User.
     *
     * @param username the username
     * @param password the password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
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
     * Gets story versions for user profile.
     *
     * @return the story versions for user profile
     */
    public Set<Story> getStoryVersionsForUserProfile() {
        return storyVersionsForUserProfile;
    }

    /**
     * Sets story versions for user profile.
     *
     * @param storyVersionsForUserProfile the story versions for user profile
     */
    public void setStoryVersionsForUserProfile(Set<Story> storyVersionsForUserProfile) {
        this.storyVersionsForUserProfile = storyVersionsForUserProfile;
    }

    /**
     * Gets story versions with user edit.
     *
     * @return the story versions with user edit
     */
    public Set<Story> getStoryVersionsWithUserEdit() {
        return storyVersionsWithUserEdit;
    }

    /**
     * Sets story versions with user edit.
     *
     * @param storyVersionsWithUserEdit the story versions with user edit
     */
    public void setStoryVersionsWithUserEdit(Set<Story> storyVersionsWithUserEdit) {
        this.storyVersionsWithUserEdit = storyVersionsWithUserEdit;
    }

    /**
     * Gets user surveys.
     *
     * @return the user surveys
     */
    public Set<Survey> getUserSurveys() {
        return userSurveys;
    }

    /**
     * Sets user surveys.
     *
     * @param userSurveys the user surveys
     */
    public void setUserSurveys(Set<Survey> userSurveys) {
        this.userSurveys = userSurveys;
    }

    /**
     * Gets user roles.
     *
     * @return the user roles
     */
    public Set<Role> getUserRoles() {
        return userRoles;
    }

    /**
     * Sets user roles.
     *
     * @param userRoles the user roles
     */
    public void setUserRoles(Set<Role> userRoles) {
        this.userRoles = userRoles;
    }

    /**
     * Add story for profile.
     *
     * @param story the story
     */
    public void addStoryForProfile(Story story) {
        storyVersionsForUserProfile.add(story);
        story.setProfileUser(this);
    }

    /**
     * Remove story for profile.
     *
     * @param story the story
     */
    public void removeStoryForProfile(Story story) {
        storyVersionsForUserProfile.remove(story);
        story.setProfileUser(null);
    }

    /**
     * Add story to edit list.
     *
     * @param story the story
     */
    public void addStoryToEditList(Story story) {
        storyVersionsWithUserEdit.add(story);
        story.setProfileUser(this);
    }

    /**
     * Remove story to edit list.
     *
     * @param story the story
     */
    public void removeStoryFromEditList(Story story) {
        storyVersionsWithUserEdit.remove(story);
        story.setProfileUser(null);
    }

    //TODO add and remove methods for survey sets plus test

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password);
    }
}
