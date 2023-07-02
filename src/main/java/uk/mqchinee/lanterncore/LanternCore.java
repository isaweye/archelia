package uk.mqchinee.lanterncore;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import uk.mqchinee.lanterncore.plugin.Command;
import uk.mqchinee.lanterncore.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public final class LanternCore extends JavaPlugin {

    public static List<Plugin> currentlyUsing = new ArrayList<>();
    private static Logger log;
    private static LanternCore instance;
    private static Economy economy = null;

    @Override
    public void onEnable() {
        instance = this;
        log = this.getLogger();

        setup();

        Bukkit.getConsoleSender().sendMessage(TextUtils.colorize("&6Lantern&fCore &fhas been successfully &aloaded &fand is &aready to use&f!"));
        update();

        getCommand("lantern").setExecutor(new Command());
    }

    private void setup() {
        if (vault()) {
            Bukkit.getConsoleSender().sendMessage(TextUtils.colorize("&fHooked into &aVault&f!"));
        }
        else { Bukkit.getConsoleSender().sendMessage(TextUtils.colorize("&fFailed to hook into &cVault&f!")); }

    }

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

    private void update() {
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            if (plugin.getDescription().getDepend().contains("LanternCore")) {
                currentlyUsing.add(plugin);
            }
            if (plugin.getDescription().getSoftDepend().contains("LanternCore")) {
                currentlyUsing.add(plugin);
            }
        }
    }

    public static Logger logger() {
        return log;
    }

    public static Economy economy() {
        return economy;
    }

    public static LanternCore get() {
        return instance;
    }

}
