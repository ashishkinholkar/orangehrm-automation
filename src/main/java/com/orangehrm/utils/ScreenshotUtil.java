package com.orangehrm.utils;

import com.orangehrm.constants.FrameworkConstants;
import com.orangehrm.driver.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

/**
 * Captures screenshots both as a byte[] (for Allure attachments, the preferred
 * path) and to disk (for the "execution screenshots" deliverable). Disk capture
 * never throws - a failed screenshot must not mask the real test failure.
 */
public final class ScreenshotUtil {

    private static final Logger log = LogManager.getLogger(ScreenshotUtil.class);

    private ScreenshotUtil() { }

    public static byte[] captureAsBytes() {
        return ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    public static String captureToFile(String scenarioName) {
        try {
            byte[] image = captureAsBytes();
            String safe = scenarioName.replaceAll("[^a-zA-Z0-9-_]", "_");
            String fileName = safe + "_" + LocalDateTime.now().format(FrameworkConstants.TIMESTAMP) + ".png";
            Path dir = Paths.get(FrameworkConstants.SCREENSHOT_PATH);
            Files.createDirectories(dir);
            Path target = dir.resolve(fileName);
            Files.write(target, image);
            log.info("Screenshot saved: {}", target);
            return target.toString();
        } catch (IOException e) {
            log.warn("Could not save screenshot for '{}': {}", scenarioName, e.getMessage());
            return null;
        }
    }
}
