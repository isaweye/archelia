package uk.mqchinee.archelia.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

/**
 * Utility class for handling text and colors in Bukkit.
 */
public class TextUtils {

    /**
     * Send messages to the console, with color support.
     *
     * @param message The messages to be sent to the console.
     */
    public static void console(String... message) {
        for (String msg : message) {
            Bukkit.getConsoleSender().sendMessage(colorize(msg));
        }
    }

    /**
     * Colorize a string by replacing color codes with their corresponding color.
     *
     * @param message The string to be colorized.
     * @return The colorized string.
     */
    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Convert a comma-separated string to a list of strings.
     *
     * @param data The comma-separated string.
     * @return The list of strings.
     */
    public static List<String> fromString(String data) {
        data = data.replace("[", "").replace("]", "");
        return Arrays.asList(data.split(",", -1));
    }

    /**
     * Convert a list of strings to a string.
     *
     * @param list The list of strings.
     * @return The concatenated string.
     */
    public static String fromList(List<String> list) {
        StringBuilder send = new StringBuilder();
        for (Object message : list) {
            String msg = message.toString();
            send.append(msg).append("\n");
        }
        return String.valueOf(send);
    }

    /**
     * Send a message to the console and all online operators (ops).
     *
     * @param message The message to be sent.
     */
    public static void send(String message) {
        console(message);
        sendToOps(message);
    }

    /**
     * Send a message to all online operators (ops).
     *
     * @param message The message to be sent.
     */
    public static void sendToOps(String message) {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (player.isOp()) {
                player.sendMessage(colorize(message));
            }
        }
    }

    /**
     * Convert a list of strings to a colorized string, where color codes are replaced with their corresponding colors.
     *
     * @param list The list of strings to be colorized.
     * @return The colorized string.
     */
    public static String fromListColorized(List<String> list) {
        StringBuilder send = new StringBuilder();
        for (Object message : list) {
            String msg = message.toString();
            send.append(colorize(msg)).append("\n");
        }
        return String.valueOf(send);
    }

}
