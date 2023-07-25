package uk.mqchinee.archelia.net;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;
import uk.mqchinee.archelia.utils.RunUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.function.Consumer;

/**
 * A utility class to check the latest version of a GitHub repository asynchronously.
 */
public class AsyncGithubChecker {

    @Getter @Setter private String user;
    @Getter @Setter private String repository;
    @Getter @Setter private String version;
    @Getter @Setter private Consumer<String> onSuccess;
    @Getter @Setter private Runnable onFailure;
    @Getter @Setter private Runnable onLatest;

    /**
     * Constructs an instance of AsyncGithubChecker with the specified GitHub user, repository, and current version.
     *
     * @param user            The GitHub user or organization owning the repository.
     * @param repository      The name of the repository to check for updates.
     * @param current_version The current version of the repository.
     */
    public AsyncGithubChecker(String user, String repository, String current_version) {
        this.setUser(user);
        this.setRepository(repository);
        this.setVersion(current_version);
    }

    /**
     * Constructs an instance of AsyncGithubChecker with the specified GitHub user, repository, and plugin's version.
     *
     * @param user       The GitHub user or organization owning the repository.
     * @param repository The name of the repository to check for updates.
     * @param plugin     The JavaPlugin instance whose version will be used as the current version.
     */
    public AsyncGithubChecker(String user, String repository, JavaPlugin plugin) {
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

    private void getLatestVersion(Consumer<String> result) {
        RunUtils.async(() -> {
            URLConnection connection = getConnection();
            String tag = null;
            try {
                JsonObject json = JsonParser.parseReader(new InputStreamReader(connection.getInputStream())).getAsJsonObject();
                tag = json.get("tag_name").getAsString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            result.accept(tag);
        });
    }

    private void runIfSet(Runnable runnable) {
        if (runnable != null) {
            runnable.run();
        }
    }

    /**
     * Gets the link to the latest release of the GitHub repository.
     *
     * @return The link to the latest release of the repository.
     */
    public String getLink() {
        return String.format("https://github.com/%s/%s/releases/latest", getUser(), getRepository());
    }

    /**
     * Checks for updates of the GitHub repository asynchronously and invokes the corresponding callbacks.
     * If the latest version cannot be retrieved, the onFailure runnable will be executed.
     * If the repository is up-to-date, the onLatest runnable will be executed.
     * If a newer version is found, the onSuccess callback will be executed with the latest version.
     */
    public void check() {
        getLatestVersion((latest) -> {
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
