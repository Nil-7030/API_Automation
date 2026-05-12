package com.myproject.tests;

import com.myproject.base.BaseClass;
import com.myproject.endpoints.Endpoints;
import com.myproject.utils.DataProviders;
import com.myproject.utils.JsonReader;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

public class UserOperation extends BaseClass {
    String UserName;

    @Test()
    public void Postlist() {

        List<Map<String, Object>> users = JsonReader.getJsonData("createUsers");
        Response response = request
                .given()
                .body(users)
                .when()
                .post(Endpoints.POST_LIST)
                .then()
                .log().all()
                .extract().response();
        String userId = response.jsonPath().getString("id");
        System.out.println("UserID : " + userId);
        response.then()
                .assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("userResponseSchema.json"));

        Response getResponse = request
                .given()
                .pathParam("username", users.get(0).get("username"))
                .when()
                .get(Endpoints.GET_BY_USERNAME)
                .then()
                .log().all()
                .extract().response();
        UserName = getResponse.jsonPath().getString("username");
        System.out.println("USERNAME : " + UserName);
        getResponse.then()
                .assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("userSchema.json"));

        // Assert.assertEquals(getResponse.getStatusCode(), 200);
        // Assert.assertEquals(getResponse.jsonPath().getString("username"),
        // users.get(0).get("username"));
        // Assert.assertEquals(getResponse.jsonPath().getString("email"),
        // users.get(0).get("email"));
    }

    @Test
    public void updateUser() {

        // List<Map<String, Object>> users = JsonReader.getJsonData("updateUser");
        // Map<String, Object> user = users.get(0);
        String updatedUsername = "Random" + System.currentTimeMillis();
        System.out.println("Updated USERNAME : " + updatedUsername);
        Map<String, Object> updatedUser = new HashMap<>();
        updatedUser.put("username", updatedUsername);

        Response response = request
                .given()
                .pathParam("username", UserName)
                .body(updatedUser)
                .when()
                .put(Endpoints.UPDATE)
                .then()
                .log().all()
                .extract().response();
        System.out.println("User updated successfully");
        response.then()
                .assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("userResponseSchema.json"));
        // String Newusername= response.jsonPath().getString(updatedUsername);

        // Verify the updated user by retrieving it
        // System.out.println(updatedUsername,"updatedUsername");
        Response getResponse = request
                .given()
                .pathParam("username", updatedUsername)
                .when()
                .get(Endpoints.GET_BY_USERNAME)
                .then()
                .log().all()
                .extract().response();

        getResponse.then()
                .assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("userSchema.json"));

        // Assert.assertEquals(getResponse.getStatusCode(), 200);
        // Assert.assertEquals(getResponse.jsonPath().getString("username"),
        // updatedUsername);

        Response Deleteresponse = request
                .given()
                .header("Content-Type", "application/json")
                .pathParam("username", updatedUsername)
                .when()
                .delete(Endpoints.DELETEUSER);
        System.out.println("User is Succesfully Deleted");
        Deleteresponse.then()
                .assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("userResponseSchema.json"));

        Response userResponse = request
                .given()
                .pathParam("username", updatedUsername)
                .when()
                .get(Endpoints.GET_BY_USERNAME)
                .then()
                .log().all()
                .extract().response();

        Assert.assertEquals(userResponse.jsonPath().getString("message"), "User not found");
        System.out.println("User Not Found");
    }

}