package com.browser.javabrowser.json.serializer;

import com.browser.javabrowser.json.adapters.IAdapter;
import com.browser.javabrowser.json.adapters.macro.step.ScriptStepAdapter;
import com.browser.javabrowser.macro.RobotMacro;
import com.browser.javabrowser.macro.ScriptMacro;
import com.browser.javabrowser.macro.step.IStep;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;

public class Serializer {
    public Gson getDefaultGson() { return new Gson(); }
    public Gson getCustomGson(Type type, IAdapter adapter) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(type, adapter);
        return gsonBuilder.create();
    }

    public <T> void serialize(T obj, String path, Gson gson) {
        try {
            JsonWriter writer = new JsonWriter(new FileWriter(path));
            gson.toJson(obj, obj.getClass(), writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> T deserialize(Type type, String path, Gson gson) {
        try {
            JsonReader reader = new JsonReader(new FileReader(path));
            return gson.fromJson(reader, type);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
