package com.orangehrm.pages.base;

import com.orangehrm.driver.DriverManager;
import com.orangehrm.enums.WaitStrategy;
import com.orangehrm.factories.ExplicitWaitFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Parent of every page object. Wraps Selenium primitives with explicit waits,
 * logging and Allure-friendly behaviour so concrete pages only express
 * business intent ("login", "searchEmployee") not raw click/sendKeys plumbing.
 */
public abstract class BasePage {

    protected static final Logger log = LogManager.getLogger(BasePage.class);

    protected WebDriver driver() {
        return DriverManager.getDriver();
    }

    /* ---------- core interactions ---------- */

    protected void click(By locator, String elementName) {
        ExplicitWaitFactory.performExplicitWait(driver(), WaitStrategy.CLICKABLE, locator).click();
        log.info("Clicked on: {}", elementName);
    }

    protected void type(By locator, String text, String elementName) {
        WebElement element =
                ExplicitWaitFactory.performExplicitWait(driver(), WaitStrategy.VISIBLE, locator);
        element.clear();
        element.sendKeys(text);
        log.info("Typed '{}' into: {}", text, elementName);
    }

    protected String getText(By locator, String elementName) {
        String text =
                ExplicitWaitFactory.performExplicitWait(driver(), WaitStrategy.VISIBLE, locator).getText();
        log.info("Read text '{}' from: {}", text, elementName);
        return text;
    }

    protected void selectByVisibleText(By locator, String visibleText, String elementName) {
        WebElement el = ExplicitWaitFactory.performExplicitWait(driver(), WaitStrategy.VISIBLE, locator);
        new Select(el).selectByVisibleText(visibleText);
        log.info("Selected '{}' from dropdown: {}", visibleText, elementName);
    }

    protected boolean isDisplayed(By locator) {
        try {
            return ExplicitWaitFactory.performExplicitWait(driver(), WaitStrategy.VISIBLE, locator)
                    .isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected List<WebElement> findElements(By locator) {
        return driver().findElements(locator);
    }

    /* ---------- helpers ---------- */

    protected void waitForElementToDisappear(By locator) {
        new WebDriverWait(driver(), Duration.ofSeconds(15))
                .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected void scrollIntoView(By locator) {
        WebElement element = driver().findElement(locator);
        ((JavascriptExecutor) driver())
                .executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    protected void jsClick(By locator) {
        WebElement element = driver().findElement(locator);
        ((JavascriptExecutor) driver()).executeScript("arguments[0].click();", element);
    }

    public String getCurrentUrl() {
        return driver().getCurrentUrl();
    }

    public String getPageTitle() {
        return driver().getTitle();
    }
}
