package com.heidiaandahl.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heidiaandahl.entity.*;
import com.heidiaandahl.persistence.GenericDao;
import com.heidiaandahl.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import java.io.IOException;
import java.util.*;

/**
 * The type Experiences search.
 */
public class ExperiencesSearch {
    private Properties properties;
    private int targetFamilySize;
    private double income;  // todo refactor as needed b/c was using int

    private final Logger logger = LogManager.getLogger(this.getClass());

    // TODO - redo getters, setters, others if needed
    // TODO - get users. for mvp, stories will be displayed for all linked users regardless of whether survey is current


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

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public int getTargetFamilySize() {
        return targetFamilySize;
    }

    public void setTargetFamilySize(int targetFamilySize) {
        this.targetFamilySize = targetFamilySize;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getMedianWageFromBls(String careerInput) {

        double medianWage = 0.0;

        Response careerResponse = null;

        String apiCareerCode = properties.getProperty(careerInput + ".bls.api.code");

        String apiUri = properties.getProperty("api.bls.endpoint") + properties.getProperty("api.bls.resource") +
                        properties.getProperty("api.bls.survey") + apiCareerCode +
                        properties.getProperty("api.bls.data") + properties.getProperty("api.bls.key.parameter");

        Client client = ClientBuilder.newClient();

        WebTarget target = client.target(apiUri);

        String response = target.request(MediaType.APPLICATION_JSON).get(String.class);

        ObjectMapper mapper = new ObjectMapper();

        try {
            careerResponse = mapper.readValue(response, Response.class);
        } catch (IOException ioException) {
            logger.error(ioException.getMessage());
        } catch (Exception exception) {
            logger.error(exception.getMessage());
        }

        // Get the first (only series) and its data items
        // todo - figure out how to handle orthodontist differently b/c data is different & NPE happens below (other pre-set careers are fine - no npe)
        List<DataItem> webDevDataItems = careerResponse.getResults().getSeries().get(0).getData();

        // Find average of wages for recent years
        // Not totally sure this is necessary - maybe only one year is ever marked latest?
        // todo - play with this more in testing
        int wagesSum = 0;
        int numberOfWages = 0;

        for (DataItem item : webDevDataItems) {
            if (item.getLatest().equals("true")) {
                wagesSum += Integer.parseInt(item.getValue());
                numberOfWages += 1;
            }
        }

        if (numberOfWages > 0) {
            medianWage = wagesSum / numberOfWages;
        }

        return medianWage;
    }

    // todo docs
    public List<Survey> getSurveysNearlyMatchingIncome(String storedPercentDifferenceFromTarget) {
        List<Survey> returnedSurveys = new ArrayList<>();
        GenericDao surveyDao = new GenericDao(Survey.class);

        double percentIncomeTarget = Double.parseDouble(storedPercentDifferenceFromTarget);
        int incomeFloor = (int) Math.round(this.income * (1 - percentIncomeTarget));
        int incomeCeiling = (int) Math.round(this.income * (1 + percentIncomeTarget));

        returnedSurveys = surveyDao.getByPropertiesValueAndRange("familySize", this.targetFamilySize, "income", incomeFloor, incomeCeiling);

        return returnedSurveys;
    }

    public boolean hasCorrectFields(String incomeTarget, String householdSizeInput, String careerInput) {
        boolean isValid = true;

        if (householdSizeInput == null || (incomeTarget == "" && careerInput == "")
                || (incomeTarget != "" && careerInput != "")) {
            isValid = false;
        }
        return isValid;
    }

    /**
     * Sets the income for this instance of the ExperiencesSearch if the value entered can be converted to a double.
     *
     * @param incomeTarget the income entered by the user
     * @return whether the income for this search was able to be set
     */
     public boolean usingThisIncome(String incomeTarget) {
        boolean usingThisIncome = false;
        double incomeDouble = 0.0;

         try {
              incomeDouble = Double.parseDouble((incomeTarget.replaceAll(",", "")).replaceAll("$", ""));
              this.income = incomeDouble;
              usingThisIncome = true;
         } catch (NumberFormatException numberFormatException) {
              logger.error("User entered non-numeric income.");
         } catch (Exception exception) {
             logger.error("There was a problem using the income entered by the user.");
         }

        return usingThisIncome;
    }

    // TODO - refactor; maybe by passing in type?  Maybe by pulling out code that is re-used?
    // TODO test

    /**
     * Returns a map of the needs descriptions and how many relevant surveys included that response
     * @param matchingSurveys surveys matching query
     * @return map of needs descriptions and number of respondents picking that level
     */
    public Map<Integer, HashMap<String, Integer>> getNeedsResponses(List<Survey> matchingSurveys) {

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
     * @param matchingSurveys surveys matching query
     * @return map of goals descriptions and number of respondents picking that level
     */
    public Map<Integer, HashMap<String, Integer>> getGoalsResponses(List<Survey> matchingSurveys) {

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
     * @param matchingSurveys surveys matching query
     * @return map of income skew descriptions and number of respondents picking that level
     */
    public Map<Integer, HashMap<String, Integer>> getIncomeSkewResponses(List<Survey> matchingSurveys) {

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
    public List<Story> getMatchingStories(List<Survey> matchingSurveys) {
        List<Story> matchingStories = new ArrayList<>();

        for (Survey survey : matchingSurveys) {
            GenericDao<Story> storyDao = new GenericDao(Story.class);
            User surveyPartipant = survey.getParticipant(); // todo separate this step in order to display link to user

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


