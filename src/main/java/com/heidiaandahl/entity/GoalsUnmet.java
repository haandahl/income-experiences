package com.heidiaandahl.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

/**
 * Survey data about which financial goals a user was able to meet. Not integrated into application as of 5/11/19 but
 * available for future enhancements.
 *
 * @author Heidi Aandahl
 */
@Entity(name = "GoalsUnmet")
@Table(name = "goals_unmet")
public class GoalsUnmet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

    @Column(name = "savings")
    private boolean savings;

    @Column(name = "career_ed")
    private boolean careerEducation;

    @Column(name = "needs_quality")
    private boolean needsQuality;

    @Column(name = "donations")
    private boolean donations;

    @Column(name = "recreation")
    private boolean recreation;

    @Column(name = "travel")
    private boolean travel;

    @Column(name = "services")
    private boolean services;

    @Column(name = "other")
    private boolean other;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "survey_id")
    private Survey survey;

    /**
     * Instantiates a new Goals unmet.
     */
    public GoalsUnmet() {
    }

    /**
     * Instantiates a new Goals unmet.
     *
     * @param savings         the savings
     * @param careerEducation the career education
     * @param needsQuality    the needs quality
     * @param donations       the donations
     * @param recreation      the recreation
     * @param travel          the travel
     * @param services        the services
     * @param other           the other
     * @param survey          the survey
     */
    public GoalsUnmet(boolean savings, boolean careerEducation, boolean needsQuality, boolean donations,
                      boolean recreation, boolean travel, boolean services, boolean other, Survey survey) {
        this.savings = savings;
        this.careerEducation = careerEducation;
        this.needsQuality = needsQuality;
        this.donations = donations;
        this.recreation = recreation;
        this.travel = travel;
        this.services = services;
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
     * Is savings boolean.
     *
     * @return the boolean
     */
    public boolean isSavings() {
        return savings;
    }

    /**
     * Sets savings.
     *
     * @param savings the savings
     */
    public void setSavings(boolean savings) {
        this.savings = savings;
    }

    /**
     * Is career education boolean.
     *
     * @return the boolean
     */
    public boolean isCareerEducation() {
        return careerEducation;
    }

    /**
     * Sets career education.
     *
     * @param careerEducation the career education
     */
    public void setCareerEducation(boolean careerEducation) {
        this.careerEducation = careerEducation;
    }

    /**
     * Is needs quality boolean.
     *
     * @return the boolean
     */
    public boolean isNeedsQuality() {
        return needsQuality;
    }

    /**
     * Sets needs quality.
     *
     * @param needsQuality the needs quality
     */
    public void setNeedsQuality(boolean needsQuality) {
        this.needsQuality = needsQuality;
    }

    /**
     * Is donations boolean.
     *
     * @return the boolean
     */
    public boolean isDonations() {
        return donations;
    }

    /**
     * Sets donations.
     *
     * @param donations the donations
     */
    public void setDonations(boolean donations) {
        this.donations = donations;
    }

    /**
     * Is recreation boolean.
     *
     * @return the boolean
     */
    public boolean isRecreation() {
        return recreation;
    }

    /**
     * Sets recreation.
     *
     * @param recreation the recreation
     */
    public void setRecreation(boolean recreation) {
        this.recreation = recreation;
    }

    /**
     * Is travel boolean.
     *
     * @return the boolean
     */
    public boolean isTravel() {
        return travel;
    }

    /**
     * Sets travel.
     *
     * @param travel the travel
     */
    public void setTravel(boolean travel) {
        this.travel = travel;
    }

    /**
     * Is services boolean.
     *
     * @return the boolean
     */
    public boolean isServices() {
        return services;
    }

    /**
     * Sets services.
     *
     * @param services the services
     */
    public void setServices(boolean services) {
        this.services = services;
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
        GoalsUnmet that = (GoalsUnmet) o;
        return id == that.id &&
                savings == that.savings &&
                careerEducation == that.careerEducation &&
                needsQuality == that.needsQuality &&
                donations == that.donations &&
                recreation == that.recreation &&
                travel == that.travel &&
                services == that.services &&
                other == that.other;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, savings, careerEducation, needsQuality, donations, recreation, travel, services, other);
    }

    @Override
    public String toString() {
        return "GoalsUnmet{" +
                "id=" + id +
                ", savings=" + savings +
                ", careerEducation=" + careerEducation +
                ", needsQuality=" + needsQuality +
                ", donations=" + donations +
                ", recreation=" + recreation +
                ", travel=" + travel +
                ", services=" + services +
                ", other=" + other +
                '}';
    }
}
