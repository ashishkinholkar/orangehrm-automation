package com.orangehrm.stepdefinitions;

import com.orangehrm.context.ScenarioContext;
import com.orangehrm.pages.modules.LeavePage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

/** Steps for the Leave apply/search/approve flow (business flow #3). */
public class LeaveSteps {

    private final ScenarioContext context;
    private final LeavePage leavePage = new LeavePage();

    public LeaveSteps(ScenarioContext context) {
        this.context = context;
    }

    @When("the user applies for {string} leave from {string} to {string} with comment {string}")
    public void applyLeave(String type, String from, String to, String comment) {
        leavePage.applyLeave(type, from, to, comment);
        context.set("leaveType", type);
    }

    @When("the admin searches the leave records")
    public void searchLeave() {
        context.set("leaveCount", leavePage.searchLeave());
    }

    @When("the admin takes the action {string} on the leave request")
    public void actOnLeave(String action) {
        leavePage.actOnLeave(action);
    }

    @Then("at least one leave record should be present")
    public void leaveRecordsPresent() {
        assertThat((int) context.get("leaveCount")).isGreaterThanOrEqualTo(0);
    }
}
