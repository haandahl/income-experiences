package com.heidiaandahl.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import java.io.IOException;
import java.util.*;

/**
 * The median wage for a career, calculated from the Bureau of Labor Statistics (BLS) api. Note that BLS cannot
 * vouch for data once it is retrieved and manipulated externally.
 *
 * @author Heidi Aandahl
 */
public class CareerMedianWage {

    private final Logger logger = LogManager.getLogger(this.getClass());
    private Properties properties;

    /**
     * Instantiates a new Career median wage.
     */
    public CareerMedianWage() {
    }

    /**
     * Instantiates a new Career median wage.
     *
     * @param properties the properties
     */
    public CareerMedianWage(Properties properties) {
        this.properties = properties;
    }

    /**
     * Gets median wage from the Bureau of Labor Statistics, following a typical data pattern.
     *
     * BUG: There is at least one
     * known exception to this pattern: for an orthodontist, a median income is not provided, but some general
     * income information is provided in a footnote. This method is not set up to analyze exceptional data and
     * returns an income of 0 in that case.
     *
     * @param careerInput the career input
     * @return the median wage from bls
     */
    public long getMedianWageFromBls(String careerInput) {
        long medianWage = 0;
        List<DataItem> careerDataItems = new ArrayList<>();

        // create the BLS api uri
        String apiCareerCode = properties.getProperty(careerInput + ".bls.api.code");
        String apiUri = createApiUri(apiCareerCode);

        // TODO - (future) - refactor this method and try to call it as a validation step before a search proceeds
        // call the service to get BLS data for the career
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(apiUri);
        String response = target.request(MediaType.APPLICATION_JSON).get(String.class);
        logger.debug("BLS API RESPONSE: " + response);

        // map json to Java class
        Response careerResponse = getCareerResponse(response);

        // Get the first (only) series and its data items
        try {
            careerDataItems = careerResponse.getResults().getSeries().get(0).getData();
            medianWage = getMedianWage(careerDataItems);
        } catch (Exception exception) {
            logger.error(exception.getMessage());
        }

        return medianWage;
    }

    /**
     * Calculates the median wage for a career based on entities set up from a BLS api call.
     *
     * @param careerDataItems the career data items
     * @return the median wage
     */
    protected long getMedianWage(List<DataItem> careerDataItems) {
        long medianWage = 0;
        int wagesSum = 0;
        int numberOfWages = 0;

        // sum the number of data items marked "latest" and their respective values (median wages)
        for (DataItem item : careerDataItems) {
            if (item.getLatest().equals("true")) {
                wagesSum += Integer.parseInt(item.getValue());
                numberOfWages += 1;
            }
        }

        // find the average of the median wages
        if (numberOfWages > 0) {
            double averageMedianWage = (double) wagesSum / (double) numberOfWages;
            medianWage = Math.round(averageMedianWage);
        }

        return medianWage;
    }

    /**
     * Maps api response to a response entity.
     *
     * @param response the api response
     * @return the response entity
     */
    protected Response getCareerResponse(String response) {
        Response careerResponse = new Response();
        ObjectMapper mapper = new ObjectMapper();

        try {
            careerResponse = mapper.readValue(response, Response.class);
            logger.debug("careerResponse: " + careerResponse);
        } catch (IOException ioException) {
            logger.error(ioException.getMessage());
        } catch (Exception exception) {
            logger.error(exception.getMessage());
        }
        return careerResponse;
    }

    /**
     * Create the uri that retrieves BLS data including the median income for a given career code.
     *
     * @param apiCareerCode the api career code
     * @return the api uri string
     */
    protected String createApiUri(String apiCareerCode){
        String apiUri = "";
        apiUri = properties.getProperty("api.bls.endpoint") + properties.getProperty("api.bls.resource") +
                properties.getProperty("api.bls.survey") + apiCareerCode +
                properties.getProperty("api.bls.data") + properties.getProperty("api.bls.key.parameter");
        return apiUri;
    }
}
