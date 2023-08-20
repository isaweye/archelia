package uk.mqchinee.archelia.commands;

import lombok.Getter;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;
import uk.mqchinee.archelia.enums.SenderFilter;
import uk.mqchinee.archelia.utils.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class ArcheliaCommand implements CommandExecutor, TabCompleter {

    @Getter
    private final Map<String, ArcheliaSubcommand> subcommands;

    private BiConsumer<CommandSender, String[]> consumer = null;
    private List<String> complete = null;
    private SenderFilter send_filter = SenderFilter.BOTH;
    private String send_filter_message = null;
    private String permission = null;
    private String permission_message = null;

    private final String command;
    private final JavaPlugin plugin;

    private ArcheliaCommand(String command, JavaPlugin plugin) {
        this.command = command;
        this.plugin = plugin;
        this.subcommands = new HashMap<>();
    }

    /**
     * Creates an instance of ArcheliaCommand.
     *
     * @param command The main command label.
     * @param plugin The JavaPlugin instance.
     * @return An instance of ArcheliaCommand.
     */
    public static ArcheliaCommand create(String command, JavaPlugin plugin) {
        return new ArcheliaCommand(command, plugin);
    }

    /**
     * Registers the command executor and tab completer with Bukkit's plugin manager.
     */
    public void register() {
        PluginCommand pluginCommand = this.plugin.getCommand(this.command);
        if (pluginCommand != null) {
            pluginCommand.setExecutor(this);
            pluginCommand.setTabCompleter(this);
        }
    }

    /**
     * Sets the list of tab completion suggestions for the command.
     *
     * @param list The list of tab completion suggestions.
     * @return This ArcheliaCommand instance.
     */
    public ArcheliaCommand tabComplete(List<String> list) {
        this.complete = list;
        return this;
    }

    /**
     * Sets the consumer that will execute when the command is invoked.
     *
     * @param executes The consumer that will execute the command.
     * @return This ArcheliaCommand instance.
     */
    public ArcheliaCommand executes(BiConsumer<CommandSender, String[]> executes) {
        this.consumer = executes;
        return this;
    }

    /**
     * Sets the subcommands associated with this command.
     *
     * @param subcommand The list of subcommands to associate with this command.
     * @return This ArcheliaCommand instance.
     */
    public ArcheliaCommand subcommands(ArcheliaSubcommand... subcommand) {
        for (ArcheliaSubcommand a : subcommand) {
            this.subcommands.put(a.getName(), a);
        }
        return this;
    }

    /**
     * Sets the sender filter for the command.
     *
     * @param filter The sender filter for the command.
     * @return This ArcheliaCommand instance.
     */
    public ArcheliaCommand sendFilter(SenderFilter filter) {
        this.send_filter = filter;
        return this;
    }

    /**
     * Sets the message to send if the sender filter condition is not met.
     *
     * @param sendFilterMessage The message to send if the sender filter condition is not met.
     * @return This ArcheliaCommand instance.
     */
    public ArcheliaCommand sendFilterMessage(String sendFilterMessage) {
        this.send_filter_message = sendFilterMessage;
        return this;
    }

    /**
     * Sets the required permission for the command.
     *
     * @param permission The required permission for the command.
     * @return This ArcheliaCommand instance.
     */
    public ArcheliaCommand permission(String permission) {
        this.permission = permission;
        return this;
    }

    /**
     * Sets the message to send if the sender lacks the required permission.
     *
     * @param permissionMessage The message to send if the sender lacks the required permission.
     * @return This ArcheliaCommand instance.
     */
    public ArcheliaCommand permissionMessage(String permissionMessage) {
        this.permission_message = permissionMessage;
        return this;
    }

    private void registerSubcommands(CommandSender sender, String[] args) {
        getSubcommands().forEach((name, clazz) -> clazz.register(sender, args));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        registerSubcommands(sender, args);

        if (!(send_filter == SenderFilter.BOTH)) {
            if (send_filter == SenderFilter.CONSOLE) {
                if (sender instanceof Player) {
                    sender.sendMessage(TextUtils.colorize(send_filter_message));
                    return true;
                }
            } else {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(TextUtils.colorize(send_filter_message));
                    return true;
                }
            }
        }

        if (!isNull(permission) && (!sender.hasPermission(permission))) {
            sender.sendMessage(TextUtils.colorize(permission_message));
            return true;
        }

        if (args.length < 1 || (subcommands.get(args[0]) == null)) {
            this.consumer.accept(sender, args);
            return true;
        }

        subcommands.get(args[0]).prepare();
        return true;
    }

    private boolean isNull(String string) {
        return (string == null);
    }

    @Override
    public @Nullable List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return filter(this.complete, args);
    }

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
