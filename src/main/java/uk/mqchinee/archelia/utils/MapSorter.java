package uk.mqchinee.archelia.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Consumer;
import uk.mqchinee.archelia.enums.ComparingBy;

import java.util.Comparator;
import java.util.Map;

/**
 * Utility class for sorting a Map based on keys or values.
 */
public class MapSorter {

    /**
     * Sorts the given Map based on keys or values and performs the specified action on the sorted entries.
     *
     * @param map         The Map to be sorted.
     * @param skip        The number of entries to skip from the beginning of the sorted result.
     * @param limit       The maximum number of entries to include in the sorted result.
     * @param comparingBy The Enum value indicating whether to sort by keys or values.
     * @param comparator  The comparator to use for custom sorting of keys or values (optional).
     * @param entry       The action to be performed on each sorted Map.Entry.
     */
    public void sort(Map map, int skip, int limit, ComparingBy comparingBy, Comparator comparator, Consumer<Map.Entry> entry, Plugin plugin) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            switch (comparingBy) {
                case KEY:
                    map.entrySet()
                            .stream()
                            .sorted(Map.Entry.comparingByKey(comparator))
                            .skip(skip)
                            .limit(limit)
                            .forEach(a -> entry.accept((Map.Entry) a));
                    break;
                case VALUE:
                    map.entrySet()
                            .stream()
                            .sorted(Map.Entry.comparingByValue(comparator))
                            .skip(skip)
                            .limit(limit)
                            .forEach(a -> entry.accept((Map.Entry) a));
                    break;
            }
        });
    }

    /**
     * Sorts the given Map based on keys or values and performs the specified action on the sorted entries.
     * Uses the default natural ordering for sorting keys or values.
     *
     * @param map         The Map to be sorted.
     * @param skip        The number of entries to skip from the beginning of the sorted result.
     * @param limit       The maximum number of entries to include in the sorted result.
     * @param comparingBy The Enum value indicating whether to sort by keys or values.
     * @param entry       The action to be performed on each sorted Map.Entry.
     */
    public void sort(Map map, int skip, int limit, ComparingBy comparingBy, Consumer<Map.Entry> entry, Plugin plugin) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            switch (comparingBy) {
                case KEY:
                    map.entrySet()
                            .stream()
                            .sorted(Map.Entry.comparingByKey())
                            .skip(skip)
                            .limit(limit)
                            .forEach(a -> entry.accept((Map.Entry) a));
                    break;
                case VALUE:
                    map.entrySet()
                            .stream()
                            .sorted(Map.Entry.comparingByValue())
                            .skip(skip)
                            .limit(limit)
                            .forEach(a -> entry.accept((Map.Entry) a));
                    break;
            }
        });
    }
}
