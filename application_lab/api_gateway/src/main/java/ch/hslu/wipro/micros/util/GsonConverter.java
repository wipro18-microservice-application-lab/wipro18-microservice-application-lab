package ch.hslu.wipro.micros.util;

import com.google.gson.Gson;

/**
 * This class represents a json converter for OrderDTOs.
 */
public class GsonConverter implements JsonConverter {

    @Override
    public String toJsonString(Object data) {
        return new Gson().toJson(data);
    }
}
