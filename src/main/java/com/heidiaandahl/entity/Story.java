package com.heidiaandahl.entity;

import com.heidiaandahl.persistence.VisibleStoryFilterFactory;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FullTextFilterDef;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;


/**
 * The financial story of a site user. Each story is a "version" that may represent the original
 * content, a version edited by the original author, or a version edited by an administrator.
 *
 * @author Heidi Aandahl
 */
@Indexed
@Entity(name = "Story")
@Table(name = "financial_story")
@FullTextFilterDef(name = "visibleStory", impl = VisibleStoryFilterFactory.class)
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

    @Field
    @Column(name = "content")
    private String storyContent;

    @Column(name = "date")
    private LocalDate editDate;

    @Field
    @Column(name = "visible")
    private boolean isVisible;

    // todo rename this and all "is" booleans to the characteristic without the is, so usable in jsps

    @Column(name = "unsuitable")
    private boolean unsuitable;

    /*
        Resource for ManyToOne JoinColumn: https://www.baeldung.com/hibernate-one-to-many
     */

    @ManyToOne
    @JoinColumn(name="profile_user")
    private User profileUser;

    @ManyToOne
    @JoinColumn(name="editor")
    private User editor;


    /**
     * Instantiates a new Story.
     */
    public Story() {
    }

    /**
     * Instantiates a new Story.
     *
     * @param storyContent the story content
     * @param editDate     the edit date
     * @param isVisible    whether the version may be displayed to site users
     * @param profileUser  the profile user
     * @param editor       the editor
     * @param unsuitable the is unsuitable
     */
    public Story(String storyContent, LocalDate editDate, boolean isVisible, User profileUser, User editor, boolean unsuitable) {
        this.storyContent = storyContent;
        this.editDate = editDate;
        this.isVisible = isVisible;
        this.profileUser = profileUser;
        this.editor = editor;
        this.unsuitable = unsuitable;
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
     * Gets story content.
     *
     * @return the story content
     */
    public String getStoryContent() {
        return storyContent;
    }

    /**
     * Sets story content.
     *
     * @param storyContent the story content
     */
    public void setStoryContent(String storyContent) {
        this.storyContent = storyContent;
    }

    /**
     * Gets edit date.
     *
     * @return the edit date
     */
    public LocalDate getEditDate() {
        return editDate;
    }

    /**
     * Sets edit date.
     *
     * @param editDate the edit date
     */
    public void setEditDate(LocalDate editDate) {
        this.editDate = editDate;
    }

    /**
     * Gets the status of whether the version may be displayed to site visitors.
     *
     * @return whether the version may be displayed to site visitors
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * Sets the status of whether the version may be displayed to site visitors.
     *
     * @param visible whether the version may be displayed to site visitors
     */
    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    /**
     * Gets profile user.
     *
     * @return the profile user
     */
    public User getProfileUser() {
        return profileUser;
    }

    /**
     * Sets profile user.
     *
     * @param profileUser the profile user
     */
    public void setProfileUser(User profileUser) {
        this.profileUser = profileUser;
    }

    /**
     * Gets editor.
     *
     * @return the editor
     */
    public User getEditor() {
        return editor;
    }

    /**
     * Sets editor.
     *
     * @param editor the editor
     */
    public void setEditor(User editor) {
        this.editor = editor;
    }

    /**
     * Gets the status indicating whether an administrator has flagged the content unsuitable.
     *
     * @return the status indicating whether an administrator has flagged the content unsuitable
     */
    public boolean isUnsuitable() {
        return unsuitable;
    }

    /**
     * Sets the status indicating whether an administrator has flagged the content unsuitable.
     *
     * @param unsuitable the status indicating whether an administrator has flagged the content unsuitable
     */
    public void setUnsuitable(boolean unsuitable) {
        this.unsuitable = unsuitable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Story story = (Story) o;
        return id == story.id &&
                isVisible == story.isVisible &&
                unsuitable == story.unsuitable &&
                Objects.equals(storyContent, story.storyContent) &&
                Objects.equals(editDate, story.editDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, storyContent, editDate, isVisible, unsuitable);
    }

    @Override
    public String toString() {
        return "Story{" +
                "id=" + id +
                ", storyContent='" + storyContent + '\'' +
                ", editDate=" + editDate +
                ", isVisible=" + isVisible +
                ", unsuitable=" + unsuitable +
                '}';
    }


}
