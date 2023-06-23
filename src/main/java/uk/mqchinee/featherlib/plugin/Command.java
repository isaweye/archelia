package uk.mqchinee.featherlib.plugin;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import uk.mqchinee.featherlib.FeatherLib;

import java.util.List;

public class Command implements CommandExecutor {

    private String get() {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        List<Plugin> list = FeatherLib.currentlyUsing;
        for (Plugin plugin : list) {
            if (list.size() != i) {
                stringBuilder.append("§a").append(plugin.getName()).append("§f, ");
                i++;
            }
            else { stringBuilder.append("§a").append(plugin.getName()).append("§f."); }
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        sender.sendMessage("§f--------< Feather§bAPI §f>--------\n§7Author: §fmqchinee\n§7Version: §f"+ FeatherLib.get().getDescription().getVersion() + "\n§7API version: §f"+ FeatherLib.get().getDescription().getAPIVersion() + "\n§7Currently used by these plugins: \n§8§l- "+ get());
        return true;
    }

}
