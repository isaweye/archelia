package uk.mqchinee.archelia.impl;

import java.util.Map;
import java.util.function.Consumer;

/**
 * A generic registry interface for mapping keys to values.
 *
 * @param <T>  The type of the keys in the registry.
 * @param <T2> The type of the values in the registry.
 */
public interface Registry<T, T2> {

    /**
     * Registers a key-value pair in the registry.
     *
     * @param key   The key to be registered.
     * @param value The value to be associated with the key.
     */
    void register(T key, T2 value);

    /**
     * Retrieves the value associated with a given key from the registry.
     *
     * @param key The key for which to retrieve the value.
     * @return The value associated with the given key, or null if not found.
     */
    T2 get(T key);

    /**
     * Checks if the registry contains a mapping for the given key.
     *
     * @param key The key to check for existence in the registry.
     * @return True if a mapping exists for the given key, false otherwise.
     */
    boolean contains(T key);

    /**
     * Replaces the value associated with a given key in the registry.
     *
     * @param key   The key for which to replace the value.
     * @param value The new value to associate with the key.
     */
    void replace(T key, T2 value);

    /**
     * Removes the mapping for a given key from the registry.
     *
     * @param key The key for which to remove the mapping.
     */
    void remove(T key);

    /**
     * Applies the given consumer function to each entry in the registry.
     *
     * @param entryConsumer The consumer function to apply to each entry.
     */
    void forEach(Consumer<Map.Entry<T, T2>> entryConsumer);
}
