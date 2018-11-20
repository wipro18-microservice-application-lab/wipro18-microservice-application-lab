package ch.hslu.wipro.micros.util;

public class JsonConverterFactory {

    private JsonConverterFactory() {

    }

    public static JsonConverter createConverter() {
        return new GsonConverter();
    }
}
