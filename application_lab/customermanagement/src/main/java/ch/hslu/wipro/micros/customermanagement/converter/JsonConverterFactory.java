package ch.hslu.wipro.micros.customermanagement.converter;

public class JsonConverterFactory<T> {

    public JsonConverter<T> get() {
        return new GsonConverter<>();
    }
}