package uk.mqchinee.featherapi.gui.core.menu;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;
import uk.mqchinee.featherapi.gui.core.buttons.Button;
import uk.mqchinee.featherapi.gui.core.toolbar.ToolbarBuilder;
import uk.mqchinee.featherapi.gui.core.toolbar.ToolbarButtonType;
import uk.mqchinee.featherapi.managers.GUIManager;
import uk.mqchinee.featherapi.utils.TextUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;


public class Menu implements InventoryHolder {

    private final JavaPlugin owner;
    private final GUIManager guiManager;

    private String name;
    private String tag;
    private int rowsPerPage;

    private final Map<Integer, Button> items;

    private int currentPage;
    private Boolean blockDefaultInteractions;
    private final Boolean np;

    private ToolbarBuilder toolbarBuilder;
    private Consumer<Menu> onClose;
    private Consumer<Menu> onPagePlus;
    private final TextUtils t = new TextUtils();


    public Menu(JavaPlugin owner, GUIManager guiManager, boolean needsPagination, String name, int rowsPerPage, String tag) {
        this.owner = owner;
        this.guiManager = guiManager;
        this.name = ChatColor.translateAlternateColorCodes('&', name);
        this.rowsPerPage = rowsPerPage;
        this.tag = tag;
        this.np = needsPagination;
        this.items = new HashMap<>();

        this.currentPage = 0;
    }

    /// INVENTORY SETTINGS ///


    public void setBlockDefaultInteractions(boolean blockDefaultInteractions) {
        this.blockDefaultInteractions = blockDefaultInteractions;
    }


    public Boolean areDefaultInteractionsBlocked() {
        return blockDefaultInteractions;
    }



    public void setToolbarBuilder(ToolbarBuilder toolbarBuilder) {
        this.toolbarBuilder = toolbarBuilder;
    }


    public ToolbarBuilder getToolbarBuilder() {
        return this.toolbarBuilder;
    }

    /// INVENTORY OWNER ///


    public JavaPlugin getOwner() {
        return owner;
    }

    /// INVENTORY SIZE ///


    public int getRowsPerPage() {
        return rowsPerPage;
    }


    public int getPageSize() {
        return rowsPerPage * 9;
    }

    public void setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }

    /// INVENTORY TAG ///


    public String getTag() {
        return tag;
    }


    public void setTag(String tag) {
        this.tag = tag;
    }

    /// INVENTORY NAME ///


    public void setName(String name) {
        this.name = ChatColor.translateAlternateColorCodes('&', name);
    }


    public void setRawName(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    /// BUTTONS ///


    public void addButton(Button button) {
        if (getHighestFilledSlot() == 0 && getButton(0) == null) {
            setButton(0, button);
            return;
        }

        setButton(getHighestFilledSlot() + 1, button);
    }


    public void addButtons(List<Button> buttons) {
        for (Button button : buttons) addButton(button);
    }


    public void setButton(int slot, Button button) {
        items.put(slot, button);
    }


    public void setButton(int page, int slot, Button button) {
        if (slot < 0 || slot > getPageSize())
            return;

        setButton((page * getPageSize()) + slot, button);
    }


    public void removeButton(int slot) {
        items.remove(slot);
    }


    public void removeButton(int page, int slot) {
        if (slot < 0 || slot > getPageSize())
            return;

        removeButton((page * getPageSize()) + slot);
    }


    public Button getButton(int slot) {
        if (slot < 0 || slot > getHighestFilledSlot())
            return null;

        return items.get(slot);
    }


    public Button getButton(int page, int slot) {
        if (slot < 0 || slot > getPageSize())
            return null;

        return getButton((page * getPageSize()) + slot);
    }

    /// PAGINATION ///


    public int getCurrentPage() {
        return currentPage;
    }


    public void setCurrentPage (int page) {
        this.currentPage = page;
    }

    public void setPage(int page, HumanEntity viewer) {
        this.currentPage = page;
        refreshInventory(viewer);
    }


    public int getMaxPage() {
        return (int) Math.ceil(((double) getHighestFilledSlot() + 1) / ((double) getPageSize()));
    }

    public int getHighestFilledSlot() {
        int slot = 0;

        for (int nextSlot : items.keySet()) {
            if (items.get(nextSlot) != null && nextSlot > slot)
                slot = nextSlot;
        }

        return slot;
    }

    public boolean nextPage(HumanEntity viewer) {
        if (currentPage < getMaxPage() - 1) {
            if (this.onPagePlus != null) this.onPagePlus.accept(this);
            currentPage++;
            refreshInventory(viewer);
            return true;
        } else {
            return false;
        }
    }

    public void setOnPagePlus(Consumer<Menu> onPagePlus) {
        this.onPagePlus = onPagePlus;
    }

    public boolean previousPage(HumanEntity viewer) {
        if (currentPage > 0) {
            currentPage--;
            refreshInventory(viewer);
            return true;
        } else {
            return false;
        }
    }


    /// EVENTS ///


    public Consumer<Menu> getOnClose() {
        return this.onClose;
    }

    public void setOnClose(Consumer<Menu> onClose) {
        this.onClose = onClose;
    }


    /// INVENTORY API ///

    public void refreshInventory(HumanEntity viewer) {
        if (
                !(viewer.getOpenInventory().getTopInventory().getHolder() instanceof Menu)
                || viewer.getOpenInventory().getTopInventory().getHolder() != this
        ) return;

        if (viewer.getOpenInventory().getTopInventory().getSize() != getPageSize() + (getMaxPage() > 0 ? 9 : 0)) {
            viewer.openInventory(getInventory());
            return;
        }

        String newName = name.replace("{currentPage}", String.valueOf(currentPage + 1))
                             .replace("{maxPage}", String.valueOf(getMaxPage()));
        if (!viewer.getOpenInventory().getTitle().equals(newName)) {
            viewer.openInventory(getInventory());
            return;
        }

        viewer.getOpenInventory().getTopInventory().setContents(getInventory().getContents());
    }

    @Override
    public Inventory getInventory() {

        boolean needsPagination = np;

        Inventory inventory = Bukkit.createInventory(this, (
            (needsPagination)
                ? getPageSize() + 9
                : getPageSize()
        ),
            name.replace("{currentPage}", String.valueOf(currentPage + 1))
                .replace("{maxPage}", String.valueOf(getMaxPage()))
        );

        for (int key = currentPage * getPageSize(); key < (currentPage + 1) * getPageSize(); key++) {
            if (key > getHighestFilledSlot()) break;

            if (items.containsKey(key)) {
                inventory.setItem(key - (currentPage * getPageSize()), items.get(key).getIcon());
            }
        }

        if (needsPagination) {

            ToolbarBuilder toolbarButtonBuilder = guiManager.getDefaultToolbarBuilder();
            if (getToolbarBuilder() != null) {
                toolbarButtonBuilder = getToolbarBuilder();
            }

            int pageSize = getPageSize();
            for (int i = pageSize; i < pageSize + 9; i++) {
                int offset = i - pageSize;

                Button paginationButton = toolbarButtonBuilder.buildToolbarButton(
                    offset, getCurrentPage(), ToolbarButtonType.getDefaultForSlot(offset),this
                );
                inventory.setItem(i, paginationButton != null ? paginationButton.getIcon() : null);
            }
        }

        return inventory;
    }

}
