package com.orangehrm.runner;

import io.cucumber.testng.CucumberOptions;

/**
 * Full regression suite. Tags resolved from the cucumber.filter.tags system
 * property (set in pom/CI) with a sensible default of @regression.
 */
@CucumberOptions(
        glue = {"com.orangehrm.stepdefinitions", "com.orangehrm.hooks"},
        features = {"src/test/resources/features"},
        tags = "@ashishtest",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber-html-report.html",
                "json:target/cucumber-reports/cucumber.json",
                "rerun:target/cucumber-reports/rerun.txt",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true
)
public class RegressionRunner extends BaseRunner { }
