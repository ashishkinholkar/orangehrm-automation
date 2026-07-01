package com.orangehrm.pages.modules;

import com.orangehrm.config.ConfigManager;
import com.orangehrm.enums.ConfigProperties;
import com.orangehrm.pages.base.BasePage;
import org.openqa.selenium.By;

import io.qameta.allure.Step;

/**
 * OrangeHRM login screen. Locators verified against the React demo build
 * (opensource-demo.orangehrmlive.com). Methods read like the business action.
 */
public class LoginPage extends BasePage {

    private final By usernameInput = By.name("username");
    private final By passwordInput = By.name("password");
    private final By loginButton   = By.cssSelector("button[type='submit']");
    private final By errorAlert    = By.cssSelector(".oxd-alert-content-text");
    private final By requiredError = By.cssSelector(".oxd-input-field-error-message");
    private final By logo          = By.cssSelector(".orangehrm-login-branding img");

    @Step("Open OrangeHRM login page")
    public LoginPage open() {
        driver().get(ConfigManager.get(ConfigProperties.BASEURL));
        return this;
    }

    @Step("Login with username '{username}'")
    public void login(String username, String password) {
        type(usernameInput, username, "Username");
        type(passwordInput, password, "Password");
        click(loginButton, "Login button");
    }

    @Step("Login with configured admin credentials")
    public void loginAsAdmin() {
        login(ConfigManager.get(ConfigProperties.USERNAME),
              ConfigManager.get(ConfigProperties.PASSWORD));
    }

    public boolean isLogoDisplayed()       { return isDisplayed(logo); }
    public String  getInvalidLoginError()  { return getText(errorAlert, "Invalid credentials alert"); }
    public String  getRequiredFieldError() { return getText(requiredError, "Required field error"); }
}
