package uk.mqchinee.featherapi.plugin;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import uk.mqchinee.featherapi.FeatherAPI;

public class Command implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        sender.sendMessage("§f--------< Feather§bAPI §f>--------\n§7Author: §fmqchinee\n§7Version: §f"+ FeatherAPI.get().getDescription().getVersion() + "\n§7API version: §f"+ FeatherAPI.get().getDescription().getAPIVersion() + "\n§7Currently used by these plugins: \n§8§l- §a"+ FeatherAPI.currentlyUsing.toString().replace("[", "").replace("]", ""));
        return true;
    }

}
