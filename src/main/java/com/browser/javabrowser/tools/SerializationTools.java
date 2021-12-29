package com.browser.javabrowser.tools;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import javafx.scene.control.Alert;;

public class SerializationTools {

    public static <T> void serialize(String filePath, T source)
    {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type dataType = new TypeToken<T>() {}.getType();
            JsonWriter writer = new JsonWriter(new FileWriter(filePath));
            gson.toJson(source, dataType, writer);
            writer.close();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error while saving JSON to file " + filePath);
            alert.show();
        }
    }

    public static <T> void serializeList(String filePath, List<T> sourceList) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type datasetListType = new TypeToken<Collection<T>>() {}.getType();
            JsonWriter writer = new JsonWriter(new FileWriter(filePath));
            gson.toJson(sourceList, datasetListType, writer);
            writer.close();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error while saving JSON to file " + filePath);
            alert.show();
        }
    }

    public static <T> List<T> deserializeList(String filePath, Class<T> type) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type datasetListType = new TypeToken<Collection<T>>() {}.getType();
            JsonReader reader = new JsonReader(new FileReader(filePath));
            List<Map<String, ?>> data = gson.fromJson(reader, datasetListType);
            reader.close();
            if(data == null) return null;
            return data.stream().map(object -> SerializationTools.mapToObject(object, type)).filter(Objects::nonNull).toList();
        } catch (IOException e) {
            return null;
        }
    }

    public static <T> T deserialize(String filePath, Class<T> type) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type datasetType = new TypeToken<T>() {}.getType();
            JsonReader reader = new JsonReader(new FileReader(filePath));
            Map<String, ?> data = gson.fromJson(reader, datasetType);
            reader.close();
            if(data == null) return null;
            return SerializationTools.mapToObject(data, type);
        } catch (IOException e) {
            return null;
        }
    }

    private static <T> T mapToObject(Map<String, ?> object, Class<T> type) {
        if(object == null) return null;
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return gson.fromJson(json, type);
    }
}