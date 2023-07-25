package uk.mqchinee.archelia.net;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.function.Consumer;

/**
 * Utility class to check for updates of a plugin on GitHub.
 */
public class GithubChecker {

    @Getter @Setter private String user;
    @Getter @Setter private String repository;
    @Getter @Setter private String version;
    @Getter @Setter private Consumer<String> onSuccess;
    @Getter @Setter private Runnable onFailure;
    @Getter @Setter private Runnable onLatest;
    @Getter @Setter private String commitMessage;

    /**
     * Constructs a GithubChecker instance with the given user, repository, and current_version.
     *
     * @param user            The GitHub user/organization name.
     * @param repository      The GitHub repository name.
     * @param current_version The current version of the plugin.
     */
    public GithubChecker(String user, String repository, String current_version) {
        this.setUser(user);
        this.setRepository(repository);
        this.setVersion(current_version);
    }

    /**
     * Constructs a GithubChecker instance with the given user, repository, and plugin's version.
     *
     * @param user       The GitHub user/organization name.
     * @param repository The GitHub repository name.
     * @param plugin     The JavaPlugin instance to get the version from.
     */
    public GithubChecker(String user, String repository, JavaPlugin plugin) {
        this.setUser(user);
        this.setRepository(repository);
        this.setVersion(plugin.getDescription().getVersion());
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

    private String getLatestVersion() {
        URLConnection connection = getConnection();
        String tag = null;
        try {
            JsonObject json = JsonParser.parseReader(new InputStreamReader(connection.getInputStream())).getAsJsonObject();
            tag = json.get("tag_name").getAsString();
            setCommitMessage(json.get("body").getAsString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tag;
    }

    private void runIfSet(Runnable runnable) {
        if (runnable != null) { runnable.run(); }
    }

    /**
     * Gets the link to the latest release of the GitHub repository.
     *
     * @return The link to the latest release.
     */
    public String getLink() {
        return String.format("https://github.com/%s/%s/releases/latest", getUser(), getRepository());
    }

    /**
     * Checks for updates of the plugin on GitHub and runs appropriate actions based on the result.
     * If a newer version is found, the onSuccess callback will be executed with the latest version.
     * If there's an error fetching the latest version, the onFailure runnable will be executed.
     * If the plugin is up-to-date, the onLatest runnable will be executed.
     */
    public void check() {
        String latest = getLatestVersion();
        if (latest == null) {
            runIfSet(getOnFailure());
            return;
        }
        if (latest.equals(getVersion())) {
            runIfSet(getOnLatest());
            return;
        }
        if (getOnSuccess() != null) getOnSuccess().accept(latest);
    }
}
