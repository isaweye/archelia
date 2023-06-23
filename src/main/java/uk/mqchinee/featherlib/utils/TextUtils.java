package uk.mqchinee.featherlib.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class TextUtils {

    public static void console(String... message) {
        for(String msg : message) {
            Bukkit.getConsoleSender().sendMessage(colorize(msg));
        }
    }

    public static String colorize(String message) {
        return message.replace("&", "§");
    }

    public static List<String> fromString(String data) {
        data = data.replace("[","").replace("]","");
        return Arrays.asList(data.split(",",-1));
    }

    public static String fromList(List<String> list) {
        StringBuilder send = new StringBuilder("");
        for(Object message : list) {
            String msg = message.toString();
            send.append(msg+"\n");
        }
        return String.valueOf(send);
    }

    public static void send(String message) {
        console(message);
        sendToOps(message);
    }

    public static void sendToOps(String message) {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (player.isOp()) {
                player.sendMessage(colorize(message));
            }
        }
    }

    public static String fromListColorized(List<String> list) {
        StringBuilder send = new StringBuilder("");
        for(Object message : list) {
            String msg = message.toString();
            send.append(colorize(msg)+"\n");
        }
        return String.valueOf(send);
    }

}
