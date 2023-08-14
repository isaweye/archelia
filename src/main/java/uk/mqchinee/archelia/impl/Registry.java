package uk.mqchinee.archelia.impl;

import java.util.Map;
import java.util.function.Consumer;

public interface Registry<T, T2> {
    void register(T key, T2 value);
    T2 get(T key);
    boolean contains(T key);
    void replace(T key, T2 value);
    void remove(T key);
    void forEach(Consumer<Map.Entry<T, T2>> entry);
}