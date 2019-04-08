package com.heidiaandahl.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heidiaandahl.entity.User;
import com.heidiaandahl.persistence.GenericDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users")
public class UserService {

    private final Logger logger = LogManager.getLogger(this.getClass());
    private GenericDao userDao = new GenericDao(User.class);
    private ObjectMapper mapper = new ObjectMapper();

    @GET
    @Path("/json/{id}")
    @Produces("application/json")
    public Response getJSONUserById(@PathParam("id") int id) {
        Response response = Response.status(500).build();
        User requestedUser = (User) userDao.getById(id); // looks ok
        String userJsonString = "";

        if (requestedUser == null) {
            response = Response.status(404).build();
        } else {
            userJsonString = convertUserToJson(requestedUser);
        }

        if (userJsonString == null) {
            response = Response.status(500).build();
        } else {
            response = Response.status(200).entity(userJsonString).build();
        }
        return response;
    }

    public String convertUserToJson(User requestedUser) {
        // Convert requested user to JSON String
        // Resource: https://www.mkyong.com/java/jackson-2-convert-java-object-to-from-json/
        String userJsonString = "";
        try {
            userJsonString = mapper.writeValueAsString(requestedUser);
        } catch (JsonProcessingException jsonProcessingException) {
            logger.error("A JsonProcessingException occurred when attempting to represent a user as a JSON string.");
        } catch (Exception exception) {
            logger.error("An exception occurred when attempting to represent a user as a JSON string.");
        }
        return userJsonString;
    }
}
