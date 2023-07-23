package uk.mqchinee.archelia.impl;

import lombok.SneakyThrows;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.function.Consumer;

public interface ConfigInterface {

    /**
     * Returns the hash code of this ConfigInterface. The hash code is used for identifying unique instances.
     *
     * @return The hash code of this ConfigInterface.
     */
    int hashCode();

    /**
     * Gets the FileConfiguration associated with this ConfigInterface.
     *
     * @return The FileConfiguration associated with this ConfigInterface.
     */
    FileConfiguration getConfig();

    /**
     * Sets the value of a specific configuration path in the configuration file. This method can be executed synchronously
     * or asynchronously based on the provided 'async' flag.
     *
     * @param path  The path to the configuration value.
     * @param value The value to set.
     * @param async Whether to execute the operation asynchronously (true) or synchronously (false).
     * @return The ConfigInterface instance for method chaining.
     */
    ConfigInterface set(String path, Object value, boolean async);

    /**
     * Sets the value of a specific configuration path in the configuration file. This method is executed synchronously.
     *
     * @param path  The path to the configuration value.
     * @param value The value to set.
     * @return The ConfigInterface instance for method chaining.
     */
    ConfigInterface set(String path, Object value);

    /**
     * Gets the value of a specific configuration path and provides it to the given consumer asynchronously.
     *
     * @param path  The path to the configuration value.
     * @param value The consumer that will receive the configuration value.
     */
    void get(String path, Consumer<Object> value);

    /**
     * Saves the configuration to its associated file.
     *
     * @throws java.io.IOException If an error occurs while saving the configuration.
     */
    @SneakyThrows
    void save();

    /**
     * Gets the File associated with this ConfigInterface.
     *
     * @return The File associated with this ConfigInterface.
     */
    File getFile();
}
