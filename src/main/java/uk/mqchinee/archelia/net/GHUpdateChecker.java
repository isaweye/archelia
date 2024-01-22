package uk.mqchinee.archelia.net;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import uk.mqchinee.archelia.impl.UpdateChecker;
import uk.mqchinee.archelia.utils.TextUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.function.Consumer;

/**
 * A utility class to check for updates on GitHub repositories.
 */
public class GHUpdateChecker implements UpdateChecker {

    @Getter @Setter private String user;
    @Getter @Setter private String repository;
    @Getter @Setter private String version;
    @Getter @Setter private Consumer<String> onSuccess;
    @Getter @Setter private Runnable onFailure;
    @Getter @Setter private Runnable onLatest;
    @Getter @Setter private List<String> description;
    @Getter @Setter private Plugin plugin;

    /**
     * Constructs a new GHUpdateChecker with the provided user, repository, and current version.
     *
     * @param user            The GitHub user or organization name.
     * @param repository      The GitHub repository name.
     * @param current_version The current version of the plugin.
     */
    public GHUpdateChecker(String user, String repository, String current_version) {
        this.setUser(user);
        this.setRepository(repository);
        this.setVersion(current_version);
    }

    /**
     * Constructs a new GHUpdateChecker with the provided user, repository, and plugin.
     * The current version is obtained from the plugin's description.
     *
     * @param user       The GitHub user or organization name.
     * @param repository The GitHub repository name.
     * @param plugin     The JavaPlugin instance representing the plugin to check for updates.
     */
    public GHUpdateChecker(String user, String repository, JavaPlugin plugin) {
        this.setUser(user);
        this.setRepository(repository);
        this.setVersion(plugin.getDescription().getVersion());
        this.setPlugin(plugin);
    }

    private URLConnection getConnection() {
        URLConnection connection = null;
        try {
            URL url = new URL(String.format("https://api.github.com/repos/%s/%s/releases/latest", getUser(), getRepository()));
            connection = url.openConnection();
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(15000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    private void runIfSet(Runnable runnable) {
        if (runnable != null) {
            runnable.run();
        }
    }

    private void getData(Consumer<String> result) {
        Bukkit.getScheduler().runTaskAsynchronously(getPlugin(), () -> {
            URLConnection connection = getConnection();
            String tag = null;
            try {
                JsonObject json = JsonParser.parseReader(new InputStreamReader(connection.getInputStream())).getAsJsonObject();
                tag = json.get("tag_name").getAsString();
                setDescription(TextUtils.fromString(json.get("body").getAsString().replace("**", "").replace("__", "")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            result.accept(tag);
        });
    }

    /**
     * Gets the URL link to the latest release on GitHub.
     *
     * @return The URL link to the latest release on GitHub.
     */
    public String getLink() {
        return String.format("https://github.com/%s/%s/releases/latest", getUser(), getRepository());
    }

    @Override
    public void check() {
        getData((latest) -> {
            if (latest == null) {
                runIfSet(getOnFailure());
                return;
            }
            if (latest.equals(getVersion())) {
                runIfSet(getOnLatest());
                return;
            }
            if (getOnSuccess() != null) {
                getOnSuccess().accept(latest);
            }
        });
    }

}
