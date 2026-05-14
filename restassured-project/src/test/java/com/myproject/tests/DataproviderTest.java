
package com.myproject.tests;

import com.myproject.base.BaseClass;
import com.myproject.endpoints.Endpoints;
import com.myproject.utils.DataProviders;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DataproviderTest extends BaseClass {
        String UserName;

        @Test(dataProvider = "userData", dataProviderClass = DataProviders.class)
        public void Postlist(Map<String, Object> user) {

                List<Map<String, Object>> userList = new ArrayList<>();
                userList.add(user);
                Response response = given()
                                .spec(request)
                                .body(userList)
                                .when()
                                .post(Endpoints.POST_LIST)
                                .then()
                                .log().all()
                                .extract().response();

                response.then()
                                .assertThat()
                                .statusCode(200)
                                .body(matchesJsonSchemaInClasspath("userResponseSchema.json"));

                Response getResponse = given()
                                .spec(request)
                                .pathParam("username", user.get("username"))
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

                Response Deleteresponse = given()
                                .spec(request)
                                .header("Content-Type", "application/json")
                                .pathParam("username", updatedUsername)
                                .when()
                                .delete(Endpoints.DELETEUSER);
                System.out.println("User is Succesfully Deleted");
                Deleteresponse.then()
                                .assertThat()
                                .statusCode(200)
                                .body(matchesJsonSchemaInClasspath("userResponseSchema.json"));

                Response userResponse = given()
                                .spec(request)
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
