package uk.mqchinee.archelia.abs;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

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
    /**
     * -- GETTER --
     *  Retrieves the file associated with this Config.
     *
     * @return The file associated with this Config.
     */
    @Getter
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
     * @return The Config instance.
     */
    public Config set(String path, Object value) {
        this.configuration.set(path, value);
        save();
        return this;
    }

    /**
     * Retrieves a configuration value from the specified path asynchronously and passes it to a consumer.
     *
     * @param path  The path to retrieve the value from.
     * @param value The consumer to receive the retrieved value.
     */
    public void get(Plugin plugin, String path, Consumer<Object> value) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> value.accept(this.configuration.get(path)));
    }

    /**
     * Saves the configuration to the underlying file.
     */
    @SneakyThrows
    public void save() {
        this.configuration.save(file);
    }

}
