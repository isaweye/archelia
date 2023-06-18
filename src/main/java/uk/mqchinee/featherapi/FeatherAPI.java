package uk.mqchinee.featherapi;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import uk.mqchinee.featherapi.economy.Economy;
import uk.mqchinee.featherapi.plugin.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public final class FeatherAPI extends JavaPlugin {

    public static List<String> currentlyUsing = new ArrayList<>();
    private static Logger log;
    private static FeatherAPI instance;
    private static Economy economy = null;
    private static boolean placeholder = false;

    @Override
    public void onEnable() {
        instance = this;
        log = this.getLogger();

        saveDefaultConfig();

        setup();

        log.info("§fFeather§bAPI §fhas been successfully §aloaded §fand is §aready to use§f!");
        update();

        getCommand("feather-api").setExecutor(new Command());
    }

    private void setup() {
        if (placeholder()) {
            log.info("§fHooked into §aPlaceholderAPI§f!");
            placeholder = true;
        }
        else { log.info("§fFailed to hook into §cPlaceholderAPI§f!"); }

        if (vault()) {
            log.info("§fHooked into §aVault§f!");
            Player player = Bukkit.getPlayerExact("greenMachine1123");
            economy.createPlayerAccount(player);
            economy.depositPlayer(player, 115);
            Bukkit.getServer().broadcastMessage(String.valueOf(economy.getBalance(player)));
        }
        else { log.info("§fFailed to hook into §cVault§f!"); }
    }
    private boolean placeholder() {
        if (Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            return true;
        }
        return false;
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
            if (plugin.getDescription().getDepend().contains("FeatherAPI")) {
                currentlyUsing.add(plugin.getName());
            }
        }
    }

    public static Logger logger() {
        return log;
    }

    public static boolean PAPIEnabled() {
        return placeholder;
    }

    public static Economy economy() {
        return economy;
    }
    public static FeatherAPI get() {
        return instance;
    }

}
