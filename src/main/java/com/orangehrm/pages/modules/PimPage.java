package com.orangehrm.pages.modules;

import com.orangehrm.pages.base.BasePage;
import com.orangehrm.utils.FileUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

/**
 * PIM module: add / search / edit / delete employees and profile-image upload.
 * Covers business flow #2 of the assignment.
 */
public class PimPage extends BasePage {

    private final By addButton        = By.cssSelector("button.oxd-button--secondary");
    private final By firstName        = By.name("firstName");
    private final By middleName       = By.name("middleName");
    private final By lastName         = By.name("lastName");
    private final By saveButton       = By.cssSelector("button[type='submit']");
    private final By empIdField       = By.xpath("//label[text()='Employee Id']/../following-sibling::div//input");
    private final By personalDetailsHeader = By.xpath("//h6[text()='Personal Details']");
    private final By toast            = By.cssSelector(".oxd-toast");

    // Search section
    private final By searchNameInput  = By.xpath("//label[text()='Employee Name']/../following-sibling::div//input");
    private final By searchSubmit     = By.cssSelector("button[type='submit']");
    private final By resultRows       = By.cssSelector(".oxd-table-card");
    private final By noRecords        = By.xpath("//span[text()='No Records Found']");

    // Profile image
    private final By profilePic       = By.cssSelector(".employee-image");
    private final By imageUploadInput = By.cssSelector("input[type='file']");

    @Step("Add employee {first} {last}")
    public String addEmployee(String first, String last) {
        click(addButton, "Add Employee");
        type(firstName, first, "First name");
        type(lastName, last, "Last name");
        click(saveButton, "Save");
        // Personal Details page confirms the create succeeded
        boolean created = isDisplayed(personalDetailsHeader);
        log.info("Employee created flag: {}", created);
        return getText(empIdField, "Employee Id");
    }

    @Step("Upload profile image '{fileName}'")
    public void uploadProfileImage(String fileName) {
        click(profilePic, "Profile image area");
        driver().findElement(imageUploadInput).sendKeys(FileUtil.absoluteUploadPath(fileName));
        click(saveButton, "Save image");
    }

    @Step("Search employee by name '{name}'")
    public int searchEmployee(String name) {
        type(searchNameInput, name, "Employee name search");
        click(searchSubmit, "Search");
        if (isDisplayed(noRecords)) {
            return 0;
        }
        return findElements(resultRows).size();
    }

    public boolean isToastDisplayed() {
        return isDisplayed(toast);
    }
}
