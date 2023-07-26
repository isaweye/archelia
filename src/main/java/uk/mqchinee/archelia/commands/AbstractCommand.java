package uk.mqchinee.archelia.commands;

import lombok.Getter;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;
import uk.mqchinee.archelia.Archelia;
import uk.mqchinee.archelia.annotations.CommandInfo;
import uk.mqchinee.archelia.annotations.Subcommand;
import uk.mqchinee.archelia.annotations.SubcommandContainer;
import uk.mqchinee.archelia.annotations.throwable.CommandInfoException;
import uk.mqchinee.archelia.enums.SenderFilter;
import uk.mqchinee.archelia.utils.TextUtils;

import java.util.*;

/**
 * An abstract base class for defining custom commands in a Bukkit plugin.
 */
public abstract class AbstractCommand implements CommandExecutor, TabCompleter {

    @Getter
    private final Map<String, AbstractSubcommand> subcommands;
    @Getter
    private final CommandInfo info;

    /**
     * Constructor for the AbstractCommand class.
     *
     * @param command The name of the command.
     * @param plugin  The JavaPlugin instance that registers the command.
     */
    public AbstractCommand(String command, JavaPlugin plugin) {
        PluginCommand pluginCommand = plugin.getCommand(command);
        if (pluginCommand != null) {
            pluginCommand.setExecutor(this);
            pluginCommand.setTabCompleter(this);
        }
        this.subcommands = new HashMap<>();
        if (this.getClass().isAnnotationPresent(CommandInfo.class)) {
            this.info = this.getClass().getAnnotation(CommandInfo.class);
        } else {
            throw new CommandInfoException("CommandInfo annotation is missing for the command.");
        }

        if (this.getClass().isAnnotationPresent(SubcommandContainer.class)) {
           List<Subcommand> n = List.of(this.getClass().getAnnotation(SubcommandContainer.class).value());
           n.forEach((name) -> subcommand(Archelia.getSubcommands().get(name.value())));
        }

    }

    /**
     * Complete method for tab completion of the command.
     *
     * @param sender The CommandSender who is tab-completing the command.
     * @param args   The arguments passed to the command.
     * @return A list of possible tab completions for the command.
     */
    public abstract List<String> complete(CommandSender sender, String[] args);

    /**
     * Main command execution method.
     *
     * @param sender The CommandSender who issued the command.
     * @param label  The label of the command.
     * @param args   The arguments passed for the command.
     */
    public abstract void execute(CommandSender sender, String label, String[] args);

    /**
     * Adds a subcommand to the command.
     *
     * @param subcommand The subcommand to be added.
     */
    public void subcommand(AbstractSubcommand subcommand) {
        this.subcommands.put(subcommand.getName(), subcommand);
    }

    private void registerSubcommands(CommandSender sender, String[] args) {
        getSubcommands().forEach((name, clazz) -> clazz.register(sender, args));
    }

    /**
     * Handles the main command and its subcommands.
     *
     * @param sender  The CommandSender who issued the command.
     * @param command The command being executed.
     * @param label   The label of the command.
     * @param args    The arguments passed for the command.
     * @return true if the command was handled successfully, false otherwise.
     */
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        registerSubcommands(sender, args);
        if (!(getInfo().filter() == SenderFilter.BOTH)) {
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

        if (args.length < 1 || (subcommands.get(args[0]) == null)) {
            execute(sender, label, args);
            return true;
        }
        subcommands.get(args[0]).prepare();
        return true;
    }

    /**
     * Checks if a String is null or empty.
     *
     * @param string The String to check.
     * @return true if the String is null or empty, false otherwise.
     */
    private boolean isNull(String string) {
        return (Objects.equals(string, ""));
    }

    /**
     * Handles tab completion for the command.
     *
     * @param sender  The CommandSender who is tab-completing the command.
     * @param command The command being tab-completed.
     * @param alias   The alias of the command.
     * @param args    The arguments passed for tab completion.
     * @return A list of possible tab completions for the command.
     */
    @Override
    public @Nullable List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return filter(complete(sender, args), args);
    }

    /**
     * Filters a list of tab completions based on the last argument.
     *
     * @param list The list of possible completions.
     * @param args The arguments passed for tab completion.
     * @return A filtered list of possible tab completions.
     */
    private List<String> filter(List<String> list, String[] args) {
        if (list == null) return null;
        String last = args[args.length - 1];
        List<String> result = new ArrayList<>();
        for (String arg : list) {
            if (arg.toLowerCase().startsWith(last.toLowerCase())) result.add(arg);
        }
        return result;
    }
}
