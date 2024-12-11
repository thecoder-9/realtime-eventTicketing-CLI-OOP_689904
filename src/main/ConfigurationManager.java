package main;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class ConfigurationManager {
    // Save configurations to a JSON file
    public static void saveToJsonFile(String filename, List<Configuration> configurations) throws IOException {
        Gson gson = new Gson();
        try (Writer writer = new FileWriter(filename)) {
            gson.toJson(configurations, writer);
            System.out.println("Configurations saved to: " + filename);
        }
    }

    // Load configurations from a JSON file
    public static List<Configuration> loadFromJsonFile(String filename) throws IOException {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(filename)) {
            Type listType = new TypeToken<List<Configuration>>() {}.getType();
            return gson.fromJson(reader, listType);
        }
    }
}
