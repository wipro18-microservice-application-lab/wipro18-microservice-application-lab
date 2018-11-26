package ch.hslu.wipro.micros.warehousemanagement.converter;

public class JsonConverterFactory<T> {

    public JsonConverter<T> get() {
        return new GsonConverter<>();
    }
}