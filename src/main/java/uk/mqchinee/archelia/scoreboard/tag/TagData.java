package uk.mqchinee.archelia.scoreboard.tag;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import uk.mqchinee.archelia.Archelia;

@Getter @Setter
public class TagData {

    private final Player player;
    private String prefix = null;
    private String suffix = null;
    private String weight = null;

    public TagData(Player player) {
        this.player = player;
    }

    public void apply() {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Archelia.getTagData().replace(player, this);
        if (weight != null && scoreboard.getTeam(weight+player.getName()) != null) {
            scoreboard.getTeam(weight+player.getName()).unregister();
        }
        Team team = scoreboard.registerNewTeam(weight+player.getName());
        if (prefix != null) { team.setPrefix(prefix); }
        if (suffix != null) { team.setPrefix(suffix); }
        team.addEntry(player.getName());
    }

}
