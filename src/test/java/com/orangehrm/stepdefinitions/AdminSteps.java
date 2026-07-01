package com.orangehrm.stepdefinitions;

import com.orangehrm.context.ScenarioContext;
import com.orangehrm.pages.modules.AdminPage;
import com.orangehrm.utils.FakerUtil;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

/** Steps for the Admin user-management flow (business flow #4). */
public class AdminSteps {

    private final ScenarioContext context;
    private final AdminPage adminPage = new AdminPage();

    public AdminSteps(ScenarioContext context) {
        this.context = context;
    }

    @When("the admin creates a user with role {string} and status {string} for employee {string}")
    public void createUser(String role, String status, String employee) {
        String username = FakerUtil.username();
        String password = FakerUtil.strongPassword();
        adminPage.createUser(username, role, status, employee, password);
        context.set("createdUsername", username);
    }

    @When("the admin searches for the created user")
    public void searchUser() {
        int count = adminPage.searchUser(context.getString("createdUsername"));
        context.set("userSearchCount", count);
    }

    @Then("the user should be found in the system records")
    public void userFound() {
        assertThat((int) context.get("userSearchCount"))
                .as("Created user must be searchable").isGreaterThan(0);
    }
}
