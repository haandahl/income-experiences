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

//TODO - review / delete notes

   /*
        Date from db doesn't convert to LocalDate.  Can the servlet make a now() date and convert it to the kind of date
        that goes into the db instead?  Is Java Date fine for this?  I am depending on date for a general idea
        of how long ago the admin made edits (to see if they are ramping up or tailing off I guess), but I am
        depending on Id for sustaining more precise order.  So date format and precision may not matter much.

        PW uses datetime and timestamp in her db

        Also interesting is that she does not seem to be mapping all her instance variables to columns...

        And she has all kinds of interesting annotations.... oh, they are making reference to utilities?:
        She followed this model: https://thoughts-on-java.org/persist-localdate-localdatetime-jpa/

        @Convert(converter = LocalDateAttributeConverter.class)
        private LocalDate rideDate;

        @CreationTimestamp
        @Convert(converter = TimestampAttributeConverter.class)
        @EqualsAndHashCode.Exclude
        private LocalDateTime createDate;

        @UpdateTimestamp
        @Convert(converter = TimestampAttributeConverter.class)
        @EqualsAndHashCode.Exclude
        private LocalDateTime updateDate;
     */
    /*
        Welcome to use PW method but she though Hibernate 5 could do better...
     */


