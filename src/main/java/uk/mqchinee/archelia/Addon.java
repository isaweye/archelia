package uk.mqchinee.archelia;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class Addon {

    @Getter private final String name;
    @Getter private final String description;
    @Getter private final JavaPlugin plugin;

    /**
     * Constructs a new Addon instance.
     *
     * @param name        The name of the addon.
     * @param description A description of the addon.
     * @param plugin      The JavaPlugin instance associated with the addon.
     */
    public Addon(String name, String description, JavaPlugin plugin) {
        this.name = name;
        this.description = description;
        this.plugin = plugin;
    }

}
