package com.myproject.utils;

import org.testng.annotations.DataProvider;

import java.util.List;
import java.util.Map;

public class DataProviderSetup {

    @DataProvider(name = "csvUserData")
    public Object[] getCsvData() {

        List<Map<String, String>> users = CsvReader.getCsvData(
                "src/test/resources/users.csv");

        return users.toArray();
    }
}