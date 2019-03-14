package com.heidiaandahl.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

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

    public GoalsUnmet() {
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSavings() {
        return savings;
    }

    public void setSavings(boolean savings) {
        this.savings = savings;
    }

    public boolean isCareerEducation() {
        return careerEducation;
    }

    public void setCareerEducation(boolean careerEducation) {
        this.careerEducation = careerEducation;
    }

    public boolean isNeedsQuality() {
        return needsQuality;
    }

    public void setNeedsQuality(boolean needsQuality) {
        this.needsQuality = needsQuality;
    }

    public boolean isDonations() {
        return donations;
    }

    public void setDonations(boolean donations) {
        this.donations = donations;
    }

    public boolean isRecreation() {
        return recreation;
    }

    public void setRecreation(boolean recreation) {
        this.recreation = recreation;
    }

    public boolean isTravel() {
        return travel;
    }

    public void setTravel(boolean travel) {
        this.travel = travel;
    }

    public boolean isServices() {
        return services;
    }

    public void setServices(boolean services) {
        this.services = services;
    }

    public boolean isOther() {
        return other;
    }

    public void setOther(boolean other) {
        this.other = other;
    }

    public Survey getSurvey() {
        return survey;
    }

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
