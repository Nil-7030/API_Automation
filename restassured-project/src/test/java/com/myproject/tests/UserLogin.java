package com.myproject.tests;

import static io.restassured.RestAssured.given;
import com.myproject.endpoints.Endpoints;
import com.myproject.utils.EnvManager;
import io.restassured.response.Response;

public class UserLogin {

    public String userLogin() {

        Response response = given()
                .baseUri(EnvManager.get("BASE_URL"))
                .queryParam("username", EnvManager.get("USERNAME"))
                .queryParam("password", EnvManager.get("PASSWORD"))
                .when()
                .get(Endpoints.LOGIN)
                .then()
                .statusCode(200)
                .extract().response();

        String message = response.jsonPath().getString("message");

        return message.split(":")[1].trim();

    }
}