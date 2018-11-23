package ch.hslu.wipro.micros.util;

/**
 * This class represents a factory for JsonConverters.
 */
public class JsonConverterFactory {

    private JsonConverterFactory() {

    }

    public static JsonConverter createConverter() {
        return new GsonConverter();
    }
}
