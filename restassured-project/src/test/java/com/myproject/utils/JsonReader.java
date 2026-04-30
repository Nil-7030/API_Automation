package com.myproject.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class JsonReader {

    public static List<Map<String, Object>> getJsonData(String key) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            InputStream is = Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("testdata.json");

            Map<String, Object> data = mapper.readValue(is, Map.class);

            return (List<Map<String, Object>>) data.get(key);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to read JSON file");
        }
    }
}