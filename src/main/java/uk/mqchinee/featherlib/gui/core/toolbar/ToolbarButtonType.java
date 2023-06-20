package uk.mqchinee.featherlib.gui.core.toolbar;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ToolbarButtonType {

    PREV_BUTTON,
    NEXT_BUTTON,
    PANE,
    CLOSE_BUTTON,
    UNASSIGNED;

    private static final Map<Integer, ToolbarButtonType> DEFAULT_MAPPINGS = Stream.of(
        new AbstractMap.SimpleImmutableEntry<>(1, PREV_BUTTON),
        new AbstractMap.SimpleImmutableEntry<>(4, CLOSE_BUTTON),
        new AbstractMap.SimpleImmutableEntry<>(7, NEXT_BUTTON),
        new AbstractMap.SimpleImmutableEntry<>(0, PANE),
        new AbstractMap.SimpleImmutableEntry<>(2, PANE),
        new AbstractMap.SimpleImmutableEntry<>(3, PANE),
        new AbstractMap.SimpleImmutableEntry<>(5, PANE),
        new AbstractMap.SimpleImmutableEntry<>(6, PANE),
        new AbstractMap.SimpleImmutableEntry<>(8, PANE)
    ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));


    public static ToolbarButtonType getDefaultForSlot(int slot) {
        return DEFAULT_MAPPINGS.getOrDefault(slot, ToolbarButtonType.UNASSIGNED);
    }

}
