package com.heidiaandahl.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity(name = "Survey")
@Table(name = "survey")
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

    @Column(name = "date")
    private LocalDate surveyDate;

    @Column(name = "family_size")
    private int familySize;

    @Column (name = "income")
    private int income;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User participant;

    @ManyToOne
    @JoinColumn(name = "needs_description_id")
    private NeedsDescription needsDescription;

    @ManyToOne
    @JoinColumn(name = "goals_description_id")
    private GoalsDescription goalsDescription;

    @ManyToOne
    @JoinColumn(name = "income_skew_id")
    private IncomeSkew incomeSkew;

    @OneToOne(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private NeedsUnmet needsUnmet;

    @OneToOne(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private GoalsUnmet goalsUnmet;

    public Survey() {
    }

    public Survey(LocalDate surveyDate, int familySize, int income, User participant, NeedsDescription needsDescription,
                  GoalsDescription goalsDescription, IncomeSkew incomeSkew) {
        this.surveyDate = surveyDate;
        this.familySize = familySize;
        this.income = income;
        this.participant = participant;
        this.needsDescription = needsDescription;
        this.goalsDescription = goalsDescription;
        this.incomeSkew = incomeSkew;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getSurveyDate() {
        return surveyDate;
    }

    public void setSurveyDate(LocalDate surveyDate) {
        this.surveyDate = surveyDate;
    }

    public int getFamilySize() {
        return familySize;
    }

    public void setFamilySize(int familySize) {
        this.familySize = familySize;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public NeedsDescription getNeedsDescription() {
        return needsDescription;
    }

    public void setNeedsDescription(NeedsDescription needsDescription) {
        this.needsDescription = needsDescription;
    }

    public GoalsDescription getGoalsDescription() {
        return goalsDescription;
    }

    public void setGoalsDescription(GoalsDescription goalsDescription) {
        this.goalsDescription = goalsDescription;
    }

    public IncomeSkew getIncomeSkew() {
        return incomeSkew;
    }

    public void setIncomeSkew(IncomeSkew incomeSkew) {
        this.incomeSkew = incomeSkew;
    }

    public User getParticipant() {
        return participant;
    }

    public void setParticipant(User participant) {
        this.participant = participant;
    }

    public NeedsUnmet getNeedsUnmet() {
        return needsUnmet;
    }

    public void setNeedsUnmet(NeedsUnmet needsUnmet) {
        this.needsUnmet = needsUnmet;
    }

    public GoalsUnmet getGoalsUnmet() {
        return goalsUnmet;
    }

    public void setGoalsUnmet(GoalsUnmet goalsUnmet) {
        this.goalsUnmet = goalsUnmet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Survey survey = (Survey) o;
        return id == survey.id &&
                familySize == survey.familySize &&
                income == survey.income &&
                Objects.equals(surveyDate, survey.surveyDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, surveyDate, familySize, income);
    }

    @Override
    public String toString() {
        return "Survey{" +
                "id=" + id +
                ", surveyDate=" + surveyDate +
                ", familySize=" + familySize +
                ", income=" + income +
                '}';
    }
}
