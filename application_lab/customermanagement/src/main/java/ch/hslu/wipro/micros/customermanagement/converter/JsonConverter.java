package ch.hslu.wipro.micros.customermanagement.converter;

public interface JsonConverter<T> {
    String toJson(T type);
    T fromJson(String json, Class<T> type);
    T fromJson(byte[] json, Class<T> type);
}