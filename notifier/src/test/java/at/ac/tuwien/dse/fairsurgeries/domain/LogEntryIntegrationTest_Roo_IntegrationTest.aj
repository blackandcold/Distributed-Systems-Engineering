// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package at.ac.tuwien.dse.fairsurgeries.domain;

import at.ac.tuwien.dse.fairsurgeries.domain.LogEntryDataOnDemand;
import at.ac.tuwien.dse.fairsurgeries.domain.LogEntryIntegrationTest;
import at.ac.tuwien.dse.fairsurgeries.service.LogEntryService;
import java.math.BigInteger;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

privileged aspect LogEntryIntegrationTest_Roo_IntegrationTest {
    
    declare @type: LogEntryIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: LogEntryIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml");
    
    @Autowired
    private LogEntryDataOnDemand LogEntryIntegrationTest.dod;
    
    @Autowired
    LogEntryService LogEntryIntegrationTest.logEntryService;
    
    @Test
    public void LogEntryIntegrationTest.testCountAllLogEntrys() {
        Assert.assertNotNull("Data on demand for 'LogEntry' failed to initialize correctly", dod.getRandomLogEntry());
        long count = logEntryService.countAllLogEntrys();
        Assert.assertTrue("Counter for 'LogEntry' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void LogEntryIntegrationTest.testFindLogEntry() {
        LogEntry obj = dod.getRandomLogEntry();
        Assert.assertNotNull("Data on demand for 'LogEntry' failed to initialize correctly", obj);
        BigInteger id = obj.getId();
        Assert.assertNotNull("Data on demand for 'LogEntry' failed to provide an identifier", id);
        obj = logEntryService.findLogEntry(id);
        Assert.assertNotNull("Find method for 'LogEntry' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'LogEntry' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void LogEntryIntegrationTest.testFindAllLogEntrys() {
        Assert.assertNotNull("Data on demand for 'LogEntry' failed to initialize correctly", dod.getRandomLogEntry());
        long count = logEntryService.countAllLogEntrys();
        Assert.assertTrue("Too expensive to perform a find all test for 'LogEntry', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<LogEntry> result = logEntryService.findAllLogEntrys();
        Assert.assertNotNull("Find all method for 'LogEntry' illegally returned null", result);
        Assert.assertTrue("Find all method for 'LogEntry' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void LogEntryIntegrationTest.testFindLogEntryEntries() {
        Assert.assertNotNull("Data on demand for 'LogEntry' failed to initialize correctly", dod.getRandomLogEntry());
        long count = logEntryService.countAllLogEntrys();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<LogEntry> result = logEntryService.findLogEntryEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'LogEntry' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'LogEntry' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void LogEntryIntegrationTest.testSaveLogEntry() {
        Assert.assertNotNull("Data on demand for 'LogEntry' failed to initialize correctly", dod.getRandomLogEntry());
        LogEntry obj = dod.getNewTransientLogEntry(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'LogEntry' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'LogEntry' identifier to be null", obj.getId());
        logEntryService.saveLogEntry(obj);
        Assert.assertNotNull("Expected 'LogEntry' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void LogEntryIntegrationTest.testDeleteLogEntry() {
        LogEntry obj = dod.getRandomLogEntry();
        Assert.assertNotNull("Data on demand for 'LogEntry' failed to initialize correctly", obj);
        BigInteger id = obj.getId();
        Assert.assertNotNull("Data on demand for 'LogEntry' failed to provide an identifier", id);
        obj = logEntryService.findLogEntry(id);
        logEntryService.deleteLogEntry(obj);
        Assert.assertNull("Failed to remove 'LogEntry' with identifier '" + id + "'", logEntryService.findLogEntry(id));
    }
    
}
