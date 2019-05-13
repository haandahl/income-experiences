package com.heidiaandahl.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heidiaandahl.entity.IncomeSkew;
import com.heidiaandahl.entity.Survey;
import com.heidiaandahl.persistence.GenericDao;
import com.heidiaandahl.test.util.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.regex.Pattern; // for test that needs improvement

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue; // for test that needs improvement

/**
 * This class tests methods in ChartData
 */
public class ChartDataTest {

    private ChartData chartData;
    private GenericDao surveyDao;
    private List<Survey> searchResultSurveys;
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        // Set up database with test data
        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");

        chartData = new ChartData();

        // create a list of 2 surveys representing those returned by a search
        /*  DB info
             -- survey (id, date, family size, income, user_id, needs_description_id, goals_description_id, income_skew_id)
               INSERT into survey values (1, '2018-01-01', 6, 65000, 4, 3, 2, 3);
               INSERT into survey values (3, '2019-01-15', 3, 50000, 1, 3, 1, 2);
         */
        surveyDao = new GenericDao(Survey.class);
        searchResultSurveys = new ArrayList<>();
        searchResultSurveys.add((Survey) surveyDao.getById(1));
        searchResultSurveys.add((Survey) surveyDao.getById(3));
    }

    /**
     * Tally matching survey answers success.
     */
    @Test
    void tallyMatchingSurveyAnswersSuccess() {
        // get surveys with income skew 2 from the database; should be surveys w/ id's 2 and 3
        /* DB info:
             -- survey (id, date, family size, income, user_id, needs_description_id, goals_description_id, income_skew_id)
            INSERT into survey values (2, '2018-03-04', 1, 150000, 8, 5, 5, 2);
            INSERT into survey values (3, '2019-01-15', 3, 50000, 1, 3, 1, 2);
         */
        GenericDao incomeSkewDao = new GenericDao(IncomeSkew.class);
        IncomeSkew incomeSkew2 = (IncomeSkew) incomeSkewDao.getById(2);
        Set<Survey> surveysWithIncomeSkew2 = incomeSkew2.getSurveysWithIncomeSkew();

        int skew2Tally = chartData.tallyMatchingSurveyAnswers(searchResultSurveys, surveysWithIncomeSkew2);

        assertEquals(1, skew2Tally);

    }

    /**
     * Gets needs responses success.
     *
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    void getNeedsResponsesSuccess() throws JsonProcessingException {
        Map<Integer, HashMap<String, Integer>> needsResponses = chartData.getNeedsResponses(searchResultSurveys);

        ObjectMapper mapper = new ObjectMapper();
        String needsResponsesJson = mapper.writeValueAsString(needsResponses);

        String expectedResponses = "{\"id1\":{\"count\":0,\"description\":\"Severely unmet needs caused permanent harm.\"},"
                + "\"id2\":{\"count\":0,\"description\":\"Unmet needs caused illness or decreased ability at work or school.\"},"
                + "\"id3\":{\"count\":2,\"description\":\"Unmet needs caused discomfort.\"},"
                + "\"id4\":{\"count\":0,\"description\":\"Needs were generally met.\"},"
                + "\"id5\":{\"count\":0,\"description\":\"All needs were comfortably met.\"}}";

        assertEquals(expectedResponses, needsResponsesJson);
    }

    /**
     * Gets goals responses success.
     *
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    void getGoalsResponsesSuccess() throws JsonProcessingException {
        Map<Integer, HashMap<String, Integer>> goalsResponses = chartData.getGoalsResponses(searchResultSurveys);

        ObjectMapper mapper = new ObjectMapper();
        String goalsResponsesJson = mapper.writeValueAsString(goalsResponses);

        String expectedResponses = "{\"id1\":{\"count\":1,\"description\":\"Unmet goals caused insecurity or high stress.\"},"
                + "\"id2\":{\"count\":1,\"description\":\"Unmet goals caused frustration.\"},"
                + "\"id3\":{\"count\":0,\"description\":\"Many goals were met.\"},"
                + "\"id4\":{\"count\":0,\"description\":\"Most or all goals were easily met.\"},"
                + "\"id5\":{\"count\":0,\"description\":\"Income allowed for new or expanding financial goals.\"}}";

        assertEquals(expectedResponses, goalsResponsesJson);
    }

    /**
     * Gets income skew responses success.
     *
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    void getIncomeSkewResponsesSuccess() throws JsonProcessingException {
        Map<Integer, HashMap<String, Integer>> incomeSkewResponses = chartData.getIncomeSkewResponses(searchResultSurveys);

        ObjectMapper mapper = new ObjectMapper();
        String incomeSkewResponsesJson = mapper.writeValueAsString(incomeSkewResponses);

        String expectedResponses = "{\"id1\":{\"count\":0,\"description\":\"little or no impact\"},"
                + "\"id2\":{\"count\":1,\"description\":\"some impact\"},"
                + "\"id3\":{\"count\":1,\"description\":\"strong impact\"}}";

        assertEquals(expectedResponses, incomeSkewResponsesJson);
    }

    /*
        TODO - (future) - fix the regex so the test passes when it should
            FYI - data returned from one call (which may not be consistent due to hashmap not perserving order):
            String expectedData = "{\"needs\":{\"id1\":{\"count\":0,\"description\":\"Severely unmet needs caused permanent harm.\"},"
                + "\"id2\":{\"count\":0,\"description\":\"Unmet needs caused illness or decreased ability at work or school.\"},"
                + "\"id3\":{\"count\":2,\"description\":\"Unmet needs caused discomfort.\"},"
                + "\"id4\":{\"count\":0,\"description\":\"Needs were generally met.\"},"
                + "\"id5\":{\"count\":0,\"description\":\"All needs were comfortably met.\"}},"
                + "\"incomeSkew\":{\"id1\":{\"count\":0,\"description\":\"little or no impact\"},"
                + "\"id2\":{\"count\":1,\"description\":\"some impact\"},"
                + "\"id3\":{\"count\":1,\"description\":\"strong impact\"}},"
                + "\"goals\":{\"id1\":{\"count\":1,\"description\":\"Unmet goals caused insecurity or high stress.\"},"
                + "\"id2\":{\"count\":1,\"description\":\"Unmet goals caused frustration.\"},"
                + "\"id3\":{\"count\":0,\"description\":\"Many goals were met.\"},"
                + "\"id4\":{\"count\":0,\"description\":\"Most or all goals were easily met.\"},"
                + "\"id5\":{\"count\":0,\"description\":\"Income allowed for new or expanding financial goals.\"}}}";

        @Test
        void getChartDataSuccess() throws JsonProcessingException {
        String testData = chartData.getChartData(searchResultSurveys);

        String expectedNeedsSubstring = "\"needs\":{\"id1\":{\"count\":0,\"description\":\"Severely unmet needs caused permanent harm.\"},"
                + "\"id2\":{\"count\":0,\"description\":\"Unmet needs caused illness or decreased ability at work or school.\"},"
                + "\"id3\":{\"count\":2,\"description\":\"Unmet needs caused discomfort.\"},"
                + "\"id4\":{\"count\":0,\"description\":\"Needs were generally met.\"},"
                + "\"id5\":{\"count\":0,\"description\":\"All needs were comfortably met.\"}}";

        String expectedGoalsSubstring = "\"goals\":{\"id1\":{\"count\":1,\"description\":\"Unmet goals caused insecurity or high stress.\"},"
                + "\"id2\":{\"count\":1,\"description\":\"Unmet goals caused frustration.\"},"
                + "\"id3\":{\"count\":0,\"description\":\"Many goals were met.\"},"
                + "\"id4\":{\"count\":0,\"description\":\"Most or all goals were easily met.\"},"
                + "\"id5\":{\"count\":0,\"description\":\"Income allowed for new or expanding financial goals.\"}}";

        String expectedSkewSubstring = "\"incomeSkew\":{\"id1\":{\"count\":0,\"description\":\"little or no impact\"},"
                + "\"id2\":{\"count\":1,\"description\":\"some impact\"},"
                + "\"id3\":{\"count\":1,\"description\":\"strong impact\"}}";

        // assertEquals(expectedData, testData); // TODO - remove

        //assertTrue(Pattern.matches(".*expectedNeedsSubstring.*", testData)); //TODO - improve regex to make it work
    }
     */

}
