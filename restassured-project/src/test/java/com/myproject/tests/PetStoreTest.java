package com.myproject.tests;

import com.myproject.base.BaseClass;
import com.myproject.endpoints.Endpoints;
import com.myproject.utils.ConfigReader;
import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;   


public class PetStoreTest extends BaseClass {

@Test
public void testCRUDOperations() {

    // 🔹 Fetch data from config
    String status = ConfigReader.get("status");
    String initialName = ConfigReader.get("initial.name");
    String updatedName = ConfigReader.get("updated.name");
    String updatedStatus = ConfigReader.get("updated.status");
    String patchedName = ConfigReader.get("patched.name");
    String patchedStatus = ConfigReader.get("patched.status");

    int petId = new Random().nextInt(100000);

        request
        .when()
            .get("UserEndpoints.LOGIN") 
        .then()
            .statusCode(200)
            .log().all();
    


    // STEP 1: POST (Create Pet)
    
    Map<String, Object> createBody = new HashMap<>();
    createBody.put("id", petId);
    createBody.put("name", initialName);
    createBody.put("status", status);

    Response postResponse = postRequest(Endpoints.ADD, createBody);
    postResponse.then().log().all();
        Assert.assertEquals(postResponse.statusCode(), 200);

    // STEP 2: GET by ID
   
    Response getResponse = getWithPathParam(
            Endpoints.GET_BY_ID,
            petId
    );

    getResponse.then().log().all();
    String actualName = getResponse.jsonPath().getString("name");
    String expectedName = ConfigReader.getProperty("initial.name");
    Assert.assertEquals(actualName, expectedName);

  
    // STEP 3: PUT (Update full pet)
   
    Map<String, Object> updateBody = new HashMap<>();
    updateBody.put("id", petId);
    updateBody.put("name", updatedName);
    updateBody.put("status", updatedStatus);

    Response putResponse = putRequest(Endpoints.UPDATE, updateBody);
    putResponse.then().log().all();
    String UpdatedName = putResponse.jsonPath().getString("name");
    String expUpName = ConfigReader.getProperty("updated.name");
    Assert.assertEquals(UpdatedName, expUpName);

   
    //  STEP 4: POST (Update using form data)

    Map<String, Object> formData = new HashMap<>();
    formData.put("name", patchedName);
    formData.put("status", patchedStatus);

    Response postFormResponse = postWithFormData(
            Endpoints.UPDATE_WITH_FORM,
            petId,
            formData
    );

    postFormResponse.then().log().all();


    
    //  STEP 5: GET (Verify updated data)

     Response finalResponse = getWithPathParam(
             Endpoints.GET_BY_ID,
             petId
    );

     finalResponse.then().log().all();

    String finalName = finalResponse.jsonPath().getString("name");
     System.out.println("Final Name: " + finalName);

   
    // //  STEP 6: DELETE
     Response deleteResponse = deleteWithPathParam(
             Endpoints.DELETE,
            petId
     );

     deleteResponse.then().log().all();
}
}