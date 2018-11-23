package ch.hslu.wipro.micros.business.converter;

public class JsonConverterFactory<T> {

    public JsonConverter<T> get() {
        return new GsonConverter<>();
    }
}