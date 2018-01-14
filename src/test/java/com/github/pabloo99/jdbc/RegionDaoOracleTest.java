package com.github.pabloo99.jdbc;

import com.github.pabloo99.jdbc.connection.CustomConnection;
import com.github.pabloo99.jdbc.connection.OracleConnector;
import com.github.pabloo99.jdbc.dao.RegionsDAO;
import com.github.pabloo99.jdbc.entity.Region;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegionDaoOracleTest {

    private static final Logger logger = Logger.getLogger(RegionDaoOracleTest.class);

    private RegionsDAO regionsDAO;
    private CustomConnection oracleConnection;

    @BeforeClass
    public void setUp() {
        oracleConnection = new OracleConnector();
        regionsDAO = new RegionsDAO(oracleConnection);
    }

    @Test
    public void shouldSaveRegion() {
        Region regionToSave = new Region(5, "Africa");
        Region regionFound = new Region();

        try {
            regionsDAO.save(regionToSave);

            regionFound = regionsDAO.findById(5);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }

        Assert.assertEquals(regionToSave, regionFound);
    }

    @Test(dependsOnMethods = {"shouldSaveRegion"})
    public void shouldUpdateRegion() {
        Region item = new Region();
        Region afterUpdate = new Region();

        try {
            item = regionsDAO.findById(5);

            item.setName("Africa Test");

            regionsDAO.update(item);

            afterUpdate = regionsDAO.findById(5);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }

        Assert.assertEquals(afterUpdate.getName(), "Africa Test");
    }

    @Test(dependsOnMethods = {"shouldSaveRegion", "shouldUpdateRegion"})
    public void shouldDeleteRegions() {
        int countBeforeDelete = 0;
        int countAfterDelete = 0;

        try {
            countBeforeDelete = regionsDAO.findAll().size();

            regionsDAO.delete(5);

            countAfterDelete = regionsDAO.findAll().size();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }

        Assert.assertEquals(countAfterDelete, countBeforeDelete - 1);
    }


    @Test(dependsOnMethods = {"shouldDeleteRegions"})
    public void shouldFindAllRegions() {
        List<Region> regions = new ArrayList<>();

        try {
            regions.addAll(regionsDAO.findAll());
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }

        logger.info("Regions count: " + regions.size());

        Assert.assertEquals(regions.size(), 4);
    }
}
