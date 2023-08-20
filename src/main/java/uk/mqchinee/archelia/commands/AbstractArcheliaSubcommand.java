package uk.mqchinee.archelia.commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uk.mqchinee.archelia.annotations.SubcommandInfo;
import uk.mqchinee.archelia.annotations.throwable.CommandInfoException;
import uk.mqchinee.archelia.enums.SenderFilter;
import uk.mqchinee.archelia.utils.TextUtils;

import java.util.Objects;

/**
 * An abstract base class for defining subcommands within a Bukkit plugin's command structure.
 * @since 1.0
 */
public abstract class AbstractArcheliaSubcommand {

    @Getter
    private CommandSender sender;
    @Getter
    private String[] args;
    @Getter
    private final SubcommandInfo info;
    private final boolean hasArgs;

    public AbstractArcheliaSubcommand(boolean hasArgs) {
        this.hasArgs = hasArgs;

        if (this.getClass().isAnnotationPresent(SubcommandInfo.class)) {
            this.info = this.getClass().getAnnotation(SubcommandInfo.class);
        } else {
            throw new CommandInfoException("SubCommandInfo annotation is missing for the subcommand.");
        }
    }

    public void register(CommandSender sender, String[] args) {
        this.sender = sender; this.args = args;
    }

    /**
     * Gets the name of the subcommand.
     *
     * @return The name of the subcommand.
     */
    public abstract String getName();

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
     * Prepares the subcommand for execution based on the provided information.
     */
    public void prepare() {
        if (!(getInfo().filter() == SenderFilter.BOTH)) {
            if (getInfo().filter() == SenderFilter.CONSOLE) {
                if (sender instanceof Player) {
                    sender.sendMessage(TextUtils.colorize(getInfo().filter_message()));
                    return;
                }
            } else {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(TextUtils.colorize(getInfo().filter_message()));
                    return;
                }
            }
        }

        if (!isNull(getInfo().permission()) && (!getSender().hasPermission(getInfo().permission()))) {
            sender.sendMessage(TextUtils.colorize(info.permission_message()));
            return;
        }

        if (hasArgs && (getArgs().length < 2)) {
            sender.sendMessage(TextUtils.colorize(getInfo().no_args_message()));
            return;
        }

        if (!isNull(getInfo().regex()) && hasArgs) {
            if (!getArgs()[1].matches(getInfo().regex())) {
                sender.sendMessage(TextUtils.colorize(getInfo().regex_message()));
                return;
            }
        }
        execute();
    }

    /**
     * Executes the subcommand.
     */
    public abstract void execute();
}
