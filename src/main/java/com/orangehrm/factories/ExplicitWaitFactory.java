package com.orangehrm.factories;

import com.orangehrm.config.ConfigManager;
import com.orangehrm.enums.ConfigProperties;
import com.orangehrm.enums.WaitStrategy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Translates a {@link WaitStrategy} into the matching ExpectedCondition and
 * returns a ready-to-use WebElement. Centralising waits here means the page
 * objects stay declarative and we never sprinkle Thread.sleep around the code.
 */
public final class ExplicitWaitFactory {

    private ExplicitWaitFactory() { }

    public static WebElement performExplicitWait(WebDriver driver, WaitStrategy strategy, By locator) {
        WebDriverWait wait = new WebDriverWait(
                driver, Duration.ofSeconds(ConfigManager.getInt(ConfigProperties.EXPLICITWAIT)));

        switch (strategy) {
            case CLICKABLE:
                return wait.until(ExpectedConditions.elementToBeClickable(locator));
            case VISIBLE:
                return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            case PRESENCE:
                return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            case NONE:
            default:
                return driver.findElement(locator);
        }
    }
}
