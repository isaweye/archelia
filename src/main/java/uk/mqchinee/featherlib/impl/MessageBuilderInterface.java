package uk.mqchinee.featherlib.impl;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public interface MessageBuilderInterface {
    MessageBuilderInterface placeholders(Player player);

    MessageBuilderInterface process();

    MessageBuilderInterface html();

    TextComponent buildComponent();

    String build();
}
