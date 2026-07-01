package com.orangehrm.utils;

import net.datafaker.Faker;

/**
 * Generates unique-ish test data at runtime so reruns don't clash on
 * already-existing employees/users. Wrapping Faker keeps a single locale and
 * gives us domain-named helpers.
 */
public final class FakerUtil {

    private static final Faker FAKER = new Faker();

    private FakerUtil() { }

    public static String firstName()  { return FAKER.name().firstName(); }
    public static String lastName()   { return FAKER.name().lastName(); }
    public static String middleName() { return FAKER.name().firstName(); }

    /** Username/login safe value with a numeric suffix to stay unique. */
    public static String username() {
        return "auto_" + FAKER.internet().username().replaceAll("[^a-zA-Z0-9]", "")
                + FAKER.number().numberBetween(100, 999);
    }

    public static String strongPassword() {
        return "Auto@" + FAKER.number().numberBetween(1000, 9999);
    }

    public static String jobTitle()  { return FAKER.job().position(); }
    public static String email()     { return FAKER.internet().emailAddress(); }
}
