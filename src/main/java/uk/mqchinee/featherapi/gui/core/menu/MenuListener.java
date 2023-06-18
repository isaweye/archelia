package uk.mqchinee.featherapi.gui.core.menu;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import uk.mqchinee.featherapi.gui.core.buttons.Button;
import uk.mqchinee.featherapi.gui.core.toolbar.ToolbarBuilder;
import uk.mqchinee.featherapi.gui.core.toolbar.ToolbarButtonType;
import uk.mqchinee.featherapi.managers.GUIManager;

public class MenuListener implements Listener {

    private final JavaPlugin owner;
    private final GUIManager guiManager;

    public MenuListener(JavaPlugin owner, GUIManager guiManager) {
        this.owner = owner;
        this.guiManager = guiManager;
    }

    private static boolean shouldIgnoreInventoryEvent(Inventory inventory) {
        return !(inventory != null &&
                 inventory.getHolder() != null &&
                 inventory.getHolder() instanceof Menu);
    }

    public static boolean willHandleInventoryEvent(JavaPlugin plugin, Inventory inventory) {
        return !shouldIgnoreInventoryEvent(inventory) && ((Menu) inventory.getHolder()).getOwner().equals(plugin);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        try {
            if (shouldIgnoreInventoryEvent(event.getClickedInventory())) return;

            Menu clickedGui = (Menu) event.getClickedInventory().getHolder();

            if (!clickedGui.getOwner().equals(owner)) return;

            if (clickedGui.areDefaultInteractionsBlocked() != null) {
                event.setCancelled(clickedGui.areDefaultInteractionsBlocked());
            } else {
                if (guiManager.areDefaultInteractionsBlocked())
                    event.setCancelled(true);
            }

            if (event.getSlot() > clickedGui.getPageSize()) {
                int offset = event.getSlot() - clickedGui.getPageSize();
                ToolbarBuilder paginationButtonBuilder = guiManager.getDefaultToolbarBuilder();

                if (clickedGui.getToolbarBuilder() != null) {
                    paginationButtonBuilder = clickedGui.getToolbarBuilder();
                }

                ToolbarButtonType buttonType = ToolbarButtonType.getDefaultForSlot(offset);
                Button paginationButton = paginationButtonBuilder.buildToolbarButton(offset, clickedGui.getCurrentPage(), buttonType, clickedGui);
                if (paginationButton != null) paginationButton.getListener().onClick(event);
                return;
            }


            Button button = clickedGui.getButton(clickedGui.getCurrentPage(), event.getSlot());
            if (button != null && button.getListener() != null) {
                button.getListener().onClick(event);
            }
         } catch (Exception ignored) {}

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryDrag(InventoryDragEvent event) {
        if (shouldIgnoreInventoryEvent(event.getInventory())) return;

        event.setCancelled(true);

    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {

        if (shouldIgnoreInventoryEvent(event.getInventory())) return;

        Menu clickedGui = (Menu) event.getInventory().getHolder();

        if (!clickedGui.getOwner().equals(owner)) return;

        if (clickedGui.getOnClose() != null)
            clickedGui.getOnClose().accept(clickedGui);
    }

}
