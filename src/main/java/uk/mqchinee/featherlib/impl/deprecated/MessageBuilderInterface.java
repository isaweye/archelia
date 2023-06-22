package uk.mqchinee.featherlib.impl.deprecated;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import uk.mqchinee.featherlib.builders.deprecated.MessageBuilder;

@Deprecated
public interface MessageBuilderInterface {
    MessageBuilder placeholders(Player player);

    MessageBuilder process();

    MessageBuilder remove();

    MessageBuilder xml();

    String build();

    TextComponent buildComponent();
}
