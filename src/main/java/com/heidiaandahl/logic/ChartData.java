package com.heidiaandahl.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heidiaandahl.entity.*;
import com.heidiaandahl.persistence.GenericDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class ChartData {

    private final Logger logger = LogManager.getLogger(this.getClass());
    // TODO - refactor; maybe by passing in type?  Maybe by pulling out code that is re-used?
    // TODO test

    /**
     * Returns a map of the needs descriptions and how many relevant surveys included that response
     * @return map of needs descriptions and number of respondents picking that level
     * @param matchingSurveys
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
     * @return map of goals descriptions and number of respondents picking that level
     * @param matchingSurveys
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
     * @return map of income skew descriptions and number of respondents picking that level
     * @param matchingSurveys
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

     // todo test

    public String getChartData(List<Survey> matchingSurveys) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        // organize survey statistics from matching surveys
        Map needsResponses = getNeedsResponses(matchingSurveys);
        Map goalsResponses = getGoalsResponses(matchingSurveys);
        Map incomeSkewResponses = getIncomeSkewResponses(matchingSurveys);

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
