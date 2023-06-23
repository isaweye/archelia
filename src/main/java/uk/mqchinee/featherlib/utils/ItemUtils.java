package uk.mqchinee.featherlib.utils;

import com.google.common.collect.Lists;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ItemUtils {

    public Map<String, Object> serialize(ItemStack item) {
        return item.serialize();
    }

    public ItemStack deserialize(Map<String, Object> map) {
        return ItemStack.deserialize(map);
    }

    public ItemStack RandomBanner() {
        List<ItemStack> i = Lists.newArrayList(new ItemStack(Material.BLACK_BANNER),
        new ItemStack(Material.WHITE_BANNER),
        new ItemStack(Material.CYAN_BANNER),
        new ItemStack(Material.BLUE_BANNER),
        new ItemStack(Material.ORANGE_BANNER),
        new ItemStack(Material.RED_BANNER),
        new ItemStack(Material.PINK_BANNER),
        new ItemStack(Material.GRAY_BANNER),
        new ItemStack(Material.BROWN_BANNER),
        new ItemStack(Material.GREEN_BANNER),
        new ItemStack(Material.LIME_BANNER),
        new ItemStack(Material.LIGHT_BLUE_BANNER));

        return i.get(new Random().nextInt(i.size()));
    }

    public String write(ItemStack... items) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream)) {

            dataOutput.writeInt(items.length);

            for (ItemStack item : items)
                dataOutput.writeObject(item);

            return Base64Coder.encodeLines(outputStream.toByteArray());

        } catch (Exception ignored) {
            return "";
        }
    }

    public ItemStack[] read(String source) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(source));
             BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream)) {

            ItemStack[] items = new ItemStack[dataInput.readInt()];

            for (int i = 0; i < items.length; i++)
                items[i] = (ItemStack) dataInput.readObject();

            return items;
        } catch (Exception ignored) {
            return new ItemStack[0];
        }
    }

}
