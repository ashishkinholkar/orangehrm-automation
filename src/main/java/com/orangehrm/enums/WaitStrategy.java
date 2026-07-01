package com.orangehrm.enums;

/**
 * Drives the ExplicitWaitFactory so page objects can declare the *intent*
 * of a wait (CLICKABLE, VISIBLE ...) rather than wiring ExpectedConditions
 * by hand each time.
 */
public enum WaitStrategy {
    CLICKABLE,
    PRESENCE,
    VISIBLE,
    NONE
}
