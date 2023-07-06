package uk.mqchinee.lanterncore.abs;

import lombok.SneakyThrows;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import uk.mqchinee.lanterncore.impl.ConfigInterface;
import uk.mqchinee.lanterncore.utils.RunUtils;

import java.io.File;
import java.util.Objects;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class Config implements ConfigInterface {

    private final FileConfiguration configuration;
    private final File file;

    public Config(String path, String child) {
        File file = new File(path, child);
        this.configuration = YamlConfiguration.loadConfiguration(file);
        this.file = file;
    }

    public Config(File file) {
        this.configuration = YamlConfiguration.loadConfiguration(file);
        this.file = file;
    }

    public Config(FileConfiguration config) {
        this.configuration = config;
        this.file = new File(config.getCurrentPath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(configuration);
    }

    @Override
    public FileConfiguration getConfig() {
        return configuration;
    }

    @Override
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

    @Override
    public Config set(String path, Object value) {
        RunUtils.async(() -> {
            this.configuration.set(path, value);
            save();
        });
        return this;
    }

    @Override
    public void get(String path, Consumer<Object> value) {
        RunUtils.async(() -> value.accept(this.configuration.get(path)));
    }

    @Override
    @SneakyThrows
    public void save() {
        this.configuration.save(file);
    }

    @Override
    public File getFile() {
        return file;
    }
}
