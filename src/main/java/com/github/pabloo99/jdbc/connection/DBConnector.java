package com.github.pabloo99.jdbc.connection;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    private static final Logger logger = Logger.getLogger(DBConnector.class);

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/hr?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USERNAME = "pmazur";
    private static final String PASSWORD = "pmazur";

    public static Connection getMySqlConnection() {

        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("Missing MySQL driver");
            logger.error(e.getMessage(), e);
        }

        logger.info("MySQL JDBC Driver Registered!");

        try {
            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
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
