package uk.mqchinee.archelia.plugin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import uk.mqchinee.archelia.Archelia;
import uk.mqchinee.archelia.annotations.CommandInfo;
import uk.mqchinee.archelia.commands.AbstractCommand;
import uk.mqchinee.archelia.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * The "archelia" command class for the Archelia plugin.
 */
@CommandInfo(
        permission = "archelia.info",
        permission_message = "&cI'm sorry, but you do not have permission to perform this command."
)
public class Command extends AbstractCommand {

    private List<Plugin> list;

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
    private String getCurrentlyUsing() {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 1;
        this.list = new ArrayList<>();
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            if (plugin.getDescription().getDepend().contains("Archelia") || plugin.getDescription().getSoftDepend().contains("Archelia") && (!Archelia.getAddons().containsKey(plugin.getName()))) {
                list.add(plugin);
            }
        }
        for (Plugin plugin : this.list) {
            if (this.list.size() != i) {
                stringBuilder.append(TextUtils.colorize("&a")).append(plugin.getName()).append(TextUtils.colorize("&f, "));
                i++;
            } else {
                stringBuilder.append(TextUtils.colorize("&a")).append(plugin.getName()).append(TextUtils.colorize("&f."));
            }
        }
        return stringBuilder.toString();
    }

    private String getAddons() {
        StringBuilder stringBuilder = new StringBuilder();
        Archelia.getAddons().forEach(((s, addon) -> stringBuilder.append(TextUtils.colorize("&a"+addon.getName()+" &7&o("+addon.getDescription()+")\n"))));
        return stringBuilder.toString();
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        getCurrentlyUsing();
        String cu = (this.list.size() > 0) ? "&7Currently used by these plugins: \n&8&l- " + getCurrentlyUsing() : "";
        String ar = (Archelia.getAddons().size() > 0) ? "&7Addons: \n&8&l- " + getAddons() : "";
        sender.sendMessage(TextUtils.colorize("&f--------< &6Archelia &f>--------"));
        sender.sendMessage(TextUtils.colorize("&7Author: &fmqchinee (isaweye)"));
        sender.sendMessage(TextUtils.colorize("&7Version: &f" + Archelia.getInstance().getDescription().getVersion()));
        sender.sendMessage(TextUtils.colorize("&7API version: &f" + Archelia.getInstance().getDescription().getAPIVersion()));
        sender.sendMessage(TextUtils.colorize(cu));
        sender.sendMessage(TextUtils.colorize(ar));
    }
}
