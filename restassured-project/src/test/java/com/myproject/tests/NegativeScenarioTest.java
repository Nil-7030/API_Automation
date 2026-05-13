package com.myproject.tests;

import com.myproject.base.BaseClass;
import com.myproject.endpoints.Endpoints;
import com.myproject.utils.CsvReader;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

public class NegativeScenarioTest extends BaseClass {
    String UserName;

    List<Map<String, String>> users = CsvReader.getCsvData(
            "src/test/resources/users.csv");

    @Test()
    public void Postlist() {

       
        // POSITIVE POST SCENARIOS
        

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

            Response postResponse = given()
                    .spec(request)
                    .body(List.of(requestBody))
                    .when()
                    .post(Endpoints.POST_LIST);

            postResponse.then()
                    .statusCode(200);

            System.out.println(
                    "POST Success for: "
                            + csvUser.get("username"));
        }

        
        // NEGATIVE GET SCENARIOS
        

        for (int i = 3; i < users.size(); i++) {

            Map<String, String> csvUser = users.get(i);
            System.out.println("Negative Username: " + csvUser.get("username"));
            Response getResponse = given()
                    .spec(request)
                    .pathParam(
                            "username",
                            csvUser.get("username"))

                    .when()
                    .get(Endpoints.GET_BY_USERNAME);

            getResponse.then()
                    .statusCode(404)
                    .log().all()
                    .extract().response();
            System.out.println(
                    "GET Negative validated for: "
                            + csvUser.get("username"));
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
                .pathParam("username", users.get(1).get("username"))
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

        // Negative Delete Scenario
        Response DeleteInvalidresponse = request
                .header("Content-Type", "application/json")
                .pathParam("username", users.get(6).get("username"))
                .when()
                .delete(Endpoints.DELETEUSER);
        System.out.println("User is Not Found for Deletion");
        DeleteInvalidresponse.then()
                .assertThat()
                .statusCode(404);
    }

}
