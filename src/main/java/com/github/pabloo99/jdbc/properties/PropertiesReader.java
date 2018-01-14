package com.github.pabloo99.jdbc.properties;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {

    private static final Logger logger = Logger.getLogger(PropertiesReader.class);

    public Properties loadFromFile(String fileName) {

        Properties properties = new Properties();
        InputStream input = null;

        input = getClass().getClassLoader().getResourceAsStream(fileName);

        try {
            properties.load(input);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return properties;
    }
}
