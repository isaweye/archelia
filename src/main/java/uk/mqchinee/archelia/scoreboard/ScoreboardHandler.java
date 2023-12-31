package uk.mqchinee.archelia.scoreboard;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import uk.mqchinee.archelia.scoreboard.element.ScoreboardElementHandler;
import uk.mqchinee.archelia.scoreboard.listener.PlayerListener;
import uk.mqchinee.archelia.scoreboard.thread.ScoreboardRunnable;

@Getter
public class ScoreboardHandler {

    private final ScoreboardElementHandler handler;
    private final ScoreboardAdapter adapter;

    /**
     * Constructor to make a new scoreboard handler
     *
     * @param plugin         the plugin to register it to
     * @param elementHandler the handler to get the elements from
     * @param delay          the delay in between scoreboard ticks
     */
    public ScoreboardHandler(JavaPlugin plugin, ScoreboardElementHandler elementHandler, long delay) {
        this.handler = elementHandler;
        this.adapter = new ScoreboardAdapter();

        new ScoreboardRunnable(this).runTaskTimer(plugin, 10L, delay);

        // listener registration
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), plugin);
    }
}
