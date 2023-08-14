package uk.mqchinee.archelia.impl;

/**
 * An interface for building objects of type T.
 *
 * @param <T> The type of object to be built.
 */
public interface Builder<T> {

    /**
     * Builds and returns an object of type T.
     *
     * @return The built object of type T.
     */
    T build();
}
