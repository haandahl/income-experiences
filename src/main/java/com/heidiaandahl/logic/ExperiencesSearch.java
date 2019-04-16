package com.heidiaandahl.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heidiaandahl.entity.Survey;
import com.heidiaandahl.entity.User;
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

    /**
     * Assemble chart information.
     */
    public void assembleChartInformation() {
        // JS will chart results, but I'm trying to gather info needed

       // this might organize some of the methods?  not sure yet

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
            // TODO - something to show problem
        } catch (Exception exception) {
            // TODO - something to show problem
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

}


