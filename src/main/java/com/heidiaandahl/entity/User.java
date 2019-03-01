package com.heidiaandahl.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
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

    @OneToMany(mappedBy = "profileUser", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Story> storyVersionsForUserProfile = new HashSet<>();

    @OneToMany(mappedBy = "editor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Story> storyVersionsWithUserEdit = new HashSet<>();

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

    public Set<Story> getStoryVersionsForUserProfile() {
        return storyVersionsForUserProfile;
    }

    public void setStoryVersionsForUserProfile(Set<Story> storyVersionsForUserProfile) {
        this.storyVersionsForUserProfile = storyVersionsForUserProfile;
    }

    public Set<Story> getStoryVersionsWithUserEdit() {
        return storyVersionsWithUserEdit;
    }

    public void setStoryVersionsWithUserEdit(Set<Story> storyVersionsWithUserEdit) {
        this.storyVersionsWithUserEdit = storyVersionsWithUserEdit;
    }

    public void addStoryForProfile(Story story) {
        storyVersionsForUserProfile.add(story);
        story.setProfileUser(this);
    }

    public void removeStoryForProfile(Story story) {
        storyVersionsForUserProfile.remove(story);
        story.setProfileUser(null);
    }

    public void addStoryToEditList(Story story) {
        storyVersionsWithUserEdit.add(story);
        story.setProfileUser(this);
    }

    public void removeStoryToEditList(Story story) {
        storyVersionsWithUserEdit.remove(story);
        story.setProfileUser(null);
    }

    /*  TODO  decide which to string makes sense... do I want to include the sets??
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
    */

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", storyVersionsForUserProfile=" + storyVersionsForUserProfile +
                ", storyVersionsWithUserEdit=" + storyVersionsWithUserEdit +
                '}';
    }
}
