package com.heidiaandahl.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Properties;


public class TestBlsApi {

    private Properties properties;
    private CareerMedianWage testWage;
    private final Logger logger = LogManager.getLogger(this.getClass());

    @BeforeEach
    void setUp() {
        properties = new Properties();
        try {
            // TODO - (future) - find a way to avoid duplicate production and test properties files?
            properties.load (this.getClass().getResourceAsStream("/incomeexperiences.properties"));
        } catch (IOException ioe) {
            logger.error(ioe.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        testWage = new CareerMedianWage(properties);
    }

    @Test
    public void getMedianWageFromBlsSuccess() {
        String testInput = "testJob1"; // web developer

        long testResult = testWage.getMedianWageFromBls(testInput);
        assertTrue(testResult > 0);
    }

    @Test
    public void getZeroMedianWageFromBlsWithBadInputSuccess(){
        String testBadInput = "garbage";

        long testResult = testWage.getMedianWageFromBls(testBadInput);
        assertTrue(testResult == 0);
    }

}

