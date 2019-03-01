package com.heidiaandahl.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;


@Entity(name = "Story")
@Table(name = "financial_story")
public class Story {

    // non FK fields
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
    From PW in Slack: As you work on week 5 and your indie project.
    Hibernate assumes that id columns (in the database) are simply named "id",
    and that a join column would then be named <table name>_id (user_id, for example).
    This is how I demonstrate this in the week 5 demos.
    However, if you are using another naming scheme, for example userID, \
    you will need to specify the join column when annotating your relationships.

    See also https://www.baeldung.com/hibernate-one-to-many
     */

    // @Column(s) not allowed on a @ManyToOne property: com.heidiaandahl.entity.Story.editor
    @ManyToOne
    @JoinColumn(name="profile_user")
    private User profileUser;

    @ManyToOne
    @JoinColumn(name="editor")
    private User editor;





}
