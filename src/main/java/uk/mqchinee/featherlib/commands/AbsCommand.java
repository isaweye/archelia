package uk.mqchinee.featherlib.commands;

import org.bukkit.command.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class AbsCommand implements CommandExecutor, TabCompleter {

    public AbsCommand(String command, JavaPlugin plugin) {
        PluginCommand pluginCommand = plugin.getCommand(command);
        if(pluginCommand != null) {
            pluginCommand.setExecutor(this);
            pluginCommand.setTabCompleter(this);
        }
    }

    public List<String> complete(CommandSender sender, String[] args) {
        return null;
    }

    public abstract void execute(CommandSender sender, String label, String[] args);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        execute(sender, label, args);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return filter(complete(sender, args), args);
    }

    private List<String> filter(List<String> list, String[] args) {
        if(list == null) return null;
        String last = args[args.length - 1];
        List<String> result = new ArrayList<>();
        for(String arg : list) {
            if(arg.toLowerCase().startsWith(last.toLowerCase())) result.add(arg);
        }
        return result;
    }
}