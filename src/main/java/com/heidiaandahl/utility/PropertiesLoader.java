package com.heidiaandahl.utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

/**
 * An interface that loads a
 * <code style="color: gray; font-size: 0.8em;">Properties</code> object
 * using a properties file path as input.
 *
 * @author Eric Knapp
 * @author haandahl
 *
 */
public interface PropertiesLoader {

     /**
     * Loads a properties file into a
     * <code style="color: gray; font-size: 0.8em;">Properties</code>
     *  instance and returns it.
     *
     * @param propertiesFilePath a path a file on the java classpath list
     * @return a populated Properties instance or an empty Properties instance
     * if the file path was not found
     * @throws Exception if there is a problem loading the properties file
     */
    default Properties loadProperties(String propertiesFilePath) throws Exception {
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getResourceAsStream(propertiesFilePath));
        } catch (IOException ioException) {
            throw ioException;
        } catch (Exception exception) {
            throw exception;
        }
        return properties;
    }
}
