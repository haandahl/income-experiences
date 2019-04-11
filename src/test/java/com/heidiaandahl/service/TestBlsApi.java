package com.heidiaandahl.service;

import org.junit.jupiter.api.Test;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBlsApi {

    @Test
    public void testBlsApi() throws Exception {
        Client client = ClientBuilder.newClient();
        // TODO - obtain api key from properties and apply it here to allow more data calls
        // ?registrationkey=

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
     }
}

