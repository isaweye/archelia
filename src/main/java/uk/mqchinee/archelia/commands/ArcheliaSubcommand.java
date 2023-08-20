package uk.mqchinee.archelia.commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uk.mqchinee.archelia.enums.SenderFilter;
import uk.mqchinee.archelia.utils.TextUtils;

import java.util.function.BiConsumer;

public class ArcheliaSubcommand {

    @Getter
    private CommandSender sender;
    @Getter
    private String[] args;
    @Getter
    private final boolean hasArgs;
    @Getter
    private final String name;

    private BiConsumer<CommandSender, String[]> consumer = null;
    private SenderFilter send_filter = SenderFilter.BOTH;
    private String send_filter_message = null;
    private String permission = null;
    private String permission_message = null;
    private String regex = null;
    private String regex_message = null;
    private String no_args_message = null;

    /**
     * Creates an instance of ArcheliaSubcommand.
     *
     * @param name The name of the subcommand.
     * @param hasArgs Whether the subcommand expects arguments.
     */
    public ArcheliaSubcommand(String name, boolean hasArgs) {
        this.name = name;
        this.hasArgs = hasArgs;
    }

    /**
     * Sets the consumer that will execute the subcommand logic.
     *
     * @param executes The consumer that will execute the subcommand logic.
     * @return This ArcheliaSubcommand instance.
     */
    public ArcheliaSubcommand executes(BiConsumer<CommandSender, String[]> executes) {
        this.consumer = executes;
        return this;
    }

    /**
     * Sets the sender filter for the subcommand.
     *
     * @param filter The sender filter for the subcommand.
     * @return This ArcheliaSubcommand instance.
     */
    public ArcheliaSubcommand sendFilter(SenderFilter filter) {
        this.send_filter = filter;
        return this;
    }

    /**
     * Sets the message to send if the sender filter condition is not met.
     *
     * @param sendFilterMessage The message to send if the sender filter condition is not met.
     * @return This ArcheliaSubcommand instance.
     */
    public ArcheliaSubcommand sendFilterMessage(String sendFilterMessage) {
        this.send_filter_message = sendFilterMessage;
        return this;
    }

    /**
     * Sets the required permission for the subcommand.
     *
     * @param permission The required permission for the subcommand.
     * @return This ArcheliaSubcommand instance.
     */
    public ArcheliaSubcommand permission(String permission) {
        this.permission = permission;
        return this;
    }

    /**
     * Sets the message to send if the sender lacks the required permission.
     *
     * @param permissionMessage The message to send if the sender lacks the required permission.
     * @return This ArcheliaSubcommand instance.
     */
    public ArcheliaSubcommand permissionMessage(String permissionMessage) {
        this.permission_message = permissionMessage;
        return this;
    }

    /**
     * Sets the regular expression pattern for validating subcommand arguments.
     *
     * @param regex The regular expression pattern for validating subcommand arguments.
     * @return This ArcheliaSubcommand instance.
     */
    public ArcheliaSubcommand regex(String regex) {
        this.regex = regex;
        return this;
    }

    /**
     * Sets the message to send if the argument validation fails using the regex pattern.
     *
     * @param regexMessage The message to send if the argument validation fails using the regex pattern.
     * @return This ArcheliaSubcommand instance.
     */
    public ArcheliaSubcommand regexMessage(String regexMessage) {
        this.regex_message = regexMessage;
        return this;
    }

    /**
     * Sets the message to send if the subcommand is executed without required arguments.
     *
     * @param message The message to send if the subcommand is executed without required arguments.
     * @return This ArcheliaSubcommand instance.
     */
    public ArcheliaSubcommand noArgsMessage(String message) {
        this.no_args_message = message;
        return this;
    }

    /**
     * Registers the sender and arguments for the subcommand.
     *
     * @param sender The command sender.
     * @param args The subcommand arguments.
     */
    public void register(CommandSender sender, String[] args) {
        this.sender = sender;
        this.args = args;
    }

    private boolean isNull(String string) {
        return (string == null);
    }

    /**
     * Prepares and executes the subcommand logic, performing necessary checks.
     */
    public void prepare() {
        if (!(send_filter == SenderFilter.BOTH)) {
            if (send_filter == SenderFilter.CONSOLE) {
                if (sender instanceof Player) {
                    sender.sendMessage(TextUtils.colorize(send_filter_message));
                    return;
                }
            } else {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(TextUtils.colorize(send_filter_message));
                    return;
                }
            }
        }

        if (!isNull(permission) && (!getSender().hasPermission(permission))) {
            sender.sendMessage(TextUtils.colorize(permission_message));
            return;
        }

        if (hasArgs && (getArgs().length < 2)) {
            sender.sendMessage(TextUtils.colorize(no_args_message));
            return;
        }

        if (!isNull(regex) && hasArgs) {
            if (!getArgs()[1].matches(regex)) {
                sender.sendMessage(TextUtils.colorize(regex_message));
                return;
            }
        }
        this.consumer.accept(sender, args);
    }
}
