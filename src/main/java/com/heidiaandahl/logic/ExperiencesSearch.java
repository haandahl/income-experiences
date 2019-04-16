package com.heidiaandahl.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heidiaandahl.entity.Survey;
import com.heidiaandahl.entity.User;
import com.heidiaandahl.persistence.GenericDao;
import com.heidiaandahl.service.*;

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
    public final static double incomePercentTarget = 0.1;  // TODO obtain via properties file

    // todo - don't delete anything yet, may refactor servlet
    private Properties properties;

    private int targetIncome;  // todo delete? - lives in controllerr
    private int targetFamilySize;
    private String career; // todo delete? - lives in controller

    // todo - Maybe not stored?
    private int incomeResultsFloor;
    private int incomeResultsCeiling;

    // todo - Maybe not stored?
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
     * @param properties       the properties
     * @param targetFamilySize the target family size
     */
    public ExperiencesSearch(Properties properties, int targetFamilySize) {
        this.properties = properties;
        this.targetFamilySize = targetFamilySize;
    }

    // TODO - get rid of the other constructors?

    /**
     * Instantiates a new Experiences search.
     *
     * @param properties       the application properties
     * @param targetIncome     the target income
     * @param targetFamilySize the target family size
     *
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
     *
    public ExperiencesSearch(Properties properties, String career, int targetFamilySize) {
        this.properties = properties;
        this.targetFamilySize = targetFamilySize;
        this.career = career;
    }
    */

    /**
     * Assemble chart information.
     */
    public void assembleChartInformation() {
        // JS will chart results, but I'm trying to gather info needed

       // this might organize some of the methods?  not sure yet

    }

    public int getMedianWageFromBls(String careerInput) {

        int medianWage = 0;

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
            // TODO - something to show problem
        } catch (Exception exception) {
            // TODO - something to show problem
        }

        // Get the first (only series) and its data items
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
            medianWage = Math.round(wagesSum / numberOfWages);
        }

        return medianWage;
    }

    // todo docs
    // TODO move a lot of stuff to servlet so servlet stores the ultimate % band searched and passes it to this method, also to dup less code
    public List<Survey> getSurveysNearlyMatchingIncome(long targetIncome, String storedPercentDifferenceFromTarget) {
        List<Survey> returnedSurveys = new ArrayList<>();
        GenericDao surveyDao = new GenericDao(Survey.class);

        // nice todo: make sure there are balanced results above & below income level, and enough of them
        // todo - decide how to get both surveys and users
            // I want surveys b/c in theory there could be more than one and I'd use them all
            // I want users, separately, to get their current profile stories
            // Ideally I'd only display the user profile if it's tied to the current survey
            // for now assume users' current profile can go with survey - this would be a maintenance upgrade
        //String storedIncomePercentTarget = properties.getProperty("search.income.percent");


        // todo - double check this... make sure it is tested maybe it should be parseDouble?
        double percentIncomeTarget = Double.parseDouble(storedPercentDifferenceFromTarget);
        int incomeFloor = (int) Math.round(targetIncome * (1 - percentIncomeTarget));
        int incomeCeiling = (int) Math.round(targetIncome * (1 + percentIncomeTarget));

        // returnedSurveys = surveyDao.getByPropertyRange("income", incomeFloor, incomeCeiling);
        // TODO apply new method to get by family size and icome range

        returnedSurveys = surveyDao.getByPropertiesValueAndRange("familySize", this.targetFamilySize, "income", incomeFloor, incomeCeiling);

        return returnedSurveys;
    }


    // todo why do i have 2 methods, same name, diff params??
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


