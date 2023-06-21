package uk.mqchinee.featherlib.builders;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
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

import java.util.Collection;
import java.util.Stack;

public class MessageBuilder {

    private String message;
    private TextComponent component;

    public void transform(Node node, TextComponent component) {
        switch (node.nodeName()) {
            case "k":
                component.setObfuscated(Boolean.valueOf(true));
                break;
            case "b":
                component.setBold(Boolean.valueOf(true));
                break;
            case "s":
                component.setStrikethrough(Boolean.valueOf(true));
                break;
            case "u":
                component.setUnderlined(Boolean.valueOf(true));
                break;
            case "i":
                component.setItalic(Boolean.valueOf(true));
                break;
            case "x0":
            case "black":
                component.setColor(ChatColor.BLACK);
                break;
            case "x1":
            case "darkblue":
                component.setColor(ChatColor.DARK_BLUE);
                break;
            case "x2":
            case "darkgreen":
                component.setColor(ChatColor.DARK_GREEN);
                break;
            case "x3":
            case "darkaqua":
                component.setColor(ChatColor.DARK_AQUA);
                break;
            case "x4":
            case "darkred":
                component.setColor(ChatColor.DARK_RED);
                break;
            case "x5":
            case "darkpurple":
                component.setColor(ChatColor.DARK_PURPLE);
                break;
            case "x6":
            case "gold":
                component.setColor(ChatColor.GOLD);
                break;
            case "x7":
            case "gray":
                component.setColor(ChatColor.GRAY);
                break;
            case "x8":
            case "darkgray":
                component.setColor(ChatColor.DARK_GRAY);
                break;
            case "x9":
            case "blue":
                component.setColor(ChatColor.BLUE);
                break;
            case "xa":
            case "green":
                component.setColor(ChatColor.GREEN);
                break;
            case "xb":
            case "aqua":
                component.setColor(ChatColor.AQUA);
                break;
            case "xc":
            case "red":
                component.setColor(ChatColor.RED);
                break;
            case "xd":
            case "lightpurple":
                component.setColor(ChatColor.LIGHT_PURPLE);
                break;
            case "xe":
            case "yellow":
                component.setColor(ChatColor.YELLOW);
                break;
            case "xf":
            case "white":
                component.setColor(ChatColor.WHITE);
                break;
            default:
                if (node instanceof TextNode) {
                    TextNode text = (TextNode)node;
                    component.setText(text.getWholeText());
                }
                break;
        }
        if (node.hasAttr("onclick"))
            component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, node.attr("onclick")));
        if (node.hasAttr("onhover"))
            component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[] { (BaseComponent)parseXML(node.attr("onhover")) }));
    }

    public TextComponent parseXML(String xml) {
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
                            base.addExtra((BaseComponent)leaf);
                        }
                    } else {
                        base.addExtra((BaseComponent)leaf);
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

    public MessageBuilder placeholders(Player player) {
        this.message = PlaceholderAPI.setPlaceholders(player, message);
        return this;
    }

    public MessageBuilder process() {
        this.message = Iridium.process(message);
        return this;
    }

    public MessageBuilder html() {
        component = parseXML(message);
        return this;
    }

    public String getMessage() {
        return message;
    }

    public TextComponent getComponent() {
        return component;
    }
}
