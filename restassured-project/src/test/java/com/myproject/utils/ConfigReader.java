package com.myproject.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

     private static Properties properties = new Properties();

    static {
        try {
            // Try to load from project root first
            FileInputStream file = new FileInputStream(".env");
            properties.load(file);
            file.close();
        } catch (IOException e) {
            // If not found in project root, try to load as resource
            try {
                properties.load(ConfigReader.class.getClassLoader().getResourceAsStream(".env"));
            } catch (Exception ex) {
                throw new RuntimeException("Failed to load .env file from project root or resources");
            }
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    public static String getProperty(String key) {
        return get(key);
    }
}

