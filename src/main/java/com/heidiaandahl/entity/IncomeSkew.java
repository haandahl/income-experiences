package com.heidiaandahl.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Income skew represents how much factors other than income contributed to a site user's household
 * meeting financial needs and goals.
 *
 * @author Heidi Aandahl
 */
@Entity(name = "IncomeSkew")
@Table(name = "income_skew")
public class IncomeSkew {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "incomeSkew", fetch = FetchType.EAGER)
    private Set<Survey> surveysWithIncomeSkew = new HashSet<>();

    /**
     * Instantiates a new Income skew.
     */
    public IncomeSkew() {
    }

    /**
     * Instantiates a new Income skew.
     *
     * @param description the description
     */
    public IncomeSkew(String description) {
        this.description = description;
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
     * Gets surveys with income skew.
     *
     * @return the surveys with income skew
     */
    public Set<Survey> getSurveysWithIncomeSkew() {
        return surveysWithIncomeSkew;
    }

    /**
     * Sets surveys with income skew.
     *
     * @param surveysWithIncomeSkew the surveys with income skew
     */
    public void setSurveysWithIncomeSkew(Set<Survey> surveysWithIncomeSkew) {
        this.surveysWithIncomeSkew = surveysWithIncomeSkew;
    }

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
