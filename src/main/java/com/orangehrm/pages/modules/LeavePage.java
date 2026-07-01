package com.orangehrm.pages.modules;

import com.orangehrm.pages.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

/**
 * Leave module: apply leave, search leave and approve/reject.
 * Covers business flow #3.
 */
public class LeavePage extends BasePage {

    private final By leaveTypeDropdown = By.cssSelector(".oxd-select-text-input");
    private final By commentArea       = By.cssSelector("textarea");
    private final By applyButton       = By.cssSelector("button[type='submit']");
    private final By fromDate          = By.xpath("(//input[@placeholder='yyyy-dd-mm'])[1]");
    private final By toDate            = By.xpath("(//input[@placeholder='yyyy-dd-mm'])[2]");
    private final By recordRows        = By.cssSelector(".oxd-table-card");
    private final By dropdownOption(String v) { return By.xpath("//span[text()='" + v + "']"); }
    private final By actionButton(String action) {
        return By.xpath("//button[normalize-space()='" + action + "']");
    }

    @Step("Apply leave of type '{leaveType}'")
    public void applyLeave(String leaveType, String from, String to, String comment) {
        click(leaveTypeDropdown, "Leave type");
        click(dropdownOption(leaveType), leaveType + " option");
        type(fromDate, from, "From date");
        type(toDate, to, "To date");
        if (comment != null && !comment.isBlank()) {
            type(commentArea, comment, "Comment");
        }
        click(applyButton, "Apply");
    }

    @Step("Search leave records")
    public int searchLeave() {
        click(applyButton, "Search");
        return findElements(recordRows).size();
    }

    @Step("Take action '{action}' on a leave request")
    public void actOnLeave(String action) {
        click(actionButton(action), action + " button");
    }
}
