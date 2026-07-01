package com.orangehrm.config;

import com.orangehrm.constants.FrameworkConstants;
import com.orangehrm.exceptions.FrameworkException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Loads config.properties exactly once (eager singleton) and exposes the raw
 * Properties object to ConfigManager. Kept package-private surface small on
 * purpose - callers go through ConfigManager, never here directly.
 */
final class ConfigLoader {

    private static ConfigLoader instance;
    private final Properties properties;

    private ConfigLoader() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(FrameworkConstants.CONFIG_FILE_PATH)) {
            properties.load(fis);
        } catch (IOException e) {
            throw new FrameworkException(
                    "Unable to load config file: " + FrameworkConstants.CONFIG_FILE_PATH, e);
        }
    }

    static ConfigLoader getInstance() {
        if (instance == null) {
            synchronized (ConfigLoader.class) {
                if (instance == null) {
                    instance = new ConfigLoader();
                }
            }
        }
        return instance;
    }

    Properties getProperties() {
        return properties;
    }
}
