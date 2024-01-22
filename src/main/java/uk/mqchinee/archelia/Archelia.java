package uk.mqchinee.archelia;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import uk.mqchinee.archelia.scoreboard.tag.TagData;

import java.util.HashMap;
import java.util.logging.Logger;

/**
 * The main class for the Archelia.
 */
public class Archelia {

    @Getter private final Plugin plugin;

    @Getter private static HashMap<Player, TagData> tagData;
    @Getter private static final Logger logger = Logger.getLogger("Archelia");

    public Archelia(Plugin plugin) {
        this.plugin = plugin;
        tagData = new HashMap<>();
        logger.info("Registered "+plugin.getName()+".");
    }
}
