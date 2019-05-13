package com.heidiaandahl.logic;

import com.heidiaandahl.entity.*;
import com.heidiaandahl.persistence.GenericDao;
import com.heidiaandahl.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * A search conducted by the user that finds data relevant to a combination of career and household size or
 * a user-selected income and household size.
 *
 * @author Heidi Aandahl
 */
public class ExperiencesSearch {
    private Properties properties;

    private String incomeInput;
    private String householdSizeInput;
    private String careerInput;
    private List<Survey> matchingSurveys;

    private final Logger logger = LogManager.getLogger(this.getClass());


    /**
     * Instantiates a new Experiences search.
     */
    public ExperiencesSearch() {
    }

    /**
     * Instantiates a new Experiences search.
     *
     * @param properties the properties
     */
    public ExperiencesSearch(Properties properties) {
            this.properties = properties;
      }

    /**
     * Instantiates a new Experiences search.
     *
     * @param properties         the properties
     * @param incomeInput        the income input
     * @param householdSizeInput the household size input
     * @param careerInput        the career input
     */
    public ExperiencesSearch(Properties properties, String incomeInput, String householdSizeInput, String careerInput) {
        this.properties = properties;
        this.incomeInput = incomeInput;
        this.householdSizeInput = householdSizeInput;
        this.careerInput = careerInput;
    }

    /**
     * Gets properties.
     *
     * @return the properties
     */
    public Properties getProperties() {
        return properties;
    }

    /**
     * Sets properties.
     *
     * @param properties the properties
     */
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    /**
     * Gets income input.
     *
     * @return the income input
     */
    public String getIncomeInput() {
        return incomeInput;
    }

    /**
     * Sets income input.
     *
     * @param incomeInput the income input
     */
    public void setIncomeInput(String incomeInput) {
        this.incomeInput = incomeInput;
    }

    /**
     * Gets household size input.
     *
     * @return the household size input
     */
    public String getHouseholdSizeInput() {
        return householdSizeInput;
    }

    /**
     * Sets household size input.
     *
     * @param householdSizeInput the household size input
     */
    public void setHouseholdSizeInput(String householdSizeInput) {
        this.householdSizeInput = householdSizeInput;
    }

    /**
     * Gets career input.
     *
     * @return the career input
     */
    public String getCareerInput() {
        return careerInput;
    }

    /**
     * Sets career input.
     *
     * @param careerInput the career input
     */
    public void setCareerInput(String careerInput) {
        this.careerInput = careerInput;
    }

    /**
     * Gets matching surveys.
     *
     * @return the matching surveys
     */
    public List<Survey> getMatchingSurveys() {
        return matchingSurveys;
    }

    /**
     * Gets validation details.
     *
     * @return the validation details
     */
    public String getValidationDetails() {
        // TODO - (future) - after separating the api call from the getMedianWageFromBls method in CareerMedianWage,
        //  try the api call as a validation step here and notify the user if the api is unavailable

        String validationDetails = "";

        if (!hasCorrectFields()) {
            validationDetails += " You need to search on a career or an income (not both) " +
            "and must select a household size.";
        }

        if (isDependentOnImproperIncome()) {
            validationDetails += "  The income must be a positive number without symbols. Example: 70000, not $70,000 or -70000.";
        }

        if (isDependentOnFailedBlsSearch()) {
            validationDetails += " Sorry, the BLS income could not be calculated for the career you chose.";
        }

         return validationDetails;
    }

    private boolean hasCorrectFields() {
        boolean hasCorrectFields = true;

        if (householdSizeInput == null || (incomeInput == "" && careerInput == "")
                || (incomeInput != "" && careerInput != "")) {
            hasCorrectFields = false;
        }
        return hasCorrectFields;
    }

    private boolean isDependentOnImproperIncome() {
          boolean dependentOnImproperIncome = false;

          if (incomeInput != "") {
              try {
                  int incomeInt = Integer.parseInt(incomeInput);
                  if (incomeInt < 0) {
                      dependentOnImproperIncome = true;
                  }
               } catch (NumberFormatException numberFormatException) {
                  dependentOnImproperIncome = true;
              } catch (Exception exception) {
                  dependentOnImproperIncome = true;
              }
          }
          return dependentOnImproperIncome;
    }

