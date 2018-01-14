package com.github.pabloo99.jdbc.dao;

import com.github.pabloo99.jdbc.connection.CustomConnection;
import com.github.pabloo99.jdbc.connection.MySqlConnector;
import com.github.pabloo99.jdbc.entity.Region;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegionsDAO {

    private static final Logger logger = Logger.getLogger(RegionsDAO.class);

    private final CustomConnection connector;

    public RegionsDAO(CustomConnection connector) {
        this.connector = connector;
    }

    public Region findById(int regionId) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        String query = "SELECT REGION_ID, REGION_NAME FROM REGIONS " +
                "WHERE REGION_ID = ?";

        try {
            connection = connector.getConnection();

            statement = connection.prepareStatement(query);
            statement.setInt(1, regionId);

            logger.info(query);

            // execute select SQL stetement
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("region_id");
                String name = rs.getString("region_name");

                return new Region(id, name);
            }

            rs.close();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }

        return null;
    }

    public List<Region> findAll() throws SQLException {
        Connection connection = null;
        Statement statement = null;

        String query = "SELECT * FROM REGIONS ";

        List<Region> regions = new ArrayList<>();

        try {
            connection = connector.getConnection();
            statement = connection.createStatement();

            logger.info(query);

            // execute select SQL stetement
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("region_id");
                String name = rs.getString("region_name");

                regions.add(new Region(id, name));
            }

            rs.close();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }

        return regions;
    }

    public void save(Region region) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        String query = "INSERT INTO REGIONS(REGION_ID, REGION_NAME)  " +
                "VALUES (?, ?)";

        try {
            connection = connector.getConnection();

            statement = connection.prepareStatement(query);
            statement.setInt(1, region.getId());
            statement.setString(2, region.getName());

            statement.executeUpdate();

            logger.info(query);

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }
    }

    public void delete(int regionId) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        String query = "DELETE FROM REGIONS WHERE REGION_ID = ? ";
        try {
            connection = connector.getConnection();

            statement = connection.prepareStatement(query);
            statement.setInt(1, regionId);

            int deletedRecords = statement.executeUpdate();

            logger.info(query);
            logger.info("Deleted records: " + deletedRecords);

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }
    }

    public void update(Region region) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        String query = "UPDATE REGIONS " +
                "SET REGION_NAME = ? WHERE REGION_ID = ?";

        try {
            connection = connector.getConnection();

            statement = connection.prepareStatement(query);
            statement.setString(1, region.getName());
            statement.setInt(2, region.getId());

            int updatedRecords = statement.executeUpdate();

            logger.info(query);
            logger.info("Updated records: " + updatedRecords);

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }
    }
}
