package com.myproject.utils;

import static io.restassured.RestAssured.put;

import java.util.HashMap;

import org.testng.annotations.DataProvider;

public class DataProviders {
// ✅ Correct — each map in its own row
@DataProvider(name = "userData")
public static Object[][] userData() {
    return new Object[][] {
        { new HashMap<String, Object>() {{ 
            put("id", 190); 
            put("username", "user19");
            put("firstName", "Dew");
            put("lastName", "Nser");
            put("email", "Dew19@test.com");
            put("password", "Dew123");
            put("phone", "944365373");
            put("userStatus", 19);
        }}},
        { new HashMap<String, Object>() {{ 
            put("id", 191); 
            put("username", "user20");
            put("firstName", "Net");
            put("lastName", "Tester");
            put("email", "Net20@test.com");
            put("password", "Net123");
            put("phone", "942435374");
            put("userStatus", 20);
        }}},
        { new HashMap<String, Object>() {{ 
            put("id", 192); 
            put("username", "user21");
            put("firstName", "dummy");
            put("lastName", "Tester");
            put("email", "dummy21@test.com");
            put("password", "dummy123");
            put("phone", "9426835375");
            put("userStatus", 21);
        }}}
    };
}
}