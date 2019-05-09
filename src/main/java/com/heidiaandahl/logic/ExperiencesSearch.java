package com.heidiaandahl.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heidiaandahl.entity.*;
import com.heidiaandahl.persistence.GenericDao;
import com.heidiaandahl.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * The type Experiences search.
 */
public class ExperiencesSearch {
    private Properties properties;

    // TODO new...
    private String incomeInput;
    private String householdSizeInput;
    private String careerInput;
    private List<Survey> matchingSurveys;

    private final Logger logger = LogManager.getLogger(this.getClass());

    // TODO - redo getters, setters, others if needed

    /**
     * Instantiates a new Experiences search.
     */
    public ExperiencesSearch() {
    }

     /**
     * Instantiates a new Experiences search.
     *
     * @param properties       the properties
      */
      public ExperiencesSearch(Properties properties) {
            this.properties = properties;
      }

    public ExperiencesSearch(Properties properties, String incomeInput, String householdSizeInput, String careerInput) {
        this.properties = properties;
        this.incomeInput = incomeInput;
        this.householdSizeInput = householdSizeInput;
        this.careerInput = careerInput;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getIncomeInput() {
        return incomeInput;
    }

    public void setIncomeInput(String incomeInput) {
        this.incomeInput = incomeInput;
    }

    public String getHouseholdSizeInput() {
        return householdSizeInput;
    }

    public void setHouseholdSizeInput(String householdSizeInput) {
        this.householdSizeInput = householdSizeInput;
    }

    public String getCareerInput() {
        return careerInput;
    }

    public void setCareerInput(String careerInput) {
        this.careerInput = careerInput;
    }

    public List<Survey> getMatchingSurveys() {
        return matchingSurveys;
    }

    public void setMatchingSurveys(List<Survey> matchingSurveys) {
        this.matchingSurveys = matchingSurveys;
    }

    public String getValidationDetails() {
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

    private boolean isDependentOnFailedBlsSearch() {
        boolean dependentOnFailedBlsSearch = false;
        CareerMedianWage medianWage = new CareerMedianWage(properties);

        if (medianWage.getMedianWageFromBls(careerInput) == 0) {
            dependentOnFailedBlsSearch = true;
        }

        return dependentOnFailedBlsSearch;
    }

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



    // todo is it bad java to have a setter with a return value???
    // todo need setter to be understood by jsp?
    // todo how can i store the percent used at the same time?  maybe this could be a setter for an instance variable and return the percentage?
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

    // todo docs
    public List<Survey> getSurveysNearlyMatchingIncome(String storedPercentDifferenceFromTarget) {
        List<Survey> returnedSurveys = new ArrayList<>();
        GenericDao surveyDao = new GenericDao(Survey.class);

        int familySize = Integer.parseInt(householdSizeInput);
        long targetIncome = getTargetIncome();

        double percentIncomeTarget = Double.parseDouble(storedPercentDifferenceFromTarget);
        int incomeFloor = (int) Math.round(targetIncome * (1 - percentIncomeTarget));
        int incomeCeiling = (int) Math.round(targetIncome * (1 + percentIncomeTarget));

        returnedSurveys = surveyDao.getByPropertiesValueAndRange("familySize", familySize, "income", incomeFloor, incomeCeiling);

        return returnedSurveys;
    }

    // TODO - refactor; maybe by passing in type?  Maybe by pulling out code that is re-used?
    // TODO test

    /**
     * Returns a map of the needs descriptions and how many relevant surveys included that response
     * @return map of needs descriptions and number of respondents picking that level
     */
    public Map<Integer, HashMap<String, Integer>> getNeedsResponses() {

         Map needsResponses = new TreeMap();

        // get all the descriptions using the dao
        GenericDao needsDescriptionDao = new GenericDao(NeedsDescription.class);
        List<NeedsDescription> needsDescriptions = needsDescriptionDao.getAll();

        // loop through descriptions to map id to a hashmap of description and count
        for (NeedsDescription needsDescription: needsDescriptions) {
            Set<Survey> needSurveys = needsDescription.getSurveysWithNeedsDescription();
            int counter = 0;

            // increment the counter for each survey returne in the result that is associated
            // with a specific needs description
            for (Survey searchSurvey : matchingSurveys) {
                if (needSurveys.contains(searchSurvey)) {
                    counter += 1;
                }
            }

            Map needsCount = new HashMap();

            needsCount.put("description", needsDescription.getDescription());
            needsCount.put("count", counter);

            needsResponses.put(("id" + needsDescription.getId()), needsCount);
        }

        return needsResponses;
    }

    /**
     * Returns a map of the goals descriptions and how many relevant surveys included that response
     * @return map of goals descriptions and number of respondents picking that level
     */
    public Map<Integer, HashMap<String, Integer>> getGoalsResponses() {

        Map goalsResponses = new TreeMap();

        // get all the descriptions using the dao
        GenericDao goalsDescriptionDao = new GenericDao(GoalsDescription.class);
        List<GoalsDescription> goalsDescriptions = goalsDescriptionDao.getAll();

        // loop through descriptions to map id to a hashmap of description and count
        for (GoalsDescription goalsDescription: goalsDescriptions) {
            Set<Survey> goalsurveys = goalsDescription.getSurveysWithGoalsDescription();
            int counter = 0;

            // increment the counter for each survey returne in the result that is associated
            // with a specific goals description
            for (Survey searchSurvey : matchingSurveys) {
                if (goalsurveys.contains(searchSurvey)) {
                    counter += 1;
                }
            }

            Map goalsCount = new HashMap();

            goalsCount.put("description", goalsDescription.getDescription());
            goalsCount.put("count", counter);

            goalsResponses.put(("id" + goalsDescription.getId()), goalsCount);
        }

        return goalsResponses;
    }


    /**
     * Returns a map of the income skew descriptions and how many relevant surveys included that response
     * @return map of income skew descriptions and number of respondents picking that level
     */
    public Map<Integer, HashMap<String, Integer>> getIncomeSkewResponses() {

        Map skewResponses = new TreeMap();

        // get all the descriptions using the dao
        GenericDao incomeSkewDao = new GenericDao(IncomeSkew.class);
        List<IncomeSkew> incomeSkewDescriptions = incomeSkewDao.getAll();

        // loop through descriptions to map id to a hashmap of description and count
        for (IncomeSkew skewDescription: incomeSkewDescriptions) {
            Set<Survey> incomeSkewSurveys = skewDescription.getSurveysWithIncomeSkew();
            int counter = 0;

            // increment the counter for each survey returne in the result that is associated
            // with a specific goals description
            for (Survey searchSurvey : matchingSurveys) {
                if (incomeSkewSurveys.contains(searchSurvey)) {
                    counter += 1;
                }
            }

            Map goalsCount = new HashMap();

            goalsCount.put("description", skewDescription.getDescription());
            goalsCount.put("count", counter);

            skewResponses.put(("id" + skewDescription.getId()), goalsCount);
        }

        return skewResponses;
    }

    // todo doc and test
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

    // todo test

    public String getChartData() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        // organize survey statistics from matching surveys
        Map needsResponses = getNeedsResponses();
        Map goalsResponses = getGoalsResponses();
        Map incomeSkewResponses = getIncomeSkewResponses();

        // Put all the chart data in one map
        Map allResponses = new HashMap();
        allResponses.put("needs", needsResponses);
        allResponses.put("goals", goalsResponses);
        allResponses.put("incomeSkew", incomeSkewResponses);

        String allResponsesJson = mapper.writeValueAsString(allResponses);

        logger.debug(allResponsesJson);
        return allResponsesJson;
    }

}


