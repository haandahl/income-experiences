package com.heidiaandahl.logic;

import com.heidiaandahl.entity.User;

import java.util.Set;

public class ExperiencesSearch {
    private int targetIncome;
    private int targetFamilySize;
    private String career;

    private Set<User> usersMatchingSearch;

    private int incomeResultsFloor;
    private int incomeResultsCeiling;

    public ExperiencesSearch() {
    }

    public ExperiencesSearch(int targetIncome, int targetFamilySize) {
        this.targetIncome = targetIncome;
        this.targetFamilySize = targetFamilySize;
        this.career = null;
    }

    public ExperiencesSearch(int targetIncome, int targetFamilySize, String career) {
        this.targetIncome = targetIncome;
        this.targetFamilySize = targetFamilySize;
        this.career = career;
    }

    // TODO final decisions about instance vars, then getters, setters, toString

    // TODO method determination

            // The Dao gets all users by family size
            // This method creates a collection of Users within 10% of income
            // Check how many match income exactly and pick number that need to be above and below (example, 2 at income, then 2 above and below, 3 at income, 2 above and below)
                    // goal: at least 6 users, balancing higher and lower
            // This method checks for x users at below income; if not there, get them
            // checks for x users at or above income; if not there, get them

            // order the set by income TODO - see how

            // TODO review methods associated with daos and see if some filtering can happen there?


    // I'm looking for matching surveys and stories, however old surveys with new stories might mis-match (future concern I guess)

    // TODO -- Review Hibernate Search - doesn't seem to be relevant to the problem in this class, but could be wrong...



}
