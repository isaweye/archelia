package uk.mqchinee.lanterncore.commands;

import lombok.Getter;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;
import uk.mqchinee.lanterncore.annotations.CommandInfo;
import uk.mqchinee.lanterncore.annotations.throwable.CommandInfoException;
import uk.mqchinee.lanterncore.enums.SenderFilter;
import uk.mqchinee.lanterncore.utils.TextUtils;

import java.util.*;

public abstract class AbstractCommand implements CommandExecutor, TabCompleter {

    @Getter Map<String, SubCommand> subCommands;
    @Getter CommandInfo info;

    public AbstractCommand(String command, JavaPlugin plugin) {
        PluginCommand pluginCommand = plugin.getCommand(command);
        if(pluginCommand != null) {
            pluginCommand.setExecutor(this);
            pluginCommand.setTabCompleter(this);
        }
        this.subCommands = new HashMap<>();
        if (this.getClass().isAnnotationPresent(CommandInfo.class)) {
            this.info = this.getClass().getAnnotation(CommandInfo.class);
        } else { throw new CommandInfoException(); }
    }

    public abstract List<String> complete(CommandSender sender, String[] args);
    public abstract void sub(CommandSender sender, String[] args);

    public abstract void execute(CommandSender sender, String label, String[] args);

    public void subCommand(SubCommand subCommand) {
        this.subCommands.put(subCommand.getName(), subCommand);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sub(sender, args);
        if(!(getInfo().filter() == SenderFilter.BOTH)) {
            if (getInfo().filter() == SenderFilter.CONSOLE) {
                if (sender instanceof Player) {
                    sender.sendMessage(TextUtils.colorize(getInfo().filter_message()));
                    return true;
                }
            } else {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(TextUtils.colorize(getInfo().filter_message()));
                    return true;
                }
            }
        }

        if (!isNull(getInfo().permission()) && (!sender.hasPermission(getInfo().permission()))) {
            sender.sendMessage(TextUtils.colorize(info.permission_message()));
            return true;
        }

        if (args.length < 1 || (subCommands.get(args[0]) == null)) {
            execute(sender, label, args);
            return true;
        }
        subCommands.get(args[0]).prepare();
        return true;
    }

    private boolean isNull(String string) {
        return (Objects.equals(string, ""));
    }

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