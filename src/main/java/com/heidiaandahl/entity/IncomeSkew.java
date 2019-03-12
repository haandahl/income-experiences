package com.heidiaandahl.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "GoalsDescription")
@Table(name = "goals_description")
public class IncomeSkew {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "goalsDescription")
    private Set<Survey> surveysWithIncomeSkew = new HashSet<>();

    public IncomeSkew() {
    }

    public IncomeSkew(String description) {
        this.description = description;
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

    public Set<Survey> getSurveysWithIncomeSkew() {
        return surveysWithIncomeSkew;
    }

    public void setSurveysWithIncomeSkew(Set<Survey> surveysWithIncomeSkew) {
        this.surveysWithIncomeSkew = surveysWithIncomeSkew;
    }

    // TODO - if needed, create and test add/remove methods for surveysWithIncomeSkew.
    //  However, I don't think I need to explicitly add/remove these...

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IncomeSkew that = (IncomeSkew) o;
        return id == that.id &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }

    @Override
    public String toString() {
        return "IncomeSkew{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
