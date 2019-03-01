package com.heidiaandahl.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;


@Entity(name = "Story")
@Table(name = "financial_story")
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

    @Column(name = "content")
    private String storyContent;

    @Column(name = "date")
    private LocalDate editDate;

    @Column(name = "visible")
    private boolean isVisible;

    /*
        Resource for ManyToOne JoinColumn: https://www.baeldung.com/hibernate-one-to-many
     */

    @ManyToOne
    @JoinColumn(name="profile_user")
    private User profileUser;

    @ManyToOne
    @JoinColumn(name="editor")
    private User editor;


    public Story() {
    }

    public Story(String storyContent, LocalDate editDate, boolean isVisible, User profileUser, User editor) {
        this.storyContent = storyContent;
        this.editDate = editDate;
        this.isVisible = isVisible;
        this.profileUser = profileUser;
        this.editor = editor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStoryContent() {
        return storyContent;
    }

    public void setStoryContent(String storyContent) {
        this.storyContent = storyContent;
    }

    public LocalDate getEditDate() {
        return editDate;
    }

    public void setEditDate(LocalDate editDate) {
        this.editDate = editDate;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public User getProfileUser() {
        return profileUser;
    }

    public void setProfileUser(User profileUser) {
        this.profileUser = profileUser;
    }

    public User getEditor() {
        return editor;
    }

    public void setEditor(User editor) {
        this.editor = editor;
    }

    @Override
    public String toString() {
        return "Story{" +
                "id=" + id +
                ", storyContent='" + storyContent + '\'' +
                ", editDate=" + editDate +
                ", isVisible=" + isVisible +
                ", profileUser=" + profileUser +
                ", editor=" + editor +
                '}';
    }
}
