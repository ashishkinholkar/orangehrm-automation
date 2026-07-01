package com.orangehrm.hooks;

import com.orangehrm.driver.DriverFactory;
import com.orangehrm.utils.ScreenshotUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;

/**
 * Lifecycle glue. @Before spins up a thread-local browser; @After tears it down
 * and, on failure, attaches a screenshot to both Allure and disk. Keeping driver
 * lifecycle in hooks (not steps) means every scenario gets a clean browser and
 * nothing leaks between scenarios in parallel runs.
 */
public class Hooks {

    private static final Logger log = LogManager.getLogger(Hooks.class);

    @Before(order = 0)
    public void setUp(Scenario scenario) {
        log.info("=========== STARTING: {} ===========", scenario.getName());
        DriverFactory.initDriver();
    }

    @After(order = 1)
    public void captureOnFailure(Scenario scenario) {
        if (scenario.isFailed()) {
            log.error("Scenario FAILED: {}", scenario.getName());
            byte[] shot = ScreenshotUtil.captureAsBytes();
            // Allure attachment (embedded in the HTML report)
            Allure.addAttachment("Failure - " + scenario.getName(),
                    new ByteArrayInputStream(shot));
            // Cucumber attachment (embedded in cucumber report)
            scenario.attach(shot, "image/png", scenario.getName());
            // Disk copy for the "execution screenshots" deliverable
            ScreenshotUtil.captureToFile(scenario.getName());
        } else {
            log.info("Scenario PASSED: {}", scenario.getName());
        }
    }

    @After(order = 0)
    public void tearDown(Scenario scenario) {
        DriverFactory.quitDriver();
        log.info("=========== FINISHED: {} | STATUS: {} ===========",
                scenario.getName(), scenario.getStatus());
    }
}
