package com.heidiaandahl.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "GoalsDescription")
@Table(name = "goals_description")
public class GoalsDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "goalsDescription")
    private Set<Survey> surveysWithGoalsDescription = new HashSet<>();

    public GoalsDescription() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Survey> getSurveysWithGoalsDescription() {
        return surveysWithGoalsDescription;
    }

    public void setSurveysWithGoalsDescription(Set<Survey> surveysWithGoalsDescription) {
        this.surveysWithGoalsDescription = surveysWithGoalsDescription;
    }

    // TODO - if needed, create and test add/remove methods for surveysWithGoalsDescription.
    //  However, I don't think I need to explicitly add/remove these...

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
