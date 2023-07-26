package uk.mqchinee.archelia.utils;

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

/**
 * Utility class for handling ItemStacks and Base64 encoding/decoding.
 */
public class ItemUtils {

    /**
     * Serialize an ItemStack to a map.
     *
     * @param item The ItemStack to serialize.
     * @return The serialized map.
     */
    public Map<String, Object> serialize(ItemStack item) {
        return item.serialize();
    }

    /**
     * Deserialize a map to an ItemStack.
     *
     * @param map The serialized map.
     * @return The deserialized ItemStack.
     */
    public ItemStack deserialize(Map<String, Object> map) {
        return ItemStack.deserialize(map);
    }

    /**
     * Get a random banner ItemStack from a predefined list of banner types.
     *
     * @return A random banner ItemStack.
     */
    public ItemStack randomBanner() {
        List<ItemStack> banners = Lists.newArrayList(
                new ItemStack(Material.BLACK_BANNER),
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
                new ItemStack(Material.LIGHT_BLUE_BANNER)
        );

        return banners.get(new Random().nextInt(banners.size()));
    }

    /**
     * Encode an array of ItemStacks to a Base64 encoded string.
     *
     * @param items The array of ItemStacks to encode.
     * @return The Base64 encoded string.
     */
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

    /**
     * Decode a Base64 encoded string to an array of ItemStacks.
     *
     * @param source The Base64 encoded string.
     * @return The array of ItemStacks.
     */
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
