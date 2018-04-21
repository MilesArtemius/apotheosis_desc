package classes.keeping;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.FileWriter;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class FileManager {
    //TODO: file creation;

    private static final String CONSTANTS = "/constants.apoth";

    public static HashMap<String, String> loadConstants(String home) {
        try {
            JsonParser JP = new JsonParser();
            JsonElement config;
            config = JP.parse(new JsonReader(new InputStreamReader(Files.newInputStream(Paths.get(home + CONSTANTS)))));
            HashMap<String, String> res = new HashMap<>();
            for (Map.Entry<String, JsonElement> entry: config.getAsJsonObject().entrySet()) {
                res.put(entry.getKey(), entry.getValue().getAsString());
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void saveConstants(String home, HashMap<String, String> database) {
        try {
            JsonObject constants = new JsonObject();
            for (Map.Entry<String, String> entry: database.entrySet()) {
                constants.addProperty(entry.getKey(), entry.getValue());
            }
            FileWriter file = new FileWriter(home + CONSTANTS);
            file.write(constants.toString());
            file.flush();
            file.close();
            System.out.println(constants.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
