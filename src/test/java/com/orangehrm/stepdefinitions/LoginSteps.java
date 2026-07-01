package com.orangehrm.stepdefinitions;

import com.orangehrm.context.ScenarioContext;
import com.orangehrm.pages.modules.DashboardPage;
import com.orangehrm.pages.modules.LoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Steps for the Login & Dashboard flow (business flow #1). Page objects are
 * instantiated here; ScenarioContext is injected by PicoContainer so state is
 * shared with other step classes.
 */
public class LoginSteps {

    private final ScenarioContext context;
    private final LoginPage loginPage = new LoginPage();
    private final DashboardPage dashboardPage = new DashboardPage();

    public LoginSteps(ScenarioContext context) {
        this.context = context;
    }

    @Given("the user is on the OrangeHRM login page")
    public void userIsOnLoginPage() {
        loginPage.open();
        assertThat(loginPage.isLogoDisplayed())
                .as("Login branding should be visible").isTrue();
    }

    @When("the user logs in with valid admin credentials")
    public void loginWithValidCredentials() {
        loginPage.loginAsAdmin();
    }

    @When("the user logs in with username {string} and password {string}")
    public void loginWithCredentials(String username, String password) {
        loginPage.login(username, password);
    }

    @Then("the dashboard should be displayed")
    public void dashboardShouldBeDisplayed() {
        assertThat(dashboardPage.isLoaded())
                .as("Dashboard header should read 'Dashboard'").isTrue();
        context.set("loggedIn", true);
    }

    @And("the user profile menu and dashboard widgets should be visible")
    public void dashboardWidgetsVisible() {
        assertThat(dashboardPage.isUserDropdownDisplayed()).isTrue();
        assertThat(dashboardPage.isDashboardGridDisplayed()).isTrue();
    }

    @Then("an invalid credentials error should be shown")
    public void invalidCredentialsError() {
        assertThat(loginPage.getInvalidLoginError())
                .containsIgnoringCase("Invalid credentials");
    }

    @Then("a required field validation should be shown")
    public void requiredFieldError() {
        assertThat(loginPage.getRequiredFieldError())
                .containsIgnoringCase("Required");
    }
}
