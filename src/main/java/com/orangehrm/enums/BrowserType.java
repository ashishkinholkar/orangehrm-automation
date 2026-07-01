package com.orangehrm.enums;

/**
 * Supported browsers. Keeping this an enum (vs raw strings) gives us
 * compile-time safety and a single place to add a new browser.
 */
public enum BrowserType {
    CHROME,
    FIREFOX,
    EDGE;

    public static BrowserType fromString(String value) {
        for (BrowserType b : values()) {
            if (b.name().equalsIgnoreCase(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unsupported browser: " + value);
    }
}
