package uk.mqchinee.featherlib.deprecated.builders;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

@Deprecated
public interface MessageBuilderInterface {
    MessageBuilder placeholders(Player player);

    MessageBuilder process();

    MessageBuilder remove();

    MessageBuilder xml();

    String build();

    TextComponent buildComponent();
}
