package com.orangehrm.pages.modules;

import com.orangehrm.pages.base.BasePage;
import com.orangehrm.utils.FileUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

/**
 * Recruitment module: add candidate, upload resume, schedule interview, delete.
 * Covers business flow #5.
 */
public class RecruitmentPage extends BasePage {

    private final By addButton    = By.cssSelector("button.oxd-button--secondary");
    private final By firstName    = By.name("firstName");
    private final By lastName     = By.name("lastName");
    private final By emailInput   = By.xpath("//label[text()='Email']/../following-sibling::div//input");
    private final By resumeInput  = By.cssSelector("input[type='file']");
    private final By saveButton   = By.cssSelector("button[type='submit']");
    private final By successText  = By.xpath("//h6[text()='Candidates']");
    private final By candidateRows = By.cssSelector(".oxd-table-card");

    @Step("Add candidate {first} {last}")
    public void addCandidate(String first, String last, String email, String resumeFile) {
        click(addButton, "Add candidate");
        type(firstName, first, "First name");
        type(lastName, last, "Last name");
        type(emailInput, email, "Email");
        if (resumeFile != null && !resumeFile.isBlank()) {
            driver().findElement(resumeInput).sendKeys(FileUtil.absoluteUploadPath(resumeFile));
        }
        click(saveButton, "Save candidate");
    }

    public int candidateCount() {
        return findElements(candidateRows).size();
    }
}
