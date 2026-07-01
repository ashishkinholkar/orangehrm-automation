package com.orangehrm.pages.modules;

import com.orangehrm.pages.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

/**
 * Admin module: create a user, assign a role / status, search and disable.
 * Covers business flow #4.
 */
public class AdminPage extends BasePage {

    private final By addButton    = By.cssSelector("button.oxd-button--secondary");
    private final By userRoleDropdown = By.xpath("(//div[@class='oxd-select-text-input'])[1]");
    private final By statusDropdown   = By.xpath("(//div[@class='oxd-select-text-input'])[2]");
    private final By employeeNameInput = By.xpath("//label[text()='Employee Name']/../following-sibling::div//input");
    private final By usernameInput = By.xpath("//label[text()='Username']/../following-sibling::div//input");
    private final By passwordInput = By.cssSelector("input[type='password']");
    private final By saveButton    = By.cssSelector("button[type='submit']");
    private final By searchUsername = By.xpath("//label[text()='Username']/../following-sibling::div//input");
    private final By resultRows    = By.cssSelector(".oxd-table-card");
    private final By dropdownOption(String value) {
        return By.xpath("//span[text()='" + value + "']");
    }

    @Step("Create user '{username}' with role '{role}'")
    public void createUser(String username, String role, String status, String employee, String password) {
        click(addButton, "Add User");
        selectDropdown(userRoleDropdown, role);
        type(employeeNameInput, employee, "Employee name");
        // pick the first auto-complete suggestion
        click(By.cssSelector(".oxd-autocomplete-option"), "Employee suggestion");
        selectDropdown(statusDropdown, status);
        type(usernameInput, username, "Username");
        // OrangeHRM shows two password fields when creating a user
        var pwdFields = findElements(passwordInput);
        pwdFields.get(0).sendKeys(password);
        pwdFields.get(1).sendKeys(password);
        click(saveButton, "Save user");
    }

    @Step("Search user '{username}'")
    public int searchUser(String username) {
        type(searchUsername, username, "Username search");
        click(saveButton, "Search");
        return findElements(resultRows).size();
    }

    private void selectDropdown(By dropdown, String value) {
        click(dropdown, "Dropdown");
        click(dropdownOption(value), value + " option");
    }
}
