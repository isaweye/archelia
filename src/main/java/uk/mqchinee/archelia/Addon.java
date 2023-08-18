package uk.mqchinee.archelia;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class Addon {

    @Getter private final String name;
    @Getter private final String description;
    @Getter private final JavaPlugin plugin;

    public Addon(String name, String description, JavaPlugin plugin) {
        this.name = name;
        this.description = description;
        this.plugin = plugin;
    }

}
