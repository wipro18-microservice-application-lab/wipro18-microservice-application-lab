package ch.hslu.wipro.micros.util;

/**
 * This interface provides a method to convert an object to a json string.
 */
public interface JsonConverter {

    /**
     * Converts the given data to a json string.
     * @param data
     * @return json string
     */
    String toJsonString(Object data);
}
