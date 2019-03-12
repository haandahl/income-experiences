package com.heidiaandahl.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * User of the web application.
 *
 * @author Heidi Aandahl
 */
@Entity(name = "User")
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    // TODO - set this up as ManyToOne once Role Entity is established
    @Column(name = "role_id")
    private int role;

    // TODO - consider cascading and fetching, keep or change?  Also hash set or tree??

    // TODO - try application with Lazy loading - defers load until needed.
    // https://www.baeldung.com/hibernate-lazy-eager-loading
    // Note from above resource "In some cases you need to handle lazily-initialized objects with a special care or you might end up with an exception"

    // TODO - take feedback: PW: . I think the decision here is around whether you want these sorted when they come back.
    //  If you do, then you will need to implement the comparable interface in Story and decided what kind of ordering you want (by date, name, etc.).

    @OneToMany(mappedBy = "profileUser", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Story> storyVersionsForUserProfile = new HashSet<>();

    @OneToMany(mappedBy = "editor", orphanRemoval = false, fetch = FetchType.LAZY)
    private Set<Story> storyVersionsWithUserEdit = new HashSet<>();

    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Survey> userSurveys = new HashSet<>();

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
     * @param role     the role
     */
    public User(String username, String password, int role) {
        this.username = username;
        this.password = password;
        this.role = role;
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
     * Gets role (a numeric reference).
     *
     * @return the role
     */
    public int getRole() {
        return role;
    }

    /**
     * Sets role (a numeric reference).
     *
     * @param role the role
     */
    public void setRole(int role) {
        this.role = role;
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

    public Set<Survey> getUserSurveys() {
        return userSurveys;
    }

    public void setUserSurveys(Set<Survey> userSurveys) {
        this.userSurveys = userSurveys;
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
                ", role=" + role +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                role == user.role &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, role);
    }
}
