package com.heidiaandahl.logic;

import com.heidiaandahl.entity.IncomeSkew;
import com.heidiaandahl.entity.Survey;
import com.heidiaandahl.persistence.GenericDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class SurveyAnswerTally<T> {
    private Class<T> type;
    private final Logger logger = LogManager.getLogger(this.getClass());

    public SurveyAnswerTally(Class<T> type) {
        this.type = type;
    }

    /**
     * Returns a map of the survey answers for a particular question and how many relevant surveys included that response.
     * @return map of the survey answers for a particular question and how many relevant surveys included that response
     * @param matchingSurveys
     */
    public Map<Integer, HashMap<String, Integer>> tallySurveyAnswers(List<Survey> matchingSurveys) {

        Map surveyAnswers = new TreeMap();

        // get all the descriptions using the dao
        GenericDao dao = new GenericDao(type);
        List<T> descriptions = dao.getAll();

        // loop through descriptions to map id to a hashmap of description and count
        for (T skewDescription: descriptions) {
            Set<Survey> incomeSkewSurveys = skewDescription.getSurveysWithIncomeSkew(); // TODO note: generic breaks down here.
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

            surveyAnswers.put(("id" + skewDescription.getId()), goalsCount);
        }

        return surveyAnswers;
    }
}
