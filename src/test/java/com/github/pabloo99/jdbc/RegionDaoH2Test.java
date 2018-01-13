package com.github.pabloo99.jdbc;

import org.apache.commons.dbutils.DbUtils;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class RegionDaoH2Test {
    private Connection connection;

    @Test
    public void setupDB() throws Exception {
        Class.forName("org.h2.Driver");
        String db
                = "jdbc:h2:mem:;INIT=runscript from 'classpath:/hr.sql'";
        connection = DriverManager.getConnection(db);

        Statement statement = connection.createStatement();

        String query = "SELECT * FROM REGIONS ";

        ResultSet resultSet = statement.executeQuery(query);

        DbUtils.closeQuietly(connection);
    }
}
