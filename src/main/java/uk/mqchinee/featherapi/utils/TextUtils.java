package uk.mqchinee.featherapi.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class TextUtils {

    public void console(String... message) {
        for(String msg : message) {
            Bukkit.getConsoleSender().sendMessage(colorize(msg));
        }
    }
    public String colorize(String message) {
        return message.replace("&", "§");
    }

    public List<String> fromString(String data) {
        data = data.replace("[","").replace("]","");
        return Arrays.asList(data.split(",",-1));
    }

    public String fromList(List<String> list) {
        StringBuilder send = new StringBuilder("");
        for(Object message : list) {
            String msg = message.toString();
            send.append(msg+"\n");
        }
        return String.valueOf(send);
    }

    public void send(String message) {
        console(message);
        sendToOps(message);
    }

    public void sendToOps(String message) {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (player.isOp()) {
                player.sendMessage(colorize(message));
            }
        }
    }

    public String fromListColorized(List<String> list) {
        StringBuilder send = new StringBuilder("");
        for(Object message : list) {
            String msg = message.toString();
            send.append(colorize(msg)+"\n");
        }
        return String.valueOf(send);
    }

}
