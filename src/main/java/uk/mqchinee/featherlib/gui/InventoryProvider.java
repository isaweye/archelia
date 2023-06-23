package uk.mqchinee.featherlib.gui;

import org.bukkit.plugin.java.JavaPlugin;
import uk.mqchinee.featherlib.gui.inventory.GUIListener;

public class InventoryProvider {

    private final JavaPlugin plugin;

    public InventoryProvider(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void init() {
        plugin.getServer().getPluginManager().registerEvents(new GUIListener(), plugin);
    }

}
