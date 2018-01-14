package com.github.pabloo99.jdbc.connection;

import com.github.pabloo99.jdbc.properties.PropertiesReader;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class OracleConnector implements CustomConnection {

    private static final Logger logger = Logger.getLogger(OracleConnector.class);

    private Properties properties;

    public OracleConnector() {
        PropertiesReader propertiesReader = new PropertiesReader();
        this.properties = propertiesReader.loadFromFile("db.properties");
    }

    public Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName(properties.getProperty("ORACLE_DRIVER_CLASS"));
        } catch (ClassNotFoundException e) {
            logger.error("Missing Oracle driver");
            logger.error(e.getMessage(), e);
        }

        logger.info("Oracle JDBC Driver Registered!");

        try {
            connection = DriverManager.getConnection(
                    properties.getProperty("ORACLE_URL"),
                    properties.getProperty("ORACLE_USERNAME"),
                    properties.getProperty("ORACLE_PASSWORD"));

            connection.setAutoCommit(true);

            if (connection != null) {
                logger.info("Connection successful!");
            } else {
                logger.error("Connection failed!");
            }

            return connection;

        } catch (SQLException e) {
            logger.error("Connection Failed! Check output console");
        }

        return null;
    }
}
