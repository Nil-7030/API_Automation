package com.myproject.base;

import static io.restassured.RestAssured.given;
import java.util.Map;
import org.testng.annotations.BeforeMethod;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import com.myproject.tests.UserLogin;
import com.myproject.utils.EnvManager;


public class BaseClass {

        protected RequestSpecification request;

        public static String token;

        protected String env;

        @BeforeMethod
        public void setup() {
                   
            EnvManager.loadEnv();

        System.out.println(
                "BASE_URL => "
                + EnvManager.get("BASE_URL"));    
                
                // Generate Token
                token = new UserLogin().userLogin();

                System.out.println( "Generated Token: " + token);
                       
                // Common Request Specification
                request = given()
                                 
                                .baseUri(EnvManager.get("BASE_URL"))
                                .header("Authorization", "Bearer " + token)
                                .header("Content-Type", "application/json")
                                .header("Accept", "application/json");

        }

        public Response getWithPathParam(String endpoint, Object pathParam) {
                return request
                                .pathParam("petId", pathParam)
                                .when()
                                .get(endpoint);
        }

        public Response postWithFormData(String endpoint, Object pathParam, Map<String, Object> formData) {
                return request
                                .pathParam("petId", pathParam)
                                .formParams(formData)
                                .when()
                                .post(endpoint);
        }

        public Response postRequest(String endpoint, Map<String, Object> body) {
                return request
                                .body(body)
                                .when()
                                .post(endpoint);
        }

        public Response putRequest(String endpoint, Map<String, Object> body) {

                return request
                                .body(body)
                                .when()
                                .put(endpoint);
        }

        public Response deleteWithPathParam(String endpoint, Object pathParam) {

                return request
                                .pathParam("petId", pathParam)
                                .when()
                                .delete(endpoint);
        }
}