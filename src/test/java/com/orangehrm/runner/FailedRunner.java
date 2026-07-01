package com.orangehrm.runner;

import io.cucumber.testng.CucumberOptions;

/**
 * Re-executes only the scenarios captured in rerun.txt from the previous run.
 * Pairs with the rerun plugin to give a "run, then re-run just the failures"
 * workflow in CI.
 */
@CucumberOptions(
        glue = {"com.orangehrm.stepdefinitions", "com.orangehrm.hooks"},
        features = {"@target/cucumber-reports/rerun.txt"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/failed-rerun-report.html",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true
)
public class FailedRunner extends BaseRunner { }
