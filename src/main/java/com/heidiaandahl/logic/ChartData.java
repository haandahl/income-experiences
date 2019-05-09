package com.heidiaandahl.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heidiaandahl.entity.*;
import com.heidiaandahl.persistence.GenericDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Data from a search result, tallied by same responses across surveys.
 *
 * @author Heidi Aandahl
 */
public class ChartData {
     /* TODO - (future) - create a superclass for GoalsDescription, NeedsDescription, and IncomeSkew
                in order to streamline their code and this class. */

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Instantiates a new Chart data entity.
     */
    public ChartData() {
    }

    /**
     * Returns a map of the needs descriptions and how many relevant surveys included that response.
     *
     * @param searchResultSurveys the surveys included in the search results
     * @return map of needs descriptions and number of respondents picking that level
     */
    protected Map<Integer, HashMap<String, Integer>> getNeedsResponses(List<Survey> searchResultSurveys) {

        Map needsResponses = new TreeMap();

        // get all the descriptions using the dao
        GenericDao needsDescriptionDao = new GenericDao(NeedsDescription.class);
        List<NeedsDescription> needsDescriptions = needsDescriptionDao.getAll();

        // loop through descriptions to map id to a hashmap of description and count
        for (NeedsDescription needsDescription: needsDescriptions) {
            Set<Survey> needSurveys = needsDescription.getSurveysWithNeedsDescription();
            int matchingReponseTally = tallyMatchingSurveyAnswers(searchResultSurveys, needSurveys);

            Map needsCount = new HashMap();

            needsCount.put("description", needsDescription.getDescription());
            needsCount.put("count", matchingReponseTally);

            needsResponses.put(("id" + needsDescription.getId()), needsCount);
        }

        return needsResponses;
    }

    /**
     * Returns a map of the goals descriptions and how many relevant surveys included that response.
     *
     * @param searchResultSurveys the surveys included in the search results
     * @return map of goals descriptions and number of respondents picking that level
     */
    protected Map<Integer, HashMap<String, Integer>> getGoalsResponses(List<Survey> searchResultSurveys) {

        Map goalsResponses = new TreeMap();

        // get all the descriptions using the dao
        GenericDao goalsDescriptionDao = new GenericDao(GoalsDescription.class);
        List<GoalsDescription> goalsDescriptions = goalsDescriptionDao.getAll();

        // loop through descriptions to map id to a hashmap of description and count
        for (GoalsDescription goalsDescription: goalsDescriptions) {
            Set<Survey> goalsurveys = goalsDescription.getSurveysWithGoalsDescription();
            int matchingReponseTally = tallyMatchingSurveyAnswers(searchResultSurveys, goalsurveys);

            Map goalsCount = new HashMap();

            goalsCount.put("description", goalsDescription.getDescription());
            goalsCount.put("count", matchingReponseTally);

            goalsResponses.put(("id" + goalsDescription.getId()), goalsCount);
        }

        return goalsResponses;
    }


    /**
     * Returns a map of the income skew descriptions and how many relevant surveys included that response.
     *
     * @param searchResultSurveys the surveys included in the search results
     * @return map of income skew descriptions and number of respondents picking that level
     */
    protected Map<Integer, HashMap<String, Integer>> getIncomeSkewResponses(List<Survey> searchResultSurveys) {

        Map skewResponses = new TreeMap();

        // get all the descriptions using the dao
        GenericDao incomeSkewDao = new GenericDao(IncomeSkew.class);
        List<IncomeSkew> incomeSkewDescriptions = incomeSkewDao.getAll();

        // loop through descriptions to map id to a hashmap of description and count
        for (IncomeSkew skewDescription: incomeSkewDescriptions) {
            Set<Survey> incomeSkewSurveys = skewDescription.getSurveysWithIncomeSkew(); // specific to type, don't refactor
            int matchingReponseTally = tallyMatchingSurveyAnswers(searchResultSurveys, incomeSkewSurveys);

            Map goalsCount = new HashMap();

            goalsCount.put("description", skewDescription.getDescription());
            goalsCount.put("count", matchingReponseTally);

            skewResponses.put(("id" + skewDescription.getId()), goalsCount);
        }

        return skewResponses;
    }

    /**
     * Tally matching survey answers int.
     *
     * @param searchResultSurveys       the search result surveys
     * @param allSurveysWithTheResponse the all surveys with the response
     * @return the int
     */
    protected int tallyMatchingSurveyAnswers(List<Survey> searchResultSurveys, Set<Survey> allSurveysWithTheResponse) {
        int counter = 0;

        // increment the counter for each survey returne in the result that is associated
        // with a specific goals description
        // todo is this backward?? test first!
        for (Survey searchSurvey : searchResultSurveys) {
            if (allSurveysWithTheResponse.contains(searchSurvey)) {
                counter += 1;
            }
        }
        return counter;
    }

    /**
     * Gets chart data.
     *
     * @param searchResultSurveys the search result surveys
     * @return the chart data
     * @throws JsonProcessingException the json processing exception
     */
    public String getChartData(List<Survey> searchResultSurveys) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        // organize survey statistics from matching surveys
        Map needsResponses = getNeedsResponses(searchResultSurveys);
        Map goalsResponses = getGoalsResponses(searchResultSurveys);
        Map incomeSkewResponses = getIncomeSkewResponses(searchResultSurveys);

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
