package uk.mqchinee.featherlib.gui.inventory;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;
import uk.mqchinee.featherlib.gui.icons.Icon;
import uk.mqchinee.featherlib.utils.Experiments;

public class GUIListener implements Listener {

    private boolean isGUI(InventoryHolder holder) {
        return holder instanceof GUI;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getClickedInventory().getHolder();
        if(isGUI(holder)) {
            GUI clickedGui = (GUI) holder;
            if (clickedGui.shouldCancelEvents()) { event.setCancelled(true); }
            Experiments.ignore(() -> {
                Icon icon = clickedGui.getIcon(clickedGui.getCurrentPage(), event.getSlot());
                switch (event.getClick()) {
                    case LEFT -> icon.getLeftClick().onClick(event);
                    case RIGHT -> icon.getRightClick().onClick(event);
                    case DROP -> icon.getDropClick().onClick(event);
                    case DOUBLE_CLICK -> icon.getDoubleClick().onClick(event);
                    case MIDDLE -> icon.getMiddleClick().onClick(event);
                    case SHIFT_LEFT -> icon.getShiftLeftClick().onClick(event);
                    case SHIFT_RIGHT -> icon.getShiftRightClick().onClick(event);
                }
            });
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if(isGUI(holder)) {
            GUI openedGui = (GUI) holder;
            if (openedGui.shouldCancelEvents()) { event.setCancelled(true); }
            Experiments.ignore(() -> openedGui.getOnOpen().onOpen(event));

        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if(isGUI(holder)) {
            GUI closedGui = (GUI) holder;
            Experiments.ignore(() -> closedGui.getOnClose().onClose(event));
        }
    }

}
