package uk.mqchinee.featherlib.builders;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.Parser;
import org.jsoup.select.NodeVisitor;
import uk.mqchinee.featherlib.colors.Iridium;
import uk.mqchinee.featherlib.ext.MessageBuilderInterface;

import java.util.Collection;
import java.util.Stack;

public class MessageBuilder implements MessageBuilderInterface {

    private String message;
    private TextComponent component;

    private void transform(Node node, TextComponent component) {
        if (node instanceof TextNode) {
            TextNode text = (TextNode)node;
            component.setText(text.getWholeText());
        }
        if (node.hasAttr("run"))
            component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, node.attr("click")));
        if (node.hasAttr("hover"))
            component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[] { (BaseComponent)parseXML(node.attr("hover")) }));
        if (node.hasAttr("suggest")) {
            component.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, node.attr("suggest")));
        }
        if (node.hasAttr("copy")) {
            component.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, node.attr("copy")));
        }
        if (node.hasAttr("link")) {
            component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, node.attr("link")));
        }
    }

    private TextComponent parseXML(String xml) {
        Document document = Jsoup.parse(xml, "", Parser.xmlParser());
        final Stack<TextComponent> stack = new Stack<>();
        document.traverse(new NodeVisitor() {
            public void head(Node node, int depth) {
                if (depth == 0) {
                    stack.push(new TextComponent());
                } else {
                    TextComponent text = new TextComponent();
                    transform(node, text);
                    stack.push(text);
                }
            }

            public void tail(Node node, int depth) {
                if (depth > 0) {
                    TextComponent leaf = stack.pop();
                    TextComponent base = stack.peek();
                    if (node instanceof TextNode) {
                        if (base.getText().isEmpty() && isEmpty(base.getExtra())) {
                            base.setText(leaf.getText());
                        } else {
                            base.addExtra(leaf);
                        }
                    } else {
                        base.addExtra(leaf);
                    }
                }
            }
        });
        return stack.empty() ? new TextComponent() : stack.pop();
    }

    private boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    public MessageBuilder(String message) {
        this.message = message;
    }

    @Override
    public MessageBuilder placeholders(Player player) {
        this.message = PlaceholderAPI.setPlaceholders(player, message);
        return this;
    }

    @Override
    public MessageBuilder process() {
        this.message = Iridium.process(message);
        return this;
    }

    @Override
    public MessageBuilder html() {
        component = parseXML(message);
        return this;
    }

    @Override
    public TextComponent buildComponent() {
        return component;
    }

    @Override
    public String build() {
        return message;
    }
}
