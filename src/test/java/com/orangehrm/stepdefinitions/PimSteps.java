package com.orangehrm.stepdefinitions;

import com.orangehrm.context.ScenarioContext;
import com.orangehrm.pages.modules.PimPage;
import com.orangehrm.utils.FakerUtil;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

/** Steps for the PIM employee lifecycle (business flow #2). */
public class PimSteps {

    private final ScenarioContext context;
    private final PimPage pimPage = new PimPage();

    public PimSteps(ScenarioContext context) {
        this.context = context;
    }

    @When("the admin adds a new employee with a generated name")
    public void addEmployee() {
        String first = FakerUtil.firstName();
        String last  = FakerUtil.lastName();
        String empId = pimPage.addEmployee(first, last);
        context.set("empFirstName", first);
        context.set("empLastName", last);
        context.set("empId", empId);
    }

    @When("the admin uploads the profile image {string}")
    public void uploadProfileImage(String fileName) {
        pimPage.uploadProfileImage(fileName);
    }

    @When("the admin searches for the created employee")
    public void searchCreatedEmployee() {
        String fullName = context.getString("empFirstName") + " " + context.getString("empLastName");
        int count = pimPage.searchEmployee(fullName);
        context.set("searchResultCount", count);
    }

    @Then("the employee should appear in the search results")
    public void employeeAppearsInResults() {
        assertThat((int) context.get("searchResultCount"))
                .as("Created employee must be searchable").isGreaterThan(0);
    }

    @Then("a confirmation toast should be displayed")
    public void confirmationToast() {
        assertThat(pimPage.isToastDisplayed()).isTrue();
    }
}