    /**
     * Checks whether the search is depending on a BLS api search that can't return an income.
     *
     * @return whether the search is dependent on a failed BLS api search for income
     */
    private boolean isDependentOnFailedBlsSearch() {
        boolean dependentOnFailedBlsSearch = false;

        CareerMedianWage medianWage = new CareerMedianWage(properties);
        long blsWage = medianWage.getMedianWageFromBls(careerInput);

        if (incomeInput == "" && blsWage == 0) {
            dependentOnFailedBlsSearch = true;
            logger.error("BLS api failed to return income for " + careerInput);
        }

        return dependentOnFailedBlsSearch;
    }

    /**
     * Get target income long.
     *
     * @return the long
     */
    public long getTargetIncome(){
        long targetIncome = 0;

        CareerMedianWage medianWage = new CareerMedianWage(properties);

        if (incomeInput != "") {
            targetIncome = Integer.parseInt(incomeInput);
        } else {
            targetIncome = medianWage.getMedianWageFromBls(careerInput);
        }
        return targetIncome;
     }

    /**
     * Searches for and returns surveys based on two tiers: a small percent difference from the target income if
     * possible, and an alternate (larger) percent difference if no results were returned with the smaller number.
     *
     * @return the matching surveys
     */
    public String setMatchingSurveys() {
         List<Survey> matchingSurveys = new ArrayList<>();
         String storedPercentDifferenceFromTarget = "";

         // try to get surveys matching family size and closely matching income
         storedPercentDifferenceFromTarget = properties.getProperty("search.income.percent");
         matchingSurveys = getSurveysNearlyMatchingIncome(storedPercentDifferenceFromTarget);


        // If no surveys matched, search again with a bigger income range
        if (matchingSurveys.isEmpty()) {
            storedPercentDifferenceFromTarget = properties.getProperty("search.income.percent.alternate");
            matchingSurveys = getSurveysNearlyMatchingIncome(storedPercentDifferenceFromTarget);
        }

        this.matchingSurveys = matchingSurveys;

        return storedPercentDifferenceFromTarget;
    }

    /**
     * Gets surveys nearly matching income.
     *
     * @param storedPercentDifferenceFromTarget the percent from the target income
     * @return the surveys nearly matching income
     */
    public List<Survey> getSurveysNearlyMatchingIncome(String storedPercentDifferenceFromTarget) {
        List<Survey> returnedSurveys = new ArrayList<>();
        GenericDao surveyDao = new GenericDao(Survey.class);

        int familySize = Integer.parseInt(householdSizeInput);
        long targetIncome = getTargetIncome();

        double percentIncomeTarget = Double.parseDouble(storedPercentDifferenceFromTarget);
        int incomeFloor = (int) Math.round(targetIncome * (1 - percentIncomeTarget));
        int incomeCeiling = (int) Math.round(targetIncome * (1 + percentIncomeTarget));

        returnedSurveys = surveyDao.getByPropertiesValueAndRange("familySize",
                familySize, "income", incomeFloor, incomeCeiling);

        return returnedSurveys;
    }

    /**
     * Gets matching stories.
     *
     * @return the matching stories
     */
    public List<Story> getMatchingStories() {
        List<Story> matchingStories = new ArrayList<>();

        for (Survey survey : matchingSurveys) {
            GenericDao<Story> storyDao = new GenericDao(Story.class);
            User surveyPartipant = survey.getParticipant();

            // Map the search criteria
            Map<String, Object> storyCriteria = new HashMap<>();
            storyCriteria.put("profileUser", surveyPartipant);
            storyCriteria.put("isVisible", true);

            // get the stories that match (there should just be one)
            List<Story> stories = (List<Story>) storyDao.getByPropertyNames(storyCriteria);

            for (Story story : stories) {
                matchingStories.add(story);
            }
        }

        return matchingStories;
    }
}


