package uk.mqchinee.archelia.impl;

import java.util.Map;
import java.util.function.Consumer;

/**
 * A generic registry interface for mapping keys to values.
 *
 * @param <K> The type of the keys in the registry.
 * @param <V> The type of the values in the registry.
 */
public interface Registry<K, V> {

    /**
     * Registers a key-value pair in the registry.
     *
     * @param key   The key to be registered.
     * @param value The value to be associated with the key.
     */
    void register(K key, V value);

    /**
     * Retrieves the value associated with a given key from the registry.
     *
     * @param key The key for which to retrieve the value.
     * @return The value associated with the given key, or null if not found.
     */
    V get(K key);

    /**
     * Checks if the registry contains a mapping for the given key.
     *
     * @param key The key to check for existence in the registry.
     * @return True if a mapping exists for the given key, false otherwise.
     */
    boolean contains(K key);

    /**
     * Replaces the value associated with a given key in the registry.
     *
     * @param key   The key for which to replace the value.
     * @param value The new value to associate with the key.
     */
    void replace(K key, V value);

    /**
     * Removes the mapping for a given key from the registry.
     *
     * @param key The key for which to remove the mapping.
     */
    void remove(K key);

    /**
     * Applies the given consumer function to each entry in the registry.
     *
     * @param entryConsumer The consumer function to apply to each entry.
     */
    void forEach(Consumer<Map.Entry<K, V>> entryConsumer);
}
