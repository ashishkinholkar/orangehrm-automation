package com.orangehrm.pages.modules;

import com.orangehrm.pages.base.BasePage;
import org.openqa.selenium.By;

import io.qameta.allure.Step;

/**
 * Landing page after a successful login plus the left-hand navigation that the
 * other module pages reuse to move around the app.
 */
public class DashboardPage extends BasePage {

    private final By headerTitle = By.cssSelector("h6.oxd-topbar-header-breadcrumb-module");
    private final By userDropdown = By.cssSelector(".oxd-userdropdown-tab");
    private final By dashboardGrid = By.cssSelector(".oxd-grid-3");
    private final By searchMenuInput = By.cssSelector("input.oxd-input.oxd-input--active");

    private By menuItem(String name) {
       // return By.xpath("//span[normalize-space()='" + name + "']");
        return By.xpath("//span[normalize-space()='" + name + "']/ancestor::a");
    }

    @Step("Verify dashboard is loaded")
    public boolean isLoaded() {
        return isDisplayed(headerTitle) && getHeaderTitle().equalsIgnoreCase("Dashboard");
    }

    public String getHeaderTitle() {
        return getText(headerTitle, "Module header");
    }

    public boolean isUserDropdownDisplayed() {
        return isDisplayed(userDropdown);
    }

    public boolean isDashboardGridDisplayed() {
        return isDisplayed(dashboardGrid);
    }

    @Step("Navigate to module '{module}'")
    public void navigateTo(String module) {
        click(menuItem(module), module + " menu item");
    }
}
