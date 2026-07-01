package com.orangehrm.pages.modules;

import com.orangehrm.pages.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

/**
 * Performance module: create KPI, assign KPI and validate reports.
 * Covers business flow #6.
 */
public class PerformancePage extends BasePage {

    private final By addButton     = By.cssSelector("button.oxd-button--secondary");
    private final By kpiNameInput  = By.xpath("//label[text()='Key Performance Indicator']/../following-sibling::div//input");
    private final By jobTitleDd    = By.cssSelector(".oxd-select-text-input");
    private final By minRating     = By.xpath("//label[text()='Minimum Rating']/../following-sibling::div//input");
    private final By maxRating     = By.xpath("//label[text()='Maximum Rating']/../following-sibling::div//input");
    private final By saveButton    = By.cssSelector("button[type='submit']");
    private final By kpiRows       = By.cssSelector(".oxd-table-card");
    private final By dropdownOption(String v) { return By.xpath("//span[text()='" + v + "']"); }

    @Step("Create KPI '{kpiName}' for job title '{jobTitle}'")
    public void createKpi(String kpiName, String jobTitle, String min, String max) {
        click(addButton, "Add KPI");
        type(kpiNameInput, kpiName, "KPI name");
        click(jobTitleDd, "Job title dropdown");
        click(dropdownOption(jobTitle), jobTitle + " option");
        type(minRating, min, "Min rating");
        type(maxRating, max, "Max rating");
        click(saveButton, "Save KPI");
    }

    public int kpiCount() {
        return findElements(kpiRows).size();
    }
}
