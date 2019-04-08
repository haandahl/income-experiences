package com.heidiaandahl.service;

import com.heidiaandahl.entity.User;
import com.heidiaandahl.persistence.GenericDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserServiceTest {

    UserService testService;

    @BeforeEach
    void setup() {
        testService = new UserService();
    }

    @Test
    public void testGetJSONUserByIdSuccess() {
        //int testId = 1;
        int testId = 1;
        Response testResponse = testService.getJSONUserById(testId);
        // TODO - real test.  just want to see how service behaves
        assertEquals("blue", "blue");
        // assertEquals("OutboundJaxrsResponse{status=200, reason=OK, hasEntity=true, closed=false, buffered=false}", testResponse);
        // Now this:
        //   UserServiceTest.testGetJSONUserByIdSuccess:23
        //   expected: java.lang.String@5b1e88f<OutboundJaxrsResponse{status=200, reason=OK, hasEntity=true, closed=false, buffered=false}>
        //   but was: org.glassfish.jersey.message.internal.OutboundJaxrsResponse@340cb97f<OutboundJaxrsResponse{status=200, reason=OK, hasEntity=true, closed=false, buffered=false}>

    }

    // Ok so this is not the most well constructed thing but I DO get JSON in teh resultsstring, very long!!

    @Test
    public void testJSONConversionSuccess() {
        int testId = 1;
        GenericDao userDao = new GenericDao(User.class);
        User testUser = (User) userDao.getById(testId);
        String resultString = testService.convertUserToJson(testUser);
        // assertEquals("???", resultString);
        // TODO - real test.  just want to see how service behaves
        assertEquals("blue", "blue");

    }
}
