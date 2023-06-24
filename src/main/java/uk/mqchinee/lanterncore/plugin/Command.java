package uk.mqchinee.lanterncore.plugin;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import uk.mqchinee.lanterncore.LanternCore;
import uk.mqchinee.lanterncore.utils.TextUtils;

import java.util.List;

public class Command implements CommandExecutor {

    private String get() {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        List<Plugin> list = LanternCore.currentlyUsing;
        for (Plugin plugin : list) {
            if (list.size() != i) {
                stringBuilder.append(TextUtils.colorize("&a")).append(plugin.getName()).append(TextUtils.colorize("&f, "));
                i++;
            }
            else { stringBuilder.append(TextUtils.colorize("&a")).append(plugin.getName()).append(TextUtils.colorize("&f.")); }
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        sender.sendMessage("&f--------< &6Lantern&fCore &f>--------\n&7Author: &fmqchinee\n&7Version: &f"+ LanternCore.get().getDescription().getVersion() + "\n&7API version: &f"+ LanternCore.get().getDescription().getAPIVersion() + "\n&7Currently used by these plugins: \n&8&l- "+ get());
        return true;
    }

}
