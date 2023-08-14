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

    /**
     * Creates a new Config object from the specified file.
     *
     * @param file The file to create the Config object from.
     * @return The Config object representing the file's configuration.
     * @throws IOException If an I/O error occurs while creating the file.
     */
    @SneakyThrows
    public static Config create(File file) {
        if (!file.exists()) {
            file.createNewFile();
        }
        return new Config(file);
    }

    /**
     * Creates a new Config object from the specified file path and child filename.
     *
     * @param path  The path of the file.
     * @param child The child filename of the file.
     * @return The Config object representing the file's configuration.
     * @throws IOException If an I/O error occurs while creating the file.
     */
    @SneakyThrows
    public static Config create(String path, String child) {
        File file = new File(path, child);
        if (!file.exists()) {
            file.createNewFile();
        }
        return new Config(file);
    }

    /**
     * Creates a new Config object from the specified file path, child filename, and resource InputStream.
     *
     * @param path     The path of the file.
     * @param child    The child filename of the file.
     * @param resource The InputStream of the resource to write to the file if it doesn't exist.
     * @return The Config object representing the file's configuration.
     * @throws IOException If an I/O error occurs while creating the file.
     */
    @SneakyThrows
    public static Config create(String path, String child, InputStream resource) {
        return new Config(write(path, child, resource));
    }

    /**
     * Creates a new Config object from the specified FileConfiguration.
     *
     * @param configuration The FileConfiguration to create the Config object from.
     * @return The Config object representing the configuration.
     */
    public static Config create(FileConfiguration configuration) {
        return new Config(configuration);
    }

    /**
     * Writes the contents of the resource InputStream to a file at the specified path and filename.
     *
     * @param path     The path of the file.
     * @param filename The filename of the file.
     * @param resource The InputStream of the resource to write to the file.
     * @return The File object representing the file where the resource was written.
     * @throws IOException If an I/O error occurs while writing the file.
     */
    public static File write(String path, String filename, InputStream resource) {
        File file = new File(path, filename);
        if (!file.exists()) {
            OutputStream outputStream = null;
            try {
                outputStream = Files.newOutputStream(file.toPath());

                int read;
                byte[] bytes = new byte[1024];

                while ((read = resource.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
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
