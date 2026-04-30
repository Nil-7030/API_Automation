package com.myproject.tests;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import com.myproject.base.BaseClass;
import com.myproject.endpoints.Endpoints;
import com.myproject.utils.ConfigReader;

import io.restassured.response.Response;

public class UserLogin extends BaseClass {
    
    public String userLogin() {

        Response response = given()
                .baseUri(ConfigReader.get("BASE_URL"))
                .queryParam("username", ConfigReader.get("USERNAME"))
                .queryParam("password", ConfigReader.get("PASSWORD"))
                .when()
                .get(Endpoints.LOGIN)
                .then()
                .statusCode(200)
                .extract().response();

        String message = response.jsonPath().getString("message");

        return message.split(":")[1].trim();

    }
}