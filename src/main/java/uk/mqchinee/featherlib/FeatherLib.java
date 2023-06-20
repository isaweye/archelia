package uk.mqchinee.featherlib;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import uk.mqchinee.featherlib.plugin.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public final class FeatherLib extends JavaPlugin {

    public static List<String> currentlyUsing = new ArrayList<>();
    private static Logger log;
    private static FeatherLib instance;
    private static Economy economy = null;

    @Override
    public void onEnable() {
        instance = this;
        log = this.getLogger();

        setup();

        log.info("§fFeather§bLib §fhas been successfully §aloaded §fand is §aready to use§f!");
        update();

        getCommand("feather").setExecutor(new Command());
    }

    private void setup() {
        if (vault()) {
            log.info("§fHooked into §aVault§f!");
        }
        else { log.info("§fFailed to hook into §cVault§f!"); }
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
            if (plugin.getDescription().getDepend().contains("FeatherLib")) {
                currentlyUsing.add(plugin.getName());
            }
            if (plugin.getDescription().getSoftDepend().contains("FeatherLib")) {
                currentlyUsing.add(plugin.getName());
            }
        }
    }

    public static Logger logger() {
        return log;
    }

    public static Economy economy() {
        return economy;
    }
    public static FeatherLib get() {
        return instance;
    }

}
