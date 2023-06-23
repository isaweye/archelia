package uk.mqchinee.featherlib.gui.inventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;
import uk.mqchinee.featherlib.gui.icons.Icon;
import uk.mqchinee.featherlib.gui.listeners.CloseListener;
import uk.mqchinee.featherlib.gui.listeners.OpenListener;
import uk.mqchinee.featherlib.gui.page.Page;
import uk.mqchinee.featherlib.gui.toolbar.Toolbar;

import java.util.NavigableMap;
import java.util.TreeMap;

public class GUI implements InventoryHolder {

    NavigableMap<Integer, Page> icons = new TreeMap<>();
    private int currentPage = 0;
    private int pageSize;
    private int pageStart;
    private Toolbar toolbar;
    private String title;
    private Player player;
    private Inventory inventory;
    private OpenListener onOpen;
    private CloseListener onClose;
    private boolean cancelEvents;

    public GUI(int pageSize, int pageStart, Toolbar toolbar, String title, Player player, boolean cancelEvents) {
        this.pageSize = pageSize;
        this.pageStart = pageStart;
        this.toolbar = toolbar;
        this.title = title;
        this.player = player;
        addPage(0, new Page(pageStart, pageSize, 0, toolbar));
        this.inventory = getInventory();
        this.cancelEvents = cancelEvents;
    }

    public GUI(int pageSize, int pageStart, String title, Player player, boolean cancelEvents) {
        this.pageSize = pageSize;
        this.pageStart = pageStart;
        this.title = title;
        this.toolbar = null;
        this.player = player;
        addPage(0, new Page(pageStart, pageSize, 0, null));
        this.inventory = getInventory();
        this.cancelEvents = cancelEvents;
    }

    public boolean shouldCancelEvents() {
        return cancelEvents;
    }

    public OpenListener getOnOpen() {
        return onOpen;
    }

    public CloseListener getOnClose() {
        return onClose;
    }

    public void onClose(CloseListener listener) {
        this.onClose = listener;
    }

    public void onOpen(OpenListener listener) {
        this.onOpen = listener;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public Icon getIcon(int page, int slot) {
        return getPage(page).getIcon(slot);
    }

    public void setCurrentPage(int page) {
        this.currentPage = page;
    }

    public Integer getLastPage() {
        return icons.lastKey();
    }

    public Page addPage(Page page) {
        icons.put(getLastPage()+1, page);
        return page;
    }

    public Page addPage(int number, Page page) {
        icons.put(number, page);
        return page;
    }

    public Page getPage(int number) {
        return icons.get(number);
    }

    public void open(int page_number, Player player) {
        Page page = getPage(page_number);
        page.getIcons().forEach((integer, icon) -> inventory.setItem(integer, icon.getItem()));
        player.openInventory(inventory);
    }

    @Override
    public @NotNull Inventory getInventory() {
        Inventory inv = Bukkit.createInventory(player, pageSize, title);
        return inv;
    }
}
