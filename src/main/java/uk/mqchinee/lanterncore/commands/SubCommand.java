package uk.mqchinee.lanterncore.commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uk.mqchinee.lanterncore.annotations.SubCommandInfo;
import uk.mqchinee.lanterncore.annotations.throwable.CommandInfoException;
import uk.mqchinee.lanterncore.enums.SenderFilter;
import uk.mqchinee.lanterncore.utils.TextUtils;

import java.util.Objects;

public abstract class SubCommand {

    @Getter AbstractCommand parent;
    @Getter CommandSender sender;
    @Getter String[] args;
    @Getter SubCommandInfo info;
    private final boolean hasArgs;
    public SubCommand(AbstractCommand parent, CommandSender sender, String[] args, boolean hasArgs) {
        this.parent = parent;
        this.sender = sender;
        this.args = args;
        this.hasArgs = hasArgs;

        if (this.getClass().isAnnotationPresent(SubCommandInfo.class)) {
            this.info = this.getClass().getAnnotation(SubCommandInfo.class);
        } else { throw new CommandInfoException(); }
    }

    public abstract String getName();

    private boolean isNull(String string) {
        return (Objects.equals(string, ""));
    }

    public void prepare() {
        if(!(getInfo().filter() == SenderFilter.BOTH)) {
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

        if(hasArgs && (getArgs().length < 2)) {
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
    public abstract void execute();
}
