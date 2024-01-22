package uk.mqchinee.archelia.commands;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class SubcommandManager {

    @Getter
    private static final Map<String, AbstractArcheliaSubcommand> subcommands = new HashMap<>();

    public static void registerSubcommand(AbstractArcheliaSubcommand subcommand) {
        if (subcommands.containsKey(subcommand.getName())) {
            subcommands.replace(subcommand.getName(), subcommand);
            return;
        }
        subcommands.put(subcommand.getName(), subcommand);
    }

    public static void registerSubcommands(AbstractArcheliaSubcommand... subcommand) {
        for (AbstractArcheliaSubcommand sbc : subcommand) {
            registerSubcommand(sbc);
        }
    }

}
