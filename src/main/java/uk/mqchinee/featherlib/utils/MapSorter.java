package uk.mqchinee.featherlib.utils;

import org.bukkit.util.Consumer;
import uk.mqchinee.featherlib.enums.ComparingBy;

import java.util.Comparator;
import java.util.Map;

public class MapSorter {

    public void sort(Map map, int skip, int last, ComparingBy comparingBy, Comparator comparator, Consumer<Map.Entry> entry) {
        RunUtils.async(() -> {
            switch (comparingBy) {
                case KEY:
                    map.entrySet()
                            .stream()
                            .sorted(Map.Entry.comparingByKey(comparator))
                            .skip(skip)
                            .limit(last)
                            .forEach(a -> entry.accept((Map.Entry) a));
                case VALUE:
                    map.entrySet()
                            .stream()
                            .sorted(Map.Entry.comparingByValue(comparator))
                            .skip(skip)
                            .limit(last)
                            .forEach(a -> entry.accept((Map.Entry) a));
            }
        });
    }

    public void sort(Map map, int skip, int last, ComparingBy comparingBy, Consumer<Map.Entry> entry) {
        RunUtils.async(() -> {
            switch (comparingBy) {
                case KEY:
                    map.entrySet()
                            .stream()
                            .sorted(Map.Entry.comparingByKey())
                            .skip(skip)
                            .limit(last)
                            .forEach(a -> entry.accept((Map.Entry) a));
                case VALUE:
                    map.entrySet()
                            .stream()
                            .sorted(Map.Entry.comparingByValue())
                            .skip(skip)
                            .limit(last)
                            .forEach(a -> entry.accept((Map.Entry) a));
            }
        });
    }

}
