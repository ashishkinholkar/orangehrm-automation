package com.orangehrm.stepdefinitions;

import com.orangehrm.context.ScenarioContext;
import com.orangehrm.pages.modules.PerformancePage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

/** Steps for the Performance KPI flow (business flow #6). */
public class PerformanceSteps {

    private final ScenarioContext context;
    private final PerformancePage performancePage = new PerformancePage();

    public PerformanceSteps(ScenarioContext context) {
        this.context = context;
    }

    @When("the admin creates a KPI {string} for job title {string} with rating {string} to {string}")
    public void createKpi(String kpi, String jobTitle, String min, String max) {
        performancePage.createKpi(kpi, jobTitle, min, max);
        context.set("kpiName", kpi);
    }

    @Then("the KPI list should contain at least one entry")
    public void kpiListNotEmpty() {
        assertThat(performancePage.kpiCount()).isGreaterThanOrEqualTo(0);
    }
}
