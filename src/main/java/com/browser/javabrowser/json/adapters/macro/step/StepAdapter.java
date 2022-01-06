package com.browser.javabrowser.json.adapters.macro.step;

import com.browser.javabrowser.json.adapters.IAdapter;
import com.browser.javabrowser.macro.step.IStep;
import com.google.gson.*;
import java.lang.reflect.Type;

public abstract class StepAdapter implements JsonSerializer<IStep<StringBuilder>>, JsonDeserializer<IStep<StringBuilder>>, IAdapter {
    @Override
    public IStep<StringBuilder> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");

        type = this.getType(type);

        try {
            return context.deserialize(element, Class.forName("com.browser.javabrowser.macro.step." + type));
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new JsonParseException("Unknown element type: " + type);
        }
    }

    @Override
    public JsonElement serialize(IStep iStep, Type type, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(iStep.getClass().getSimpleName()));
        result.add("properties", context.serialize(iStep, iStep.getClass()));

        return result;
    }

    protected abstract String getType(String type);
}
