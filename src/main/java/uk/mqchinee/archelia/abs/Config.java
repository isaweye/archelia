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
 * @since 1.0
 */
@SuppressWarnings("unused")
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

    public int hashCode() {
        return Objects.hash(configuration);
    }

    public FileConfiguration getConfig() {
        return configuration;
    }

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

    public Config set(String path, Object value) {
        RunUtils.async(() -> {
            this.configuration.set(path, value);
            save();
        });
        return this;
    }

    public void get(String path, Consumer<Object> value) {
        RunUtils.async(() -> value.accept(this.configuration.get(path)));
    }

    @SneakyThrows
    public void save() {
        this.configuration.save(file);
    }

    public File getFile() {
        return file;
    }
}
