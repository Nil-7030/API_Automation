package com.myproject.tests;

import com.myproject.base.BaseClass;
import com.myproject.endpoints.Endpoints;
import com.myproject.utils.CsvReader;
import com.myproject.utils.DataProviderSetup;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DataDrivenTest extends BaseClass {
    String UserName;
    List<Map<String, String>> users = CsvReader.getCsvData(
            "src/test/resources/users.csv");

    @Test
    public void Postlist() {
        // int expectedStatus = Integer.parseInt(user.get("expectedStatus"));

        for (int i = 0; i < 3; i++) {

            Map<String, String> csvUser = users.get(i);

            Map<String, Object> requestBody = new HashMap<>();

            requestBody.put(
                    "id",
                    Integer.parseInt(
                            csvUser.get("id")));

            requestBody.put(
                    "username",
                    csvUser.get("username"));

            requestBody.put(
                    "firstName",
                    csvUser.get("firstName"));

            requestBody.put(
                    "lastName",
                    csvUser.get("lastName"));

            requestBody.put(
                    "email",
                    csvUser.get("email"));

            requestBody.put(
                    "password",
                    csvUser.get("password"));

            requestBody.put(
                    "phone",
                    csvUser.get("phone"));

            requestBody.put(
                    "userStatus",
                    Integer.parseInt(
                            csvUser.get("userStatus")));

            Response response = given()
                    .spec(request)
                    .body(List.of(requestBody))
                    .when()
                    .post(Endpoints.POST_LIST)
                    .then()
                    .statusCode(200)
                    .log().all()
                    .extract().response();

            response.then()
                    .assertThat()
                    .statusCode(200)
                    .body(matchesJsonSchemaInClasspath("userResponseSchema.json"));

            Response getResponse = given()
                    .spec(request)
                    .pathParam("username", users.get(1).get("username"))
                    .when()
                    .get(Endpoints.GET_BY_USERNAME)
                    .then()
                    .log().all()
                    .extract().response();
            String actualUsername = getResponse.jsonPath().getString("username");

            UserName = actualUsername;
            // int expectednewStatus = Integer.parseInt(user.get("expectedStatus"));
            System.out.println("ACTUAL USERNAME: " + actualUsername);

          //  Assert.assertEquals(
                    //actualUsername,
                   // users.get(1).get("username"));

            getResponse.then()
                    .assertThat()
                    // .statusCode(404)
                    .statusCode(200)
                    .body(matchesJsonSchemaInClasspath("userSchema.json"));
        }
    }

    @Test
    public void updateUser() {

        String updatedUsername = "Random" + System.currentTimeMillis();
        System.out.println("Updated USERNAME : " + updatedUsername);
        Map<String, Object> updatedUser = new HashMap<>();
        updatedUser.put("username", updatedUsername);

        Response response = given()
                .spec(request)
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

        Response getResponse = given()
                .spec(request)
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

        Response Deleteresponse = request
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
