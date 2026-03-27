package com.weatherapp.util;

import java.io.FileWriter;
import java.io.IOException;

public class HistoryManager {

    public static void save(String city) {
        try {
            FileWriter writer = new FileWriter("history.txt", true);
            writer.write(city + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
