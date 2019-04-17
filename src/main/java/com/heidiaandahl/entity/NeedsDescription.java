package com.heidiaandahl.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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


    public NeedsDescription() {
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

    public Set<Survey> getSurveysWithNeedsDescription() {
        return surveysWithNeedsDescription;
    }

    public void setSurveysWithNeedsDescription(Set<Survey> surveysWithNeedsDescription) {
        this.surveysWithNeedsDescription = surveysWithNeedsDescription;
    }

    // TODO - if needed, create and test add/remove methods for surveysWithNeedsDescription.
    //  However, I don't think I need to explicitly add/remove these...

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
