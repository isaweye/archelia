package uk.mqchinee.featherapi;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import uk.mqchinee.featherapi.plugin.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public final class FeatherAPI extends JavaPlugin {

    public static List<String> currentlyUsing = new ArrayList<>();
    private static Logger log;
    private static FeatherAPI instance;

    @Override
    public void onEnable() {
        instance = this;
        log = this.getLogger();

        saveDefaultConfig();

        log.info("§fFeather§bAPI §fhas been successfully §aloaded §fand is §aready to use§f!");
        update();

        getCommand("feather-api").setExecutor(new Command());
    }

    public static void update() {
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            if (plugin.getDescription().getDepend().contains("FeatherAPI")) {
                currentlyUsing.add(plugin.getName());
            }
        }
    }

    public static Logger logger() {
        return log;
    }

    public static FeatherAPI get() {
        return instance;
    }

}
