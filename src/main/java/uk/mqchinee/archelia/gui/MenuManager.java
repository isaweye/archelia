package uk.mqchinee.archelia.gui;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public final class MenuManager {

    static final Map<JavaPlugin, ChestMenu.Listener> registeredListeners = Maps.newHashMap();

    /**
     @param isConcurrent Allows you to choose between HashMap (recommended) and ConcurrentHashMap.
     If you want to get rid of ConcurrentModificationException (occurs when using MovableItem), use ConcurrentHashMap
     */
    public static ChestMenu createChestMenu(String title, int rows, JavaPlugin plugin, boolean isConcurrent) {
        registerListener(plugin);
        return new ChestMenu(title, rows, plugin, isConcurrent);
    }

    /**
     @param isConcurrent Allows you to choose between HashMap (recommended) and ConcurrentHashMap.
     If you want to get rid of ConcurrentModificationException (occurs when using MovableItem), use ConcurrentHashMap
     */
    public static PageableChestMenu createPageableChestMenu(String title, int rows, int[] itemSlots, JavaPlugin plugin, boolean isConcurrent) {
        registerListener(plugin);
        return new PageableChestMenu(title, rows, itemSlots, plugin, isConcurrent);
    }

    /**
     @param isConcurrent Allows you to choose between HashMap (recommended) and ConcurrentHashMap.
     If you want to get rid of ConcurrentModificationException (occurs when using MovableItem), use ConcurrentHashMap
     */
    public static PageableChestMenu createPageableChestMenu(String title, int rows, JavaPlugin plugin, boolean isConcurrent) {
        registerListener(plugin);
        return new PageableChestMenu(title, rows, plugin, isConcurrent);
    }

    private static void registerListener(JavaPlugin plugin) {
        if(!registeredListeners.containsKey(plugin)) {
            ChestMenu.Listener listener = new ChestMenu.Listener(plugin);
            Bukkit.getPluginManager().registerEvents(listener, plugin);
            registeredListeners.put(plugin, listener);
        }
    }

}
