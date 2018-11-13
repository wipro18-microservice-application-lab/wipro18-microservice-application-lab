package ch.hslu.wipro.micros.util;

/**
 * This interface provides a method to convert an object to a json string.
 * @param <T> The type of the data
 */
public interface JsonConverter<T> {

    /**
     * Converts the given data to a json string.
     * @param data
     * @return json string
     */
    String toJsonString(T data);
}
