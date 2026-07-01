package com.orangehrm.exceptions;

/**
 * One unchecked exception type for the whole framework. Wrapping low-level
 * checked exceptions (IO, config, driver) into this keeps step definitions
 * and page objects clean and gives consistent failure messages in reports.
 */
public class FrameworkException extends RuntimeException {

    public FrameworkException(String message) {
        super(message);
    }

    public FrameworkException(String message, Throwable cause) {
        super(message, cause);
    }
}
