package uk.mqchinee.archelia.scoreboard.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import uk.mqchinee.archelia.scoreboard.ScoreboardHandler;

@RequiredArgsConstructor
public class PlayerListener implements Listener {

    private final ScoreboardHandler handler;

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        this.handler.getAdapter().removeElement(
                player,
                this.handler.getAdapter().getScoreboard(player)
        );
    }
}
