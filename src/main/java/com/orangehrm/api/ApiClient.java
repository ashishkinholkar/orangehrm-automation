package com.orangehrm.api;

import com.orangehrm.config.ConfigManager;
import com.orangehrm.enums.ConfigProperties;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

/**
 * RestAssured wrapper for the (bonus) API-validation layer. Useful to seed/verify
 * data faster than the UI - e.g. confirm an employee created via UI also exists
 * through the REST endpoint, or pre-create data so a UI test starts from a known
 * state.
 */
public final class ApiClient {

    private ApiClient() { }

    private static RequestSpecification base() {
        RestAssured.baseURI = ConfigManager.get(ConfigProperties.APIBASEURL, "https://opensource-demo.orangehrmlive.com");
        return given().relaxedHTTPSValidation().header("Content-Type", "application/json");
    }

    public static Response get(String endpoint) {
        return base().when().get(endpoint).then().extract().response();
    }

    public static Response post(String endpoint, Object body) {
        return base().body(body).when().post(endpoint).then().extract().response();
    }

    public static Response delete(String endpoint) {
        return base().when().delete(endpoint).then().extract().response();
    }
}
