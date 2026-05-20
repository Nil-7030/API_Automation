package com.myproject.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class EnvManager {

    private static final Map<String, String> envMap = new HashMap<>();

    public static void loadEnv() {

        try {

            String env = System.getProperty("env");

            if (env == null || env.isEmpty()) {

                env = "qa";
            }

            String filePath = System.getProperty("user.dir") + "/env/.env."  + env;
            System.out.println("Loading Environment File: .env." + env);
            BufferedReader br = new BufferedReader(new FileReader(filePath));

            String line;

            while ((line = br.readLine()) != null) {

                if (!line.startsWith("#")
                        && line.contains("=")) {

                    String[] keyValue = line.split("=", 2);

                    envMap.put(
                            keyValue[0].trim(),
                            keyValue[1].trim());
                }
            }

            br.close();

            System.out.println(
                    "Loaded Environment: "
                            + env);

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Failed to load env file");
        }
    }

    public static String get(String key) {

        return envMap.get(key);
    }
}