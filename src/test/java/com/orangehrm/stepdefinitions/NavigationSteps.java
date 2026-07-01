package com.orangehrm.stepdefinitions;

import com.orangehrm.context.ScenarioContext;
import com.orangehrm.pages.modules.DashboardPage;
import com.orangehrm.pages.modules.LoginPage;
import io.cucumber.java.en.Given;

/**
 * Reusable background steps: "given I am logged in and on module X". Keeps the
 * module feature files free of repetitive login plumbing.
 */
public class NavigationSteps {

    private final ScenarioContext context;
    private final LoginPage loginPage = new LoginPage();
    private final DashboardPage dashboardPage = new DashboardPage();

    public NavigationSteps(ScenarioContext context) {
        this.context = context;
    }

    @Given("the admin user is logged in")
    public void adminLoggedIn() {
        loginPage.open().loginAsAdmin();
        context.set("loggedIn", true);
    }

    @Given("the admin navigates to the {string} module")
    public void navigateToModule(String module) {
        dashboardPage.navigateTo(module);
        context.set("currentModule", module);
    }
}
