package com.myproject.utils;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class CsvReader {

    public static List<Map<String, String>> getCsvData(String filePath) {

        List<Map<String, String>> dataList = new ArrayList<>();

        try {
            CSVReader reader = new CSVReader(new FileReader(filePath));

            List<String[]> records = reader.readAll();

            String[] headers = records.get(0);

            for (int i = 1; i < records.size(); i++) {

                Map<String, String> data = new HashMap<>();

                for (int j = 0; j < headers.length; j++) {
                    data.put(headers[j], records.get(i)[j]);
                }

                dataList.add(data);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataList;
    }
}
