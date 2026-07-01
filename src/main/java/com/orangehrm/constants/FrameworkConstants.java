package com.orangehrm.constants;

import java.time.format.DateTimeFormatter;

/**
 * Immutable, environment-independent values. All file-system paths are
 * derived from user.dir so the framework is portable across machines / CI.
 */
public final class FrameworkConstants {

    private FrameworkConstants() { }

    private static final String PROJECT_ROOT = System.getProperty("user.dir");
    private static final String RESOURCES    = PROJECT_ROOT + "/src/test/resources";

    public static final String CONFIG_FILE_PATH   = RESOURCES + "/config/config.properties";
    public static final String TEST_DATA_PATH     = RESOURCES + "/testdata/";
    public static final String UPLOADS_PATH        = RESOURCES + "/uploads/";
    public static final String SCREENSHOT_PATH     = PROJECT_ROOT + "/target/screenshots/";
    public static final String ALLURE_RESULTS_PATH = PROJECT_ROOT + "/target/allure-results/";
    public static final String EXTENT_REPORT_PATH  = PROJECT_ROOT + "/target/cucumber-reports/";

    public static final int DEFAULT_EXPLICIT_WAIT  = 20;
    public static final int DEFAULT_IMPLICIT_WAIT   = 0;     // we rely on explicit waits only
    public static final int DEFAULT_PAGELOAD_WAIT   = 40;
    public static final int DEFAULT_POLLING_TIME    = 500;   // millis
    public static final int DEFAULT_RETRY_COUNT     = 1;

    public static final DateTimeFormatter TIMESTAMP =
            DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
}
