package com.heidiaandahl.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Goals description represents the extent to which a household's income allowed financial goals to be met.
 *
 * @author Heidi Aandahl
 */
@Entity(name = "GoalsDescription")
@Table(name = "goals_description")
public class GoalsDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "goalsDescription", fetch = FetchType.EAGER)
    private Set<Survey> surveysWithGoalsDescription = new HashSet<>();

    /**
     * Instantiates a new Goals description.
     */
    public GoalsDescription() {
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
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets surveys with goals description.
     *
     * @return the surveys with goals description
     */
    public Set<Survey> getSurveysWithGoalsDescription() {
        return surveysWithGoalsDescription;
    }

    /**
     * Sets surveys with goals description.
     *
     * @param surveysWithGoalsDescription the surveys with goals description
     */
    public void setSurveysWithGoalsDescription(Set<Survey> surveysWithGoalsDescription) {
        this.surveysWithGoalsDescription = surveysWithGoalsDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoalsDescription that = (GoalsDescription) o;
        return id == that.id &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }

    @Override
    public String toString() {
        return "GoalsDescription{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
