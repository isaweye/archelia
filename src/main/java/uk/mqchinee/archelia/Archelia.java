package uk.mqchinee.archelia;

import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import uk.mqchinee.archelia.annotations.ArcheliaAddon;
import uk.mqchinee.archelia.commands.AbstractArcheliaSubcommand;
import uk.mqchinee.archelia.impl.UpdateChecker;
import uk.mqchinee.archelia.net.GHUpdateChecker;
import uk.mqchinee.archelia.plugin.Command;
import uk.mqchinee.archelia.utils.TextUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * The main plugin class for the Archelia plugin.
 */
public final class Archelia extends JavaPlugin {

    @Getter
    private static Logger pluginLogger;

    @Getter private static Map<String, Addon> addons;

    @Getter
    private static Archelia instance;

    @Getter private static Map<String, AbstractArcheliaSubcommand> subcommands;

    @Getter
    private static Economy economy = null;

    @Override
    public void onEnable() {
        instance = this;
        setup();
    }

    private void setup() {
        pluginLogger = this.getLogger();
        subcommands = new HashMap<>();
        addons = new HashMap<>();

        new Command();
        if (vault()) {
            this.getLogger().info("Hooked into Vault!");
            return;
        }
        this.getLogger().warning("Failed to hook into Vault!");

        getLogger().info("Checking for updates...");
        getChecker().check();
    }

    public static void registerAddon(JavaPlugin plugin) {
        if (!plugin.getClass().isAnnotationPresent(ArcheliaAddon.class)) {
            throw new NullPointerException("null");
        }
        if (getAddons().containsKey(plugin.getName())) {
            return;
        }
        ArcheliaAddon addon = plugin.getClass().getAnnotation(ArcheliaAddon.class);
        addons.put(plugin.getName(), new Addon(plugin.getName(), addon.description(), plugin));
    }

    public static void registerSubcommand(AbstractArcheliaSubcommand subcommand) {
        if (subcommands.containsKey(subcommand.getName())) {
            subcommands.replace(subcommand.getName(), subcommand);
            return;
        }
        subcommands.put(subcommand.getName(), subcommand);
    }

    public static void registerSubcommands(AbstractArcheliaSubcommand... subcommand) {
        for (AbstractArcheliaSubcommand sbc : subcommand) {
            registerSubcommand(sbc);
        }
    }

    private UpdateChecker getChecker() {
        GHUpdateChecker checker = new GHUpdateChecker("isaweye", "archelia", this);
        checker.setOnLatest(() -> getLogger().info("No updates available!"));
        checker.setOnFailure(() -> getLogger().warning("Unable to check for updates."));
        checker.setOnSuccess((latest) -> {
            getLogger().info("An update has been found!");
            TextUtils.console(TextUtils.colorize("[Archelia] &eCurrent version: &f"+ checker.getVersion()));
            TextUtils.console(TextUtils.colorize("[Archelia] &aLatest version: &f"+ latest));
            TextUtils.console(TextUtils.colorize("[Archelia] &fChangelog: "));
            checker.getDescription().forEach((line) -> getLogger().info(line));
            getLogger().info("Download here: "+ checker.getLink());
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
