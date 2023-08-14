package uk.mqchinee.archelia.abs;

import lombok.SneakyThrows;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import uk.mqchinee.archelia.utils.RunUtils;

import java.io.File;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Represents a configuration file.
 *
 * @since 1.0
 */
public class Config {

    private final FileConfiguration configuration;
    private final File file;

    /**
     * Constructs a new Config instance from the given path and config(file) name.
     *
     * @param path  The path to the folder.
     * @param child The name of the child file.
     */
    public Config(String path, String child) {
        File file = new File(path, child);
        this.configuration = YamlConfiguration.loadConfiguration(file);
        this.file = file;
    }

    /**
     * Constructs a new Config instance from the given file.
     *
     * @param file The configuration file.
     */
    public Config(File file) {
        this.configuration = YamlConfiguration.loadConfiguration(file);
        this.file = file;
    }

    /**
     * Constructs a new Config instance from the given FileConfiguration.
     *
     * @param config The FileConfiguration to use.
     */
    public Config(FileConfiguration config) {
        this.configuration = config;
        this.file = new File(config.getCurrentPath());
    }

    /**
     * Returns the hash code for this Config.
     *
     * @return The hash code for this Config.
     */
    public int hashCode() {
        return Objects.hash(configuration);
    }

    /**
     * Retrieves the underlying FileConfiguration of this Config.
     *
     * @return The FileConfiguration of this Config.
     */
    public FileConfiguration getConfig() {
        return configuration;
    }

    /**
     * Sets a configuration value at the specified path, optionally saving the configuration synchronously.
     *
     * @param path   The path to set the value at.
     * @param value  The value to set.
     * @param async  Whether to save the configuration asynchronously.
     * @return The Config instance.
     */
    public Config set(String path, Object value, boolean async) {
        if (!async) {
            this.configuration.set(path, value);
            save();
            return this;
        }
        RunUtils.async(() -> {
            this.configuration.set(path, value);
            save();
        });
        return this;
    }

    /**
     * Sets a configuration value at the specified path, saving the configuration asynchronously.
     *
     * @param path  The path to set the value at.
     * @param value The value to set.
     * @return The Config instance.
     */
    public Config set(String path, Object value) {
        RunUtils.async(() -> {
            this.configuration.set(path, value);
            save();
        });
        return this;
    }

    /**
     * Retrieves a configuration value from the specified path asynchronously and passes it to a consumer.
     *
     * @param path  The path to retrieve the value from.
     * @param value The consumer to receive the retrieved value.
     */
    public void get(String path, Consumer<Object> value) {
        RunUtils.async(() -> value.accept(this.configuration.get(path)));
    }

    /**
     * Saves the configuration to the underlying file.
     */
    @SneakyThrows
    public void save() {
        this.configuration.save(file);
    }

    /**
     * Retrieves the file associated with this Config.
     *
     * @return The file associated with this Config.
     */
    public File getFile() {
        return file;
    }
}
