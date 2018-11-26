package ch.hslu.wipro.micros.warehousemanagement.converter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.nio.charset.StandardCharsets;

public class GsonConverter<T> implements JsonConverter<T> {
    private final Gson gson;

    GsonConverter() {
        GsonBuilder builder = new GsonBuilder()
                .serializeNulls()
                .enableComplexMapKeySerialization();

        this.gson = builder.create();
    }

    @Override
    public String toJson(T type) {
        return gson.toJson(type);
    }

    @Override
    public T fromJson(String json, Class<T> type) {
        return gson.fromJson(json, type);
    }

    @Override
    public T fromJson(byte[] json, Class<T> type) {
        String jsonString = new String(json, StandardCharsets.UTF_8);
        return gson.fromJson(jsonString, type);
    }
}