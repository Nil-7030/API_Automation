package com.myproject.utils;

import org.testng.Assert;

import io.restassured.response.Response;

public class ResponseValidator {

    public static void validateHeaders(Response response) {

        Assert.assertEquals(
                response.getHeader(
                        "Content-Type"),
                "application/json");

        Assert.assertEquals(
                response.getHeader(
                        "Connection"),
                "keep-alive");

        System.out.println(
                "Headers validated successfully");
    }

    public static void validateCookies(Response response) {

        String sessionCookie = response.getCookie(
                "JSESSIONID");

        if (sessionCookie != null) {

            System.out.println(
                    "Cookie validated: "
                            + sessionCookie);

        } else {

            System.out.println(
                    "No cookies found");
        }

    }
}
