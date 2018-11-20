package ch.hslu.wipro.micros.service;

/**
 * This interface provides to methods to store and get message information.
 */
public interface MessageDomain {

    /**
     * Stores the given command key.
     * @param commandKey
     */
    void addCommandKey(String commandKey);

    /**
     * Gets a corresponding command key for the given pattern.
     * @param pattern
     * @return
     */
    String getCommandKey(String pattern);

    /**
     * Gets the exchange name for this domain.
     * @return
     */
    String getExchange();

    /**
     * Prepares the information for this domain.
     */
    void prepareMessageDomain();
}
