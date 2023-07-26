package uk.mqchinee.archelia.gui;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

/**
 * The MenuManager class is a utility class that provides methods for creating different types of Chest Menus and registering their listeners.
 * @since 1.0
 */
public final class MenuManager {

    /**
     * A mapping of JavaPlugin instances to their respective ChestMenu Listeners.
     */
    static final Map<JavaPlugin, ChestMenu.Listener> registeredListeners = Maps.newHashMap();

    /**
     * Creates a new ChestMenu with the specified title, number of rows, and plugin.
     *
     * @param title        The title of the menu.
     * @param rows         The number of rows for the menu (1 to 6).
     * @param plugin       The JavaPlugin instance that owns the menu.
     * @param isConcurrent Allows you to choose between HashMap (recommended) and ConcurrentHashMap.
     *                     If you want to get rid of ConcurrentModificationException (occurs when using MovableItem),
     *                     use ConcurrentHashMap.
     * @return The created ChestMenu.
     */
    public static ChestMenu createChestMenu(String title, int rows, JavaPlugin plugin, boolean isConcurrent) {
        registerListener(plugin);
        return new ChestMenu(title, rows, plugin, isConcurrent);
    }

    /**
     * Creates a new PageableChestMenu with the specified title, number of rows, item slots, and plugin.
     *
     * @param title        The title of the menu.
     * @param rows         The number of rows for the menu (1 to 6).
     * @param itemSlots    An array of integers representing the slots where the items will be placed on the pages.
     * @param plugin       The JavaPlugin instance that owns the menu.
     * @param isConcurrent Allows you to choose between HashMap (recommended) and ConcurrentHashMap.
     *                     If you want to get rid of ConcurrentModificationException (occurs when using MovableItem),
     *                     use ConcurrentHashMap.
     * @return The created PageableChestMenu.
     */
    public static PageableChestMenu createPageableChestMenu(String title, int rows, int[] itemSlots, JavaPlugin plugin, boolean isConcurrent) {
        registerListener(plugin);
        return new PageableChestMenu(title, rows, itemSlots, plugin, isConcurrent);
    }

    /**
     * Creates a new PageableChestMenu with the specified title, number of rows, and plugin.
     *
     * @param title        The title of the menu.
     * @param rows         The number of rows for the menu (1 to 6).
     * @param plugin       The JavaPlugin instance that owns the menu.
     * @param isConcurrent Allows you to choose between HashMap (recommended) and ConcurrentHashMap.
     *                     If you want to get rid of ConcurrentModificationException (occurs when using MovableItem),
     *                     use ConcurrentHashMap.
     * @return The created PageableChestMenu.
     */
    public static PageableChestMenu createPageableChestMenu(String title, int rows, JavaPlugin plugin, boolean isConcurrent) {
        registerListener(plugin);
        return new PageableChestMenu(title, rows, plugin, isConcurrent);
    }

    /**
     * Registers the ChestMenu.Listener for the specified JavaPlugin, if not already registered.
     *
     * @param plugin The JavaPlugin instance to register the listener for.
     */
    private static void registerListener(JavaPlugin plugin) {
        if (!registeredListeners.containsKey(plugin)) {
            ChestMenu.Listener listener = new ChestMenu.Listener(plugin);
            Bukkit.getPluginManager().registerEvents(listener, plugin);
            registeredListeners.put(plugin, listener);
        }
    }

}
