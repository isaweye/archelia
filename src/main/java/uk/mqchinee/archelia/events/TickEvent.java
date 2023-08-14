package uk.mqchinee.archelia.events;

import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class TickEvent extends Event {
    @Getter
    private static final HandlerList list = new HandlerList();


    public static HandlerList getHandlerList() {
        return getList();
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return getList();
    }
}
