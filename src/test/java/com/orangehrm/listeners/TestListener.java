package com.orangehrm.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Suite-level logging hook. Logs start/finish/skip and the pass-fail tally so
 * console + CI output tells the story even before Allure is generated.
 */
public class TestListener implements ITestListener {

    private static final Logger log = LogManager.getLogger(TestListener.class);

    @Override public void onStart(ITestContext c)  { log.info(">>> SUITE START: {}", c.getName()); }
    @Override public void onTestStart(ITestResult r){ log.info("--> {}", r.getName()); }
    @Override public void onTestSuccess(ITestResult r){ log.info("PASS: {}", r.getName()); }
    @Override public void onTestFailure(ITestResult r){ log.error("FAIL: {}", r.getName(), r.getThrowable()); }
    @Override public void onTestSkipped(ITestResult r){ log.warn("SKIP: {}", r.getName()); }

    @Override public void onFinish(ITestContext c) {
        log.info(">>> SUITE END: {} | Passed={} Failed={} Skipped={}",
                c.getName(),
                c.getPassedTests().size(),
                c.getFailedTests().size(),
                c.getSkippedTests().size());
    }
}
