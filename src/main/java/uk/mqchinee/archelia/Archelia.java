package uk.mqchinee.archelia;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import uk.mqchinee.archelia.impl.UpdateChecker;
import uk.mqchinee.archelia.net.GHUpdateChecker;
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
        sendConsoleMessage("Checking for updates...");
        getChecker().check();
    }

    public static void sendConsoleMessage(String message) {
        Bukkit.getServer().getConsoleSender().sendMessage(message);
    }

    private UpdateChecker getChecker() {
        GHUpdateChecker checker = new GHUpdateChecker("isaweye", "archelia", (JavaPlugin) plugin);
        checker.setOnLatest(() -> sendConsoleMessage("No updates available!"));
        checker.setOnFailure(() -> sendConsoleMessage("Unable to check for updates."));
        checker.setOnSuccess((latest) -> {
            sendConsoleMessage("An update has been found!");
            TextUtils.console(TextUtils.colorize("[Archelia] &eCurrent version: &f"+ checker.getVersion()));
            TextUtils.console(TextUtils.colorize("[Archelia] &aLatest version: &f"+ latest));
            TextUtils.console(TextUtils.colorize("[Archelia] &fChangelog: "));
            checker.getDescription().forEach(Archelia::sendConsoleMessage);
            sendConsoleMessage("Download here: "+ checker.getLink());
        });
        return checker;
    }

}
