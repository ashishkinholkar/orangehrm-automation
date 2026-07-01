package com.orangehrm.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/**
 * Parent runner holding shared CucumberOptions. Concrete runners override the
 * tags they execute. Overriding scenarios() with parallel=true is what enables
 * scenario-level parallelism on top of the TestNG thread-count.
 */
@CucumberOptions(
        glue = {"com.orangehrm.stepdefinitions", "com.orangehrm.hooks"},
        features = {"src/test/resources/features"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber-html-report.html",
                "json:target/cucumber-reports/cucumber.json",
                "rerun:target/cucumber-reports/rerun.txt",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true
)
public class BaseRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
