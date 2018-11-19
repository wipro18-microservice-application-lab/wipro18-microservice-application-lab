package ch.hslu.wipro.micros.service.convertion;

import com.google.gson.Gson;

public class GsonConverter implements JsonConverter {

    @Override
    public String toJson(Object object) {
        return new Gson().toJson(object);
    }
}