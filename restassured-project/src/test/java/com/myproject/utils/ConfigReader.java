package com.myproject.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {

    private static Properties prop =
            new Properties();

    static {

        try {

            String env =
                    System.getProperty("env");

            if (env == null || env.isEmpty()) {

                env = "prod";
            }

            String filePath =
                    "src/test/resources/config/"
                    + env + ".properties";

            FileInputStream fis =
                    new FileInputStream(filePath);

            prop.load(fis);

            System.out.println(
                    "Loaded Environment: " + env);

        } catch (Exception e) {

            System.out.println(
                "Properties file not found. "
                + "Using Jenkins environment variables.");
        }
    }

    public static String get(String key) {

        String envValue =
                System.getenv(key);

        if (envValue != null
                && !envValue.isEmpty()) {

            return envValue;
        }

        return prop.getProperty(key);
    }
}