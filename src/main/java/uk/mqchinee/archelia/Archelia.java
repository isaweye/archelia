package uk.mqchinee.archelia;

import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import uk.mqchinee.archelia.plugin.Command;

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
        pluginLogger = this.getLogger();

        // Check which plugins are using Archelia as a dependency or soft dependency and add them to the currentlyUsing list.
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            if (plugin.getDescription().getDepend().contains("Archelia") || plugin.getDescription().getSoftDepend().contains("Archelia")) {
                currentlyUsing.add(plugin);
            }
        }

        // Register the custom command.
        new Command();

        // Attempt to hook into the Vault economy plugin.
        log();
    }

    private void log() {
        if (vault()) {
            this.getLogger().info("Hooked into Vault!");
            return;
        }
        this.getLogger().warning("Failed to hook into Vault!");
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
