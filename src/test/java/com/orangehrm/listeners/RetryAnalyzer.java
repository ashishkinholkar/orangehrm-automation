package com.orangehrm.listeners;

import com.orangehrm.config.ConfigManager;
import com.orangehrm.enums.ConfigProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Re-runs a failed test up to retrycount times (config-driven). Guards against
 * flaky UI failures (animation timing, transient network) so a genuine bug is
 * not hidden behind, and a flake does not fail the whole pipeline.
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    private static final Logger log = LogManager.getLogger(RetryAnalyzer.class);
    private int attempt = 0;
    private final int maxRetry = ConfigManager.getInt(ConfigProperties.RETRYCOUNT);

    @Override
    public boolean retry(ITestResult result) {
        if (attempt < maxRetry) {
            attempt++;
            log.warn("Retrying '{}' - attempt {}/{}", result.getName(), attempt, maxRetry);
            return true;
        }
        return false;
    }
}
