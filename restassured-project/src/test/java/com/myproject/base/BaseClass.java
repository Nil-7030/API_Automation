package com.myproject.base;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

import java.util.Map;

import org.testng.annotations.BeforeMethod;


import com.myproject.tests.UserLogin;
import com.myproject.utils.ConfigReader;

public class BaseClass {

    protected RequestSpecification request;
    public static String token;

    @BeforeMethod
    public void setup() {

        request = given()
                .baseUri(ConfigReader.get("BASE_URL"))
                .header("Content-Type", "application/json");

        token = new UserLogin().userLogin();
        System.out.println("Generated Token: " + token);

        request = given()
                .baseUri(ConfigReader.get("BASE_URL"))

                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json");
    }

     public Response getWithPathParam(String endpoint, Object pathParam) {
        return given()
                .header("Content-Type", "application/json")
                .pathParam("petId", pathParam)
                .when()
                .get(endpoint);
    }

    public Response postWithFormData(String endpoint,
            Object pathParam,
            Map<String, Object> formData) {

        return given()
                .header("Content-Type", "application/json")
                .pathParam("petId", pathParam)
                .formParams(formData)
                .when()
                .post(endpoint);
    }

    public Response postRequest(String endpoint, Map<String, Object> body) {
        return given()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post(endpoint);
    }

    public Response putRequest(String endpoint, Map<String, Object> body) {
        return given()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .put(endpoint);
    }

    public Response deleteWithPathParam(String endpoint, Object pathParam) {
        return given()
                .header("Content-Type", "application/json")
                .pathParam("petId", pathParam)
                .when()
                .delete(endpoint);
    } 

}