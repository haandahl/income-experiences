package com.heidiaandahl.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A survey taken by a site user when they sign up, which includes data about their financial situation and
 * household size.
 *
 * @author Heidi Aandahl
 */
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

    // todo - need to unmap unused survey parts?
    @OneToOne(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private NeedsUnmet needsUnmet;

    @OneToOne(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private GoalsUnmet goalsUnmet;

    /**
     * Instantiates a new Survey.
     */
    public Survey() {
    }

    /**
     * Instantiates a new Survey.
     *
     * @param surveyDate       the survey date
     * @param familySize       the family size
     * @param income           the income
     * @param participant      the participant
     * @param needsDescription the needs description
     * @param goalsDescription the goals description
     * @param incomeSkew       the income skew
     */
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
     * Gets survey date.
     *
     * @return the survey date
     */
    public LocalDate getSurveyDate() {
        return surveyDate;
    }

    /**
     * Sets survey date.
     *
     * @param surveyDate the survey date
     */
    public void setSurveyDate(LocalDate surveyDate) {
        this.surveyDate = surveyDate;
    }

    /**
     * Gets family size.
     *
     * @return the family size
     */
    public int getFamilySize() {
        return familySize;
    }

    /**
     * Sets family size.
     *
     * @param familySize the family size
     */
    public void setFamilySize(int familySize) {
        this.familySize = familySize;
    }

    /**
     * Gets income.
     *
     * @return the income
     */
    public int getIncome() {
        return income;
    }

    /**
     * Sets income.
     *
     * @param income the income
     */
    public void setIncome(int income) {
        this.income = income;
    }

    /**
     * Gets needs description.
     *
     * @return the needs description
     */
    public NeedsDescription getNeedsDescription() {
        return needsDescription;
    }

    /**
     * Sets needs description.
     *
     * @param needsDescription the needs description
     */
    public void setNeedsDescription(NeedsDescription needsDescription) {
        this.needsDescription = needsDescription;
    }

    /**
     * Gets goals description.
     *
     * @return the goals description
     */
    public GoalsDescription getGoalsDescription() {
        return goalsDescription;
    }

    /**
     * Sets goals description.
     *
     * @param goalsDescription the goals description
     */
    public void setGoalsDescription(GoalsDescription goalsDescription) {
        this.goalsDescription = goalsDescription;
    }

    /**
     * Gets income skew.
     *
     * @return the income skew
     */
    public IncomeSkew getIncomeSkew() {
        return incomeSkew;
    }

    /**
     * Sets income skew.
     *
     * @param incomeSkew the income skew
     */
    public void setIncomeSkew(IncomeSkew incomeSkew) {
        this.incomeSkew = incomeSkew;
    }

    /**
     * Gets participant.
     *
     * @return the participant
     */
    public User getParticipant() {
        return participant;
    }

    /**
     * Sets participant.
     *
     * @param participant the participant
     */
    public void setParticipant(User participant) {
        this.participant = participant;
    }

    /**
     * Gets needs unmet.
     *
     * @return the needs unmet
     */
    public NeedsUnmet getNeedsUnmet() {
        return needsUnmet;
    }

    /**
     * Sets needs unmet.
     *
     * @param needsUnmet the needs unmet
     */
    public void setNeedsUnmet(NeedsUnmet needsUnmet) {
        this.needsUnmet = needsUnmet;
    }

    /**
     * Gets goals unmet.
     *
     * @return the goals unmet
     */
    public GoalsUnmet getGoalsUnmet() {
        return goalsUnmet;
    }

    /**
     * Sets goals unmet.
     *
     * @param goalsUnmet the goals unmet
     */
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
