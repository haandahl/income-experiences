package com.heidiaandahl.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Survey {
    private int id;
    private LocalDate date;
    private int familySize;
    private int income;

    private NeedsDescription needsDescription;
    private GoalsDescription goalsDescription;
    private IncomeSkew incomeSkew;

    private NeedsUnmet needsUnmet;
    private GoalsUnmet goalsUnmet;

    // TODO read re: one-one relationships before doing relationships to 2 boolean chunks


    public Survey() {
    }

    public Survey(LocalDate date, int familySize, int income, NeedsDescription needsDescription,
                  GoalsDescription goalsDescription, IncomeSkew incomeSkew, NeedsUnmet needsUnmet, GoalsUnmet goalsUnmet) {
        this.date = date;
        this.familySize = familySize;
        this.income = income;
        this.needsDescription = needsDescription;
        this.goalsDescription = goalsDescription;
        this.incomeSkew = incomeSkew;
        this.needsUnmet = needsUnmet;
        this.goalsUnmet = goalsUnmet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    // TODO - maybe FK things don't go in equals/hash code... review

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Survey survey = (Survey) o;
        return id == survey.id &&
                familySize == survey.familySize &&
                income == survey.income &&
                Objects.equals(date, survey.date) &&
                Objects.equals(needsDescription, survey.needsDescription) &&
                Objects.equals(goalsDescription, survey.goalsDescription) &&
                Objects.equals(incomeSkew, survey.incomeSkew) &&
                Objects.equals(needsUnmet, survey.needsUnmet) &&
                Objects.equals(goalsUnmet, survey.goalsUnmet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, familySize, income, needsDescription, goalsDescription, incomeSkew, needsUnmet, goalsUnmet);
    }

    @Override
    public String toString() {
        return "Survey{" +
                "id=" + id +
                ", date=" + date +
                ", familySize=" + familySize +
                ", income=" + income +
                ", needsDescription=" + needsDescription +
                ", goalsDescription=" + goalsDescription +
                ", incomeSkew=" + incomeSkew +
                ", needsUnmet=" + needsUnmet +
                ", goalsUnmet=" + goalsUnmet +
                '}';
    }
}
