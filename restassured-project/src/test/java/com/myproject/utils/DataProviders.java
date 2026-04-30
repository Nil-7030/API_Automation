package com.myproject.utils;

import org.testng.annotations.DataProvider;

public class DataProviders {

    @DataProvider(name = "userData")
    public static Object[][] data() {
        return new Object[][] {
                { 190, "user19", "New", "User", "New19@test.com", "New123", "9454435373", 19 },
                { 191, "user20", "Demo", "User", "demo20@test.com", "Demo123", "9363425693", 20 },
                { 192, "user21", "Test", "User", "Test21@test.com", "Test123", "9343453783", 21 },
                { 193, "user22", "Demo", "Test", "demo22@test.com", "dummy123", "8746467733", 22 },
                { 194, "user23", "Dummy", "New", "dummy23@new.com", "Demo123", "9999999999", 23 }
        };
    }
}
