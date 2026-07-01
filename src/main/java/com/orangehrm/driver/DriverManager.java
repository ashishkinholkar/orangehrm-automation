package com.orangehrm.driver;

import org.openqa.selenium.WebDriver;

/**
 * ThreadLocal store for WebDriver. This is the cornerstone of safe parallel
 * execution: each TestNG / Cucumber thread sees its own driver, so scenarios
 * never collide on a shared browser instance.
 */
public final class DriverManager {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverManager() { }

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    static void setDriver(WebDriver driver) {
        DRIVER.set(driver);
    }

    static void unload() {
        DRIVER.remove();
    }
}
