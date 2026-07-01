package com.orangehrm.runner;

import io.cucumber.testng.CucumberOptions;

/** Fast subset tagged @smoke - the gate before a regression run. */
@CucumberOptions(
        glue = {"com.orangehrm.stepdefinitions", "com.orangehrm.hooks"},
        features = {"src/test/resources/features"},
        tags = "@smoke",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/smoke-html-report.html",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true
)
public class SmokeRunner extends BaseRunner { }
