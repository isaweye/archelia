package uk.mqchinee.archelia.scoreboard.tag;

import lombok.Getter;
import org.bukkit.entity.Player;
import uk.mqchinee.archelia.Archelia;

@Getter
public class TagManager {

    private final Player player;
    private final TagData tagData;
    private TagManager(Player player) {
        this.player = player;
        if (Archelia.getTagData().containsKey(player)) {
            tagData = Archelia.getTagData().get(player);
        } else {
            tagData = new TagData(player);
            Archelia.getTagData().put(player, tagData);
        }
    }

}
