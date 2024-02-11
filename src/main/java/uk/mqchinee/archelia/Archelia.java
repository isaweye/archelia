package uk.mqchinee.archelia;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import uk.mqchinee.archelia.scoreboard.tag.TagData;
import uk.mqchinee.archelia.utils.TextUtils;

import java.util.HashMap;

/**
 * The main class for the Archelia.
 */
public class Archelia {

    @Getter private final Plugin plugin;

    @Getter private static HashMap<Player, TagData> tagData;

    public Archelia(Plugin plugin) {
        this.plugin = plugin;
        tagData = new HashMap<>();
        Bukkit.getConsoleSender().sendMessage(TextUtils.colorize("[Archelia] Registered &6"+plugin.getName()+"&r."));
    }

    public Archelia(Plugin plugin, boolean silent) {
        this.plugin = plugin;
        tagData = new HashMap<>();
        if (silent) {
            Bukkit.getConsoleSender().sendMessage(TextUtils.colorize("[Archelia] Registered &6"+plugin.getName()+"&r."));
        }
    }
}
