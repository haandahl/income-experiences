package com.heidiaandahl.logic;

import com.heidiaandahl.entity.Survey;
import com.heidiaandahl.entity.User;

import java.util.List;
import java.util.Set;

/**
 * The type Experiences search.
 */
public class ExperiencesSearch {
    /**
     * The constant incomePercentTarget.
     */
    public final static double incomePercentTarget = 0.1;  // TODO properties file
    private int targetIncome;
    private int targetFamilySize;
    private String career;

    private int incomeResultsFloor;
    private int incomeResultsCeiling;

    // Maybe not stored?
    private Set<User> usersMatchingSearch;
    private Set<Survey> surveysMatchingSearch;

    /**
     * Instantiates a new Experiences search.
     */
    public ExperiencesSearch() {
    }

    /**
     * Instantiates a new Experiences search.
     *
     * @param targetIncome     the target income
     * @param targetFamilySize the target family size
     */
    public ExperiencesSearch(int targetIncome, int targetFamilySize) {
        this.targetIncome = targetIncome;
        this.targetFamilySize = targetFamilySize;
        this.career = null;
    }

    /**
     * Instantiates a new Experiences search.
     *
     * @param targetIncome     the target income
     * @param targetFamilySize the target family size
     * @param career           the career
     */
    public ExperiencesSearch(int targetIncome, int targetFamilySize, String career) {
        this.targetIncome = targetIncome;
        this.targetFamilySize = targetFamilySize;
        this.career = career;
    }

    /**
     * Assemble chart information.
     */
    public void assembleChartInformation() {
        // JS will chart results, but I'm trying to gather info needed

       // this might organize some of the methods?  not sure yet

    }

    /**
     * Establish income band set.
     *
     * @return the set
     */
    public Set<Survey> establishIncomeBand() {
        // where family size matches query and income is within +- 10% of query

        // nice to do: make sure there are balanced results above & below income level, and enough of them

        // set incomeResultsFloor and incomeResults Ceiling
    }

    /**
     * Gets number of responses matching criteria.
     *
     * @param criteria the criteria
     * @param value    the value
     * @return the number of responses matching criteria
     */
    public int getNumberOfResponsesMatchingCriteria(String criteria, boolean value) {
        // this is needed to build charts, I assume
    }

    /**
     * Gets number of responses matching criteria.
     *
     * @param criteria the criteria
     * @param value    the value
     * @return the number of responses matching criteria
     */
    public int getNumberOfResponsesMatchingCriteria(String criteria, String value) {
        // this is needed to build charts, I assume
    }

    public int getTargetIncome() {
        return targetIncome;
    }

    public void setTargetIncome(int targetIncome) {
        this.targetIncome = targetIncome;
    }

    public int getTargetFamilySize() {
        return targetFamilySize;
    }

    public void setTargetFamilySize(int targetFamilySize) {
        this.targetFamilySize = targetFamilySize;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public int getIncomeResultsFloor() {
        return incomeResultsFloor;
    }

    public void setIncomeResultsFloor(int incomeResultsFloor) {
        this.incomeResultsFloor = incomeResultsFloor;
    }

    public int getIncomeResultsCeiling() {
        return incomeResultsCeiling;
    }

    public void setIncomeResultsCeiling(int incomeResultsCeiling) {
        this.incomeResultsCeiling = incomeResultsCeiling;
    }

    public Set<User> getUsersMatchingSearch() {
        return usersMatchingSearch;
    }

    public void setUsersMatchingSearch(Set<User> usersMatchingSearch) {
        this.usersMatchingSearch = usersMatchingSearch;
    }

    public Set<Survey> getSurveysMatchingSearch() {
        return surveysMatchingSearch;
    }

    public void setSurveysMatchingSearch(Set<Survey> surveysMatchingSearch) {
        this.surveysMatchingSearch = surveysMatchingSearch;
    }

    @Override
    public String toString() {
        // TODO consider including sets if needed?
        return "ExperiencesSearch{" +
                "targetIncome=" + targetIncome +
                ", targetFamilySize=" + targetFamilySize +
                ", career='" + career + '\'' +
                ", incomeResultsFloor=" + incomeResultsFloor +
                ", incomeResultsCeiling=" + incomeResultsCeiling +
                '}';
    }

}


