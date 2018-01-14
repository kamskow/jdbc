package com.github.pabloo99.jdbc.connection;

import com.github.pabloo99.jdbc.properties.PropertiesReader;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySqlConnector implements CustomConnection {

    private static final Logger logger = Logger.getLogger(MySqlConnector.class);

    private Properties properties;

    public MySqlConnector() {
        PropertiesReader propertiesReader = new PropertiesReader();
        this.properties = propertiesReader.loadFromFile("db.properties");
    }

    public Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName(properties.getProperty("MYSQL_DRIVER_CLASS"));
        } catch (ClassNotFoundException e) {
            logger.error("Missing MySQL driver");
            logger.error(e.getMessage(), e);
        }

        logger.info("MySQL JDBC Driver Registered!");

        try {
            connection = DriverManager.getConnection(
                    properties.getProperty("MYSQL_URL"),
                    properties.getProperty("MYSQL_USERNAME"),
                    properties.getProperty("MYSQL_PASSWORD"));

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
