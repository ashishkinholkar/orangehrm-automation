package com.orangehrm.config;

import com.orangehrm.enums.ConfigProperties;
import com.orangehrm.exceptions.FrameworkException;

/**
 * Single entry point for configuration. Resolution order:
 *   1. JVM system property  (-Dbrowser=firefox)   -> highest priority, lets CI override
 *   2. config.properties value
 * This is what allows the same build to run locally headed and on Jenkins headless
 * without touching code.
 */
public final class ConfigManager {

    private ConfigManager() { }

    public static String get(ConfigProperties key) {
        String keyName = key.name().toLowerCase();

        // System property wins so CI / mvn -D flags override the file.
        String sysValue = System.getProperty(keyName);
        if (sysValue != null && !sysValue.isBlank()) {
            return sysValue.trim();
        }

        String value = ConfigLoader.getInstance().getProperties().getProperty(keyName);
        if (value == null) {
            throw new FrameworkException("Property not found in config: " + keyName);
        }
        return value.trim();
    }

    public static String get(ConfigProperties key, String defaultValue) {
        try {
            return get(key);
        } catch (FrameworkException e) {
            return defaultValue;
        }
    }

    public static int getInt(ConfigProperties key) {
        return Integer.parseInt(get(key));
    }

    public static boolean getBoolean(ConfigProperties key) {
        return Boolean.parseBoolean(get(key));
    }
}
