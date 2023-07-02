package uk.mqchinee.lanterncore.impl;

import lombok.SneakyThrows;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.function.Consumer;

public interface ConfigInterface {
    int hashCode();

    FileConfiguration getConfig();

    ConfigInterface set(String path, Object value, boolean async);

    ConfigInterface set(String path, Object value);

    void get(String path, Consumer<Object> value);

    @SneakyThrows
    void save();

    File getFile();
}
