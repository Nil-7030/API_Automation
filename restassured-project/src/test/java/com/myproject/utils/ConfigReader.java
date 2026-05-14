package com.myproject.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {

    private static Properties prop =
            new Properties();

    static {

        try {

            FileInputStream fis =
                new FileInputStream(
                    "src/test/resources/config/qa.properties");

            prop.load(fis);

        } catch (Exception e) {

            System.out.println(
                "Properties file not found. "
                + "Using Jenkins environment variables.");
        }
    }

    public static String get(
            String key) {

        String envValue =
                System.getenv(key);

        if (envValue != null
                && !envValue.isEmpty()) {

            return envValue;
        }

        return prop.getProperty(key);
    }
}