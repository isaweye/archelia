package uk.mqchinee.archelia.scoreboard.thread;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import uk.mqchinee.archelia.scoreboard.ScoreboardHandler;

@RequiredArgsConstructor
public class ScoreboardRunnable extends BukkitRunnable {

    private final ScoreboardHandler handler;

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            this.handler.getAdapter().handleElement(player, this.handler.getHandler().getElement(player));
        }
    }
}