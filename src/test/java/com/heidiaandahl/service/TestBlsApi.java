package com.heidiaandahl.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBlsApi {

    @Test
    public void testBlsApi() throws Exception {
        Client client = ClientBuilder.newClient();
        // TODO - obtain api key from properties and apply it here to allow more data calls
        // ?registrationkey=

        // api request for web developer wage
        WebTarget target =
                client.target("https://api.bls.gov/publicAPI/v2/timeseries/data/" +
                        "OEUN000000000000015113413");

        String expectedResponse = "{\"status\":\"REQUEST_SUCCEEDED\",\"responseTime\":266,\"message\":[\"No Data Available for Series OEUN000000000000015113413 Year: 2016\",\"No Data Available for Series OEUN000000000000015113413 Year: 2017\"],\"Results\":{\n" +
                "  \"series\":\n" +
                "  [{\"seriesID\":\"OEUN000000000000015113413\",\"data\":[{\"year\":\"2018\",\"period\":\"A01\",\"periodName\":\"Annual\",\"latest\":\"true\",\"value\":\"69430\",\"footnotes\":[{}]}]}]\n" +
                "}}";
        String response = target.request(MediaType.APPLICATION_JSON).get(String.class);
        // Note: this doesn't work because the respones includes responseTime, which varies
                // assertEquals(expectedResponse, response);

        // I think how this might work is we want to get the DataItem for which "latest" is "true" and return its "value"
        // I'm not sure how this might look if a career has data for more than one year
        // For the test case, the only value is 69430 so I'll try to get at that

        ObjectMapper mapper = new ObjectMapper();
        Response webDevResponse = mapper.readValue(response, Response.class);

        // For teh results get the first series, tehn get the data, in particular value?
        List<DataItem> webDevDataItems = webDevResponse.getResults().getSeries().get(0).getData();

        // List<Integer> webDevWages;
        int wagesSum = 0;
        int numberOfWages = 0;

        for (DataItem item : webDevDataItems) {
            if (item.getLatest().equals("true")) {
                // webDevWages.add(item.getValue().valueOf());
                wagesSum += Integer.parseInt(item.getValue());
                numberOfWages += 1;
            }
        }

        int averageRecentWage = 0;

        if (numberOfWages > 0) {
            averageRecentWage = Math.round(wagesSum / numberOfWages);
        }

        assertEquals(69430, averageRecentWage);

    }
}

