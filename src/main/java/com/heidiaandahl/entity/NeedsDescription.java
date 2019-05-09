package com.heidiaandahl.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Needs description represents the extent to which a household's income allowed their needs to be met.
 *
 * @author Heidi Aandahl
 */
@Entity(name = "NeedsDescription")
@Table(name = "needs_description")
public class NeedsDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "needsDescription", fetch = FetchType.EAGER)
    private Set<Survey> surveysWithNeedsDescription = new HashSet<>();


    /**
     * Instantiates a new Needs description.
     */
    public NeedsDescription() {
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
     * Gets surveys with needs description.
     *
     * @return the surveys with needs description
     */
    public Set<Survey> getSurveysWithNeedsDescription() {
        return surveysWithNeedsDescription;
    }

    /**
     * Sets surveys with needs description.
     *
     * @param surveysWithNeedsDescription the surveys with needs description
     */
    public void setSurveysWithNeedsDescription(Set<Survey> surveysWithNeedsDescription) {
        this.surveysWithNeedsDescription = surveysWithNeedsDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NeedsDescription that = (NeedsDescription) o;
        return id == that.id &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }

    @Override
    public String toString() {
        return "NeedsDescription{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
