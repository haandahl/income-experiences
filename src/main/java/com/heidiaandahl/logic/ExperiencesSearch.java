package com.heidiaandahl.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heidiaandahl.entity.Survey;
import com.heidiaandahl.entity.User;
import com.heidiaandahl.service.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Experiences search.
 */
public class ExperiencesSearch {
    public final static double incomePercentTarget = 0.1;  // TODO obtain via properties file

    private Properties properties;

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
     * @param properties       the application properties
     * @param targetIncome     the target income
     * @param targetFamilySize the target family size
     */
    public ExperiencesSearch(Properties properties, int targetIncome, int targetFamilySize) {
        this.properties = properties;
        this.targetIncome = targetIncome;
        this.targetFamilySize = targetFamilySize;
        this.career = null;
    }

    /**
     * Instantiates a new Experiences search.
     *
     * @param properties       the application properties
     * @param targetFamilySize the target family size
     * @param career           the career
     */
    public ExperiencesSearch(Properties properties, String career, int targetFamilySize) {
        this.properties = properties;
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

    public int getMedianWageFromBls(String career) {

        int medianWage = 0;
        Response webDevResponse = null;

        //TODO - turn career into code and make the following actually based on the search

        Client client = ClientBuilder.newClient();
        // TODO - obtain api key from properties and apply it here to allow more data calls
        // ?registrationkey=

        // api request for web developer wage
        WebTarget target =
                client.target("https://api.bls.gov/publicAPI/v2/timeseries/data/" +
                        "OEUN000000000000015113413");

        String response = target.request(MediaType.APPLICATION_JSON).get(String.class);

        ObjectMapper mapper = new ObjectMapper();

        try {
            webDevResponse = mapper.readValue(response, Response.class);

        } catch (IOException ioException) {
            // TODO - something to show problem
        } catch (Exception exception) {
            // TODO - something to show problem
        }

        // Get the first (only series) and its data items
        List<DataItem> webDevDataItems = webDevResponse.getResults().getSeries().get(0).getData();

        // Find average of wages for recent years
        // Not totally sure this is necessary - maybe only one year is ever marked latest?

        int wagesSum = 0;
        int numberOfWages = 0;

        for (DataItem item : webDevDataItems) {
            if (item.getLatest().equals("true")) {
                wagesSum += Integer.parseInt(item.getValue());
                numberOfWages += 1;
            }
        }

        if (numberOfWages > 0) {
            medianWage = Math.round(wagesSum / numberOfWages);
        }

        return medianWage;
    }

    /**
     * Establish income band set.
     *
     * @return the set
     */
    public Set<Survey> establishIncomeBand() {
        Set<Survey> returnedSurveys = new TreeSet<>();

        // where family size matches query and income is within +- 10% of query

        // nice to do: make sure there are balanced results above & below income level, and enough of them

        // set incomeResultsFloor and incomeResults Ceiling

        return returnedSurveys;
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

        int numberOfResponses = 0;
        return numberOfResponses;
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

        int numberOfResponses = 0;
        return numberOfResponses;
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


