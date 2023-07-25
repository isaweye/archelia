package uk.mqchinee.archelia;

import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import uk.mqchinee.archelia.net.AsyncGithubChecker;
import uk.mqchinee.archelia.plugin.Command;
import uk.mqchinee.archelia.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * The main plugin class for the Archelia plugin.
 */
public final class Archelia extends JavaPlugin {

    /**
     * A list of plugins that are currently using the Archelia plugin as a dependency or soft dependency.
     */
    public static List<Plugin> currentlyUsing = new ArrayList<>();

    @Getter
    private static Logger pluginLogger;

    @Getter
    private static Archelia instance;

    @Getter
    private static Economy economy = null;

    @Override
    public void onEnable() {
        instance = this;
        setup();
    }

    private void setup() {
        pluginLogger = this.getLogger();

        new Command();
        if (vault()) {
            this.getLogger().info("Hooked into Vault!");
            return;
        }
        this.getLogger().warning("Failed to hook into Vault!");

        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            if (plugin.getDescription().getDepend().contains("Archelia") || plugin.getDescription().getSoftDepend().contains("Archelia")) {
                currentlyUsing.add(plugin);
            }
        }

        getLogger().info("Checking for updates...");
        getChecker().check();

    }

    private AsyncGithubChecker getChecker() {
        AsyncGithubChecker checker = new AsyncGithubChecker("isaweye", "archelia", this);
        checker.setOnLatest(() -> getLogger().info("No updates available!"));
        checker.setOnFailure(() -> getLogger().warning("Unable to check for updates."));
        checker.setOnSuccess((latest) -> {
            getLogger().info("An update has been found!");
            TextUtils.console(TextUtils.colorize("[Archelia] &eCurrent version: &f"+ checker.getVersion()));
            TextUtils.console(TextUtils.colorize("[Archelia] &aLatest version: &f"+ latest + " &o("+ checker.getCommitMessage() +")"));
            getLogger().warning("Download here: "+ checker.getLink());
        });
        return checker;
    }

    /**
     * Attempt to hook into the Vault economy plugin and set the economy variable.
     *
     * @return True if hooking was successful, false otherwise.
     */
    private boolean vault() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") != null) {
            RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
            if (rsp == null) {
                return false;
            }
            economy = rsp.getProvider();
            return true;
        }
        return false;
    }

}
