package uk.mqchinee.lanterncore.utils;

import org.bukkit.util.Consumer;
import uk.mqchinee.lanterncore.enums.ComparingBy;

import java.util.Comparator;
import java.util.Map;

public class MapSorter {

    public void sort(Map map, int skip, int limit, ComparingBy comparingBy, Comparator comparator, Consumer<Map.Entry> entry) {
        RunUtils.async(() -> {
            switch (comparingBy) {
                case KEY:
                    map.entrySet()
                            .stream()
                            .sorted(Map.Entry.comparingByKey(comparator))
                            .skip(skip)
                            .limit(limit)
                            .forEach(a -> entry.accept((Map.Entry) a));
                case VALUE:
                    map.entrySet()
                            .stream()
                            .sorted(Map.Entry.comparingByValue(comparator))
                            .skip(skip)
                            .limit(limit)
                            .forEach(a -> entry.accept((Map.Entry) a));
            }
        });
    }

    public void sort(Map map, int skip, int limit, ComparingBy comparingBy, Consumer<Map.Entry> entry) {
        RunUtils.async(() -> {
            switch (comparingBy) {
                case KEY:
                    map.entrySet()
                            .stream()
                            .sorted(Map.Entry.comparingByKey())
                            .skip(skip)
                            .limit(limit)
                            .forEach(a -> entry.accept((Map.Entry) a));
                case VALUE:
                    map.entrySet()
                            .stream()
                            .sorted(Map.Entry.comparingByValue())
                            .skip(skip)
                            .limit(limit)
                            .forEach(a -> entry.accept((Map.Entry) a));
            }
        });
    }

}
