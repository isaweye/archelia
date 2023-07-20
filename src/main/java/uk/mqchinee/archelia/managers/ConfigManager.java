package uk.mqchinee.archelia.managers;

import lombok.SneakyThrows;
import org.bukkit.configuration.file.FileConfiguration;
import uk.mqchinee.archelia.abs.Config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

public class ConfigManager {

    @SneakyThrows
    public static Config create(File file) {
        if (!file.exists()) {
            file.createNewFile();
        }
        return new Config(file);
    }

    @SneakyThrows
    public static Config create(String path, String child) {
        File file = new File(path, child);
        if (!file.exists()) {
            file.createNewFile();
        }
        return new Config(file);
    }

    @SneakyThrows
    public static Config create(String path, String child, InputStream resource) {
        return new Config(write(path, child, resource));
    }

    public static Config create(FileConfiguration configuration) {
        return new Config(configuration);
    }

    public static File write(String path, String filename, InputStream resource) {
        File file = new File(path, filename);
        if( !file.exists() ) {
            OutputStream outputStream = null;
            try {

                outputStream = Files.newOutputStream(file.toPath());


                int read;
                byte[] bytes = new byte[1024];

                while ((read = resource.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
            } catch (IOException e) { e.printStackTrace(); }
            finally {
                if (resource != null) {
                    try {
                        resource.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        return file;
    }



}
