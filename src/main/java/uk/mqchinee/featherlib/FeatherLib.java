package uk.mqchinee.featherlib;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
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

    public static List<Plugin> currentlyUsing = new ArrayList<>();
    private static Logger log;
    private static FeatherLib instance;
    private static Economy economy = null;
    private static ProtocolManager protocolManager = null;

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

        if (protocolLib()) {
            log.info("§fHooked into §aProtocolLib§f!");
        }
        else { log.info("§fFailed to hook into §cProtocolLib§f!"); }
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

    private boolean protocolLib() {
        if (Bukkit.getServer().getPluginManager().getPlugin("ProtocolLib") != null) {
            protocolManager = ProtocolLibrary.getProtocolManager();
            return true;
        }
        return false;
    }

    private void update() {
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            if (plugin.getDescription().getDepend().contains("FeatherLib")) {
                currentlyUsing.add(plugin);
            }
            if (plugin.getDescription().getSoftDepend().contains("FeatherLib")) {
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

    public static ProtocolManager protocol() {
        return protocolManager;
    }
    public static FeatherLib get() {
        return instance;
    }

}
