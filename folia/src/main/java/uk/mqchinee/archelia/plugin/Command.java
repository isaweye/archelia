package uk.mqchinee.archelia.plugin;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import uk.mqchinee.archelia.Archelia;
import uk.mqchinee.archelia.annotations.CommandInfo;
import uk.mqchinee.archelia.commands.AbstractCommand;
import uk.mqchinee.archelia.utils.TextUtils;

import java.util.List;

/**
 * The "archelia" command class for the Archelia plugin.
 */
@CommandInfo(
        permission = "archelia.info",
        permission_message = "&cI'm sorry, but you do not have permission to perform this command."
)
public class Command extends AbstractCommand {

    /**
     * Constructor for the "archelia" command.
     * Initializes the command with the name "archelia" and the instance of the Archelia plugin.
     */
    public Command() {
        super("archelia", Archelia.getInstance());
    }

    /**
     * Formats the list of plugins currently using Archelia into a string.
     *
     * @return A formatted string with the names of plugins currently using Archelia.
     */
    private String get() {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 1;
        List<Plugin> list = Archelia.currentlyUsing;
        for (Plugin plugin : list) {
            if (list.size() != i) {
                stringBuilder.append(TextUtils.colorize("&a")).append(plugin.getName()).append(TextUtils.colorize("&f, "));
                i++;
            } else {
                stringBuilder.append(TextUtils.colorize("&a")).append(plugin.getName()).append(TextUtils.colorize("&f."));
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        // Display information about the Archelia plugin to the sender
        String cu = (Archelia.currentlyUsing.size() > 0) ? "&7Currently used by these plugins: \n&8&l- " + get() : "";
        sender.sendMessage(TextUtils.colorize("&f--------< &6Archelia &f>--------"));
        sender.sendMessage(TextUtils.colorize("&7Author: &fmqchinee (isaweye)"));
        sender.sendMessage(TextUtils.colorize("&7Version: &f" + Archelia.getInstance().getDescription().getVersion()));
        sender.sendMessage(TextUtils.colorize("&7API version: &f" + Archelia.getInstance().getDescription().getAPIVersion()));
        sender.sendMessage(TextUtils.colorize(cu));
    }
}
