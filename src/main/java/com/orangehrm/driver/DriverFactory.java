package com.orangehrm.driver;

import com.orangehrm.config.ConfigManager;
import com.orangehrm.enums.BrowserType;
import com.orangehrm.enums.ConfigProperties;
import com.orangehrm.exceptions.FrameworkException;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

/**
 * Creates a configured WebDriver and parks it in {@link DriverManager}.
 * Supports local + remote (Selenium Grid / Docker) execution and three browsers.
 * All timeouts/headless/grid come from config so nothing is hard-coded.
 */
public final class DriverFactory {

    private DriverFactory() { }

    public static void initDriver() {
        if (DriverManager.getDriver() != null) {
            return; // already initialised for this thread
        }

        BrowserType browser = BrowserType.fromString(ConfigManager.get(ConfigProperties.BROWSER));
        boolean headless    = ConfigManager.getBoolean(ConfigProperties.HEADLESS);
        boolean remote      = Boolean.parseBoolean(ConfigManager.get(ConfigProperties.REMOTE, "false"));

        WebDriver driver = remote
                ? createRemoteDriver(browser, headless)
                : createLocalDriver(browser, headless);

        configureTimeouts(driver);
        driver.manage().window().maximize();
        DriverManager.setDriver(driver);
    }

    private static WebDriver createLocalDriver(BrowserType browser, boolean headless) {
        switch (browser) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver(chromeOptions(headless));
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver(firefoxOptions(headless));
            case EDGE:
                WebDriverManager.edgedriver().setup();
                return new EdgeDriver(edgeOptions(headless));
            default:
                throw new FrameworkException("Unhandled browser: " + browser);
        }
    }

    private static WebDriver createRemoteDriver(BrowserType browser, boolean headless) {
        String gridUrl = ConfigManager.get(ConfigProperties.GRIDURL);
        MutableCapabilities caps;
        switch (browser) {
            case CHROME:  caps = chromeOptions(headless);  break;
            case FIREFOX: caps = firefoxOptions(headless); break;
            case EDGE:    caps = edgeOptions(headless);    break;
            default: throw new FrameworkException("Unhandled browser: " + browser);
        }
        try {
            return new RemoteWebDriver(new URL(gridUrl), caps);
        } catch (MalformedURLException e) {
            throw new FrameworkException("Invalid Grid URL: " + gridUrl, e);
        }
    }

    private static ChromeOptions chromeOptions(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage",
                "--disable-gpu", "--window-size=1920,1080",
                "--remote-allow-origins=*", "--disable-notifications");
        options.setAcceptInsecureCerts(true);
        return options;
    }

    private static FirefoxOptions firefoxOptions(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();
        if (headless) {
            options.addArguments("-headless");
        }
        options.addArguments("--width=1920", "--height=1080");
        options.setAcceptInsecureCerts(true);
        return options;
    }

    private static EdgeOptions edgeOptions(boolean headless) {
        EdgeOptions options = new EdgeOptions();
        if (headless) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--window-size=1920,1080");
        options.setAcceptInsecureCerts(true);
        return options;
    }

    private static void configureTimeouts(WebDriver driver) {
        driver.manage().timeouts().pageLoadTimeout(
                Duration.ofSeconds(ConfigManager.getInt(ConfigProperties.PAGELOADTIMEOUT)));
        driver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(ConfigManager.getInt(ConfigProperties.IMPLICITWAIT)));
    }

    public static void quitDriver() {
        if (DriverManager.getDriver() != null) {
            DriverManager.getDriver().quit();
            DriverManager.unload();
        }
    }
}
