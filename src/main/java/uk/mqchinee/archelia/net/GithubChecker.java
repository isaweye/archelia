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

public class GithubChecker {

    @Getter @Setter private String user;
    @Getter @Setter private String repository;
    @Getter @Setter private String version;
    @Getter @Setter Consumer<String> onSuccess;
    @Getter @Setter Runnable onFailure;
    @Getter @Setter Runnable onLatest;

    public GithubChecker(String user, String repository, String current_version) {
        this.setUser(user);
        this.setRepository(repository);
        this.setVersion(current_version);
    }

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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tag;
    }

    private void runIfSet(Runnable runnable) {
        if (runnable != null) { runnable.run(); }
    }

    public String getLink() {
        return String.format("https://github.com/%s/%s/releases/latest", getUser(), getRepository());
    }

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
