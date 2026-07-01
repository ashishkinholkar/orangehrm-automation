package com.orangehrm.enums;

/**
 * Every key that lives in config.properties is declared here.
 * Reading config through an enum prevents "magic string" typos and
 * lets the IDE auto-complete available keys.
 */
public enum ConfigProperties {
    BASEURL,
    BROWSER,
    HEADLESS,
    REMOTE,
    GRIDURL,
    IMPLICITWAIT,
    EXPLICITWAIT,
    PAGELOADTIMEOUT,
    POLLINGTIME,
    USERNAME,
    PASSWORD,
    RETRYCOUNT,
    APIBASEURL,
    DBURL,
    DBUSER,
    DBPASSWORD,
    SCREENSHOTONPASS
}
