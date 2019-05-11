package com.heidiaandahl.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

/**
 * Survey data about which financial needs a user was able to meet. Not integrated into application as of 5/11/19 but
 * available for future enhancements.
 *
 * @author Heidi Aandahl
 */
@Entity(name = "NeedsUnmet")
@Table(name = "needs_unmet")
public class NeedsUnmet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

    @Column(name = "food")
    private boolean food;

    @Column(name = "housing")
    private boolean housing;

    @Column(name = "utilities")
    private boolean utilities;

    @Column(name = "health_care")
    private boolean healthcare;

    @Column(name = "clothing")
    private boolean clothing;

    @Column(name = "transportation")
    private boolean transportation;

    @Column(name = "child_care")
    private boolean childcare;

    @Column(name = "other")
    private boolean other;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "survey_id")
    private Survey survey;

    /**
     * Instantiates a new Needs unmet.
     */
    public NeedsUnmet() {
    }

    /**
     * Instantiates a new Needs unmet.
     *
     * @param food           the food
     * @param housing        the housing
     * @param utilities      the utilities
     * @param healthcare     the healthcare
     * @param clothing       the clothing
     * @param transportation the transportation
     * @param childcare      the childcare
     * @param other          the other
     * @param survey         the survey
     */
    public NeedsUnmet(boolean food, boolean housing, boolean utilities, boolean healthcare, boolean clothing,
                      boolean transportation, boolean childcare, boolean other, Survey survey) {
        this.food = food;
        this.housing = housing;
        this.utilities = utilities;
        this.healthcare = healthcare;
        this.clothing = clothing;
        this.transportation = transportation;
        this.childcare = childcare;
        this.other = other;
        this.survey = survey;
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
     * Is food boolean.
     *
     * @return the boolean
     */
    public boolean isFood() {
        return food;
    }

    /**
     * Sets food.
     *
     * @param food the food
     */
    public void setFood(boolean food) {
        this.food = food;
    }

    /**
     * Is housing boolean.
     *
     * @return the boolean
     */
    public boolean isHousing() {
        return housing;
    }

    /**
     * Sets housing.
     *
     * @param housing the housing
     */
    public void setHousing(boolean housing) {
        this.housing = housing;
    }

    /**
     * Is utilities boolean.
     *
     * @return the boolean
     */
    public boolean isUtilities() {
        return utilities;
    }

    /**
     * Sets utilities.
     *
     * @param utilities the utilities
     */
    public void setUtilities(boolean utilities) {
        this.utilities = utilities;
    }

    /**
     * Is healthcare boolean.
     *
     * @return the boolean
     */
    public boolean isHealthcare() {
        return healthcare;
    }

    /**
     * Sets healthcare.
     *
     * @param healthcare the healthcare
     */
    public void setHealthcare(boolean healthcare) {
        this.healthcare = healthcare;
    }

    /**
     * Is clothing boolean.
     *
     * @return the boolean
     */
    public boolean isClothing() {
        return clothing;
    }

    /**
     * Sets clothing.
     *
     * @param clothing the clothing
     */
    public void setClothing(boolean clothing) {
        this.clothing = clothing;
    }

    /**
     * Is transportation boolean.
     *
     * @return the boolean
     */
    public boolean isTransportation() {
        return transportation;
    }

    /**
     * Sets transportation.
     *
     * @param transportation the transportation
     */
    public void setTransportation(boolean transportation) {
        this.transportation = transportation;
    }

    /**
     * Is childcare boolean.
     *
     * @return the boolean
     */
    public boolean isChildcare() {
        return childcare;
    }

    /**
     * Sets childcare.
     *
     * @param childcare the childcare
     */
    public void setChildcare(boolean childcare) {
        this.childcare = childcare;
    }

    /**
     * Is other boolean.
     *
     * @return the boolean
     */
    public boolean isOther() {
        return other;
    }

    /**
     * Sets other.
     *
     * @param other the other
     */
    public void setOther(boolean other) {
        this.other = other;
    }

    /**
     * Gets survey.
     *
     * @return the survey
     */
    public Survey getSurvey() {
        return survey;
    }

    /**
     * Sets survey.
     *
     * @param survey the survey
     */
    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NeedsUnmet that = (NeedsUnmet) o;
        return id == that.id &&
                food == that.food &&
                housing == that.housing &&
                utilities == that.utilities &&
                healthcare == that.healthcare &&
                clothing == that.clothing &&
                transportation == that.transportation &&
                childcare == that.childcare &&
                other == that.other;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, food, housing, utilities, healthcare, clothing, transportation, childcare, other);
    }

    @Override
    public String toString() {
        return "NeedsUnmet{" +
                "id=" + id +
                ", food=" + food +
                ", housing=" + housing +
                ", utilities=" + utilities +
                ", healthcare=" + healthcare +
                ", clothing=" + clothing +
                ", transportation=" + transportation +
                ", childcare=" + childcare +
                ", other=" + other +
                '}';
    }
}
