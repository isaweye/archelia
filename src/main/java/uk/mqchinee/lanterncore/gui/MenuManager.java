package uk.mqchinee.lanterncore.gui;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public final class MenuManager {

    static final Map<JavaPlugin, ChestMenu.Listener> registeredListeners = Maps.newHashMap();

    public static ChestMenu createChestMenu(String title, int rows, JavaPlugin plugin) {
        registerListener(plugin);
        return new ChestMenu(title, rows, plugin);
    }

    public static PageableChestMenu createPageableChestMenu(String title, int rows, int[] itemSlots, JavaPlugin plugin) {
        registerListener(plugin);
        return new PageableChestMenu(title, rows, itemSlots, plugin);
    }

    public static PageableChestMenu createPageableChestMenu(String title, int rows, JavaPlugin plugin) {
        registerListener(plugin);
        return new PageableChestMenu(title, rows, plugin);
    }

    private static void registerListener(JavaPlugin plugin) {
        if(!registeredListeners.containsKey(plugin)) {
            ChestMenu.Listener listener = new ChestMenu.Listener(plugin);
            Bukkit.getPluginManager().registerEvents(listener, plugin);
            registeredListeners.put(plugin, listener);
        }
    }

}
