package com.heidiaandahl.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests methods in SignUpAttempt. Currently focuses on validation checks.
 */
public class SignUpAttemptTest {

    /**
     * The Test attempt.
     */
    SignUpAttempt testAttempt;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        testAttempt = new SignUpAttempt();
    }

    /**
     * Has matching passwords success.
     */
    @Test
    void hasMatchingPasswordsSuccess() {
        testAttempt.setPassword("one");
        testAttempt.setPassword2("one");
        boolean testResult = testAttempt.hasMatchingPasswords();
        boolean expectedResult = true;
        assertEquals(expectedResult, testResult);
    }

    /**
     * Has matching passwords fail success.
     */
    @Test
    void hasMatchingPasswordsFailSuccess() {
        testAttempt.setPassword("one");
        testAttempt.setPassword2("x");
        boolean testResult = testAttempt.hasMatchingPasswords();
        boolean expectedResult = false;
        assertEquals(expectedResult, testResult);
    }

    /**
     * Has complete fields success.
     */
    @Test
    void hasCompleteFieldsSuccess() {
        testAttempt.setUsername("U");
        testAttempt.setPassword("x");
        testAttempt.setPassword2("y");
        testAttempt.setIncome("1");
        boolean testResult = testAttempt.hasCompleteFields();
        boolean expectedResult = true;
        assertEquals(expectedResult, testResult);
    }

    /**
     * Has complete fields fail income success.
     */
    @Test
    void hasCompleteFieldsFailIncomeSuccess() {
        testAttempt.setUsername("U");
        testAttempt.setPassword("x");
        testAttempt.setPassword2("y");
        testAttempt.setIncome("");
        boolean testResult = testAttempt.hasCompleteFields();
        boolean expectedResult = false;
        assertEquals(expectedResult, testResult);
    }

    /**
     * Has complete fields fail username success.
     */
    @Test
    void hasCompleteFieldsFailUsernameSuccess() {
        testAttempt.setUsername("");
        testAttempt.setPassword("x");
        testAttempt.setPassword2("y");
        testAttempt.setIncome("1");
        boolean testResult = testAttempt.hasCompleteFields();
        boolean expectedResult = false;
        assertEquals(expectedResult, testResult);
    }

    /**
     * Has complete fields fail password success.
     */
    @Test
    void hasCompleteFieldsFailPasswordSuccess() {
        testAttempt.setUsername("");
        testAttempt.setPassword("");
        testAttempt.setPassword2("y");
        testAttempt.setIncome("1");
        boolean testResult = testAttempt.hasCompleteFields();
        boolean expectedResult = false;
        assertEquals(expectedResult, testResult);
    }

    /**
     * Has complete fields fail password 2 success.
     */
    @Test
    void hasCompleteFieldsFailPassword2Success() {
        testAttempt.setUsername("");
        testAttempt.setPassword("x");
        testAttempt.setPassword2("");
        testAttempt.setIncome("1");
        boolean testResult = testAttempt.hasCompleteFields();
        boolean expectedResult = false;
        assertEquals(expectedResult, testResult);
    }

    /**
     * Has unique username success.
     */
    @Test
    void hasUniqueUsernameSuccess() {
        testAttempt.setUsername("heidi");
        boolean expectedResult = true;
        boolean testResult = testAttempt.hasUniqueUsername();
        assertEquals(expectedResult, testResult);
    }


    /**
     * Has unique username fail success.
     */
    @Test
    void hasUniqueUsernameFailSuccess() {
        testAttempt.setUsername("jean");
        boolean expectedResult = false;
        boolean testResult = testAttempt.hasUniqueUsername();
        assertEquals(expectedResult, testResult);
    }

    /**
     * Has valid income success.
     */
    @Test
    void hasValidIncomeSuccess() {
        testAttempt.setIncome("10");
        boolean expectedResult = true;
        boolean testResult = testAttempt.hasValidIncome();
        assertEquals(expectedResult,testResult);
    }

    /**
     * Has valid income negative fail success.
     */
    @Test
    void hasValidIncomeNegativeFailSuccess() {
        testAttempt.setIncome("-50");
        boolean expectedResult = false;
        boolean testResult = testAttempt.hasValidIncome();
        assertEquals(expectedResult,testResult);
    }

    /**
     * Has valid income non numeric fail success.
     */
    @Test
    void hasValidIncomeNonNumericFailSuccess() {
        testAttempt.setIncome("bad");
        boolean expectedResult = false;
        boolean testResult = testAttempt.hasValidIncome();
        assertEquals(expectedResult,testResult);
    }

    /**
     * Has selections from all menus success.
     */
    @Test
    void hasSelectionsFromAllMenusSuccess() {
        testAttempt.setHouseholdSize("3");
        testAttempt.setNeeds("1");
        testAttempt.setGoals("1");
        testAttempt.setIncomeSkew("1");
        boolean expectedResult = true;
        boolean testResult = testAttempt.hasSelectionsFromAllMenus();
        assertEquals(expectedResult,testResult);
    }

    /**
     * Has selections from all menus household size fail success.
     */
    @Test
    void hasSelectionsFromAllMenusHouseholdSizeFailSuccess() {
        testAttempt.setNeeds("1");
        testAttempt.setGoals("1");
        testAttempt.setIncomeSkew("1");
        boolean expectedResult = false;
        boolean testResult = testAttempt.hasSelectionsFromAllMenus();
        assertEquals(expectedResult,testResult);
    }

    /**
     * Has selections from all menus needs fail success.
     */
    @Test
    void hasSelectionsFromAllMenusNeedsFailSuccess() {
        testAttempt.setHouseholdSize("3");
        testAttempt.setGoals("1");
        testAttempt.setIncomeSkew("1");
        boolean expectedResult = false;
        boolean testResult = testAttempt.hasSelectionsFromAllMenus();
        assertEquals(expectedResult,testResult);
    }

    /**
     * Has selections from all menus goals fail success.
     */
    @Test
    void hasSelectionsFromAllMenusGoalsFailSuccess() {
        testAttempt.setHouseholdSize("3");
        testAttempt.setNeeds("1");
        testAttempt.setIncomeSkew("1");
        boolean expectedResult = false;
        boolean testResult = testAttempt.hasSelectionsFromAllMenus();
        assertEquals(expectedResult,testResult);
    }

    /**
     * Has selections from all menus income skew fail success.
     */
    @Test
    void hasSelectionsFromAllMenusIncomeSkewFailSuccess() {
        testAttempt.setHouseholdSize("3");
        testAttempt.setNeeds("1");
        testAttempt.setGoals("1");
        boolean expectedResult = false;
        boolean testResult = testAttempt.hasSelectionsFromAllMenus();
        assertEquals(expectedResult,testResult);
    }


    /**
     * Has selections from all menus all null fail success.
     */
    @Test
    void hasSelectionsFromAllMenusAllNullFailSuccess() {
        boolean expectedResult = false;
        boolean testResult = testAttempt.hasSelectionsFromAllMenus();
        assertEquals(expectedResult,testResult);
    }



}

