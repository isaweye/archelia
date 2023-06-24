package uk.mqchinee.lanterncore.gui;

import com.google.common.collect.Lists;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import uk.mqchinee.lanterncore.gui.item.ClickableItem;
import uk.mqchinee.lanterncore.gui.item.MenuItem;

import java.util.*;
import java.util.function.Consumer;

public class PageableChestMenu extends ChestMenu {

    @Getter private final int[] itemSlots;
    private List<MenuItem> pageableItems;

    private int page;

    private AbstractMap.SimpleEntry<Integer, ClickableItem> nextPageItem = null;
    private AbstractMap.SimpleEntry<Integer, ClickableItem> previousPageItem = null;

    private List<PageableChestMenu> mirrorMenus = Lists.newArrayList();
    private PageableChestMenu fatherMenu;

    PageableChestMenu(String title, int rows, int[] itemSlots, JavaPlugin plugin) {
        // TODO: rewrite this
        super(title, rows, plugin);
        this.itemSlots = itemSlots;
        this.page = 0;
        this.pageableItems = Lists.newArrayList();
        this.fatherMenu = null;
    }

    private PageableChestMenu(PageableChestMenu father) {
        this(father.getTitle(), father.getRows(), father.getItemSlots(), father.plugin);
        this.items = father.items;
        this.pageableItems = father.pageableItems;

        if(father.nextPageItem != null) this.setNextPageItem(father.nextPageItem.getValue(), father.nextPageItem.getKey());
        if(father.previousPageItem != null) this.setPreviousPageItem(father.previousPageItem.getValue(), father.previousPageItem.getKey());

        this.mirrorMenus = null;
        this.fatherMenu = father;
        father.mirrorMenus.add(this);
    }

    public PageableChestMenu addPageableItem(MenuItem item, int index) {
        pageableItems.add(Math.min(index, pageableItems.size()), item);
        requireUpdate(null);
        return this;
    }

    public PageableChestMenu addPageableItem(MenuItem item) {
        return addPageableItem(item, pageableItems.size());
    }

    public PageableChestMenu removePageableItem(MenuItem item) {
        pageableItems.remove(item);
        requireUpdate(null);
        return this;
    }

    public List<MenuItem> getPageableItems() {
        return Collections.unmodifiableList(pageableItems);
    }

    public PageableChestMenu setPageableItems(List<MenuItem> items) {
        this.pageableItems.clear();
        pageableItems.addAll(items);
        this.page = 0;
        requireUpdate(null);
        return this;
    }

    public PageableChestMenu clearPageableItems() {
        this.pageableItems.clear();
        requireUpdate(null);
        return this;
    }

    public Map.Entry<Integer, Integer> getPageableItemSlot(MenuItem item) {
        if(!this.pageableItems.contains(item)) return null;

        int indexOf = pageableItems.indexOf(item);
        int page = (int) Math.floor((float) indexOf / itemSlots.length);
        int slot = itemSlots[indexOf - (page * itemSlots.length)];

        return new AbstractMap.SimpleEntry<>(page, slot);
    }

    public int getPageCount() {
        return (int) Math.max(1, Math.ceil(this.pageableItems.size() / (double) itemSlots.length));
    }

    public int getCurrentPage(Player player) {
        if(this.fatherMenu != null && this.fatherMenu.inventory != null && this.fatherMenu.inventory.getViewers().contains(player)) {
            return this.fatherMenu.page;
        } else if(this.inventory != null && this.inventory.getViewers().contains(player)) {
            return this.page;
        } else {
            Optional<PageableChestMenu> menu = mirrorMenus.stream().filter(m -> m.inventory != null && m.inventory.getViewers().contains(player)).findFirst();
            if(menu.isPresent()) return menu.get().page;
        }

        return 0;
    }

    public void nextPage() {
        Bukkit.getScheduler().runTask(this.plugin, () -> {
            if(this.page + 1 < getPageCount()) {
                this.page += 1;
                update();
            }
        });
    }

    public void previousPage() {
        Bukkit.getScheduler().runTask(this.plugin, () -> {
            if(this.page > 0) {
                this.page -= 1;
                update();
            }
        });
    }

    public void setPage(int page) {
        this.page = page;
        update();
    }

    public PageableChestMenu setNextPageItem(ClickableItem item, int slot) {
        if(slot < 0 || slot >= this.getRows() * 9) throw new IllegalArgumentException("The slot can't be less than zero or greater than the inventory size.");
        for(int slotIndex : itemSlots) {
            if(slot == slotIndex) throw new IllegalArgumentException("You can't add an item in a slot reserved for pageable items.");
        }

        if(fatherMenu == null) mirrorMenus.forEach(menu -> menu.setNextPageItem(item, slot));

        Integer oldSlot = nextPageItem != null ? nextPageItem.getKey() : null;
        nextPageItem = new AbstractMap.SimpleEntry<>(slot, (ClickableItem) item
                .setOnPrimary(click -> {
                    if(click.getWhoClicked() instanceof Player) {
                        nextPage();
                    }
                })
        );

        requireUpdate(slot);
        if(!Objects.equals(oldSlot, slot)) requireUpdate(oldSlot);

        return this;
    }

    public PageableChestMenu setPreviousPageItem(ClickableItem item, int slot) {
        if(slot < 0 || slot >= this.getRows() * 9) throw new IllegalArgumentException("The slot can't be less than zero or greater than the inventory size.");
        for(int slotIndex : itemSlots) {
            if(slot == slotIndex) throw new IllegalArgumentException("You can't add an item in a slot reserved for pageable items.");
        }

        if(fatherMenu == null) mirrorMenus.forEach(menu -> menu.setPreviousPageItem(item, slot));

        Integer oldSlot = previousPageItem != null ? previousPageItem.getKey() : null;
        previousPageItem = new AbstractMap.SimpleEntry<>(slot, (ClickableItem) item
                .setOnPrimary(click -> {
                    if(click.getWhoClicked() instanceof Player) {
                        previousPage();
                    }
                })
        );

        requireUpdate(slot);
        if(!Objects.equals(oldSlot, slot)) requireUpdate(oldSlot);

        return this;
    }

    @Override
    public ChestMenu addItem(MenuItem item, int slot) {
        for(int slotIndex : itemSlots) {
            if(slot == slotIndex) throw new IllegalArgumentException("You can't add an item in a slot reserved for pageable items.");
        }

        return super.addItem(item, slot);
    }

    @Override
    public boolean containsItem(Integer slot) {
        if(nextPageItem != null && Objects.equals(slot, nextPageItem.getKey()) && getPageCount() > this.page + 1) return true;
        else if(previousPageItem != null && Objects.equals(slot, previousPageItem.getKey()) && this.page > 0) return true;

        for (int i = 0; i < itemSlots.length; i++) {
            int itemSlot = itemSlots[i];
            if (slot == itemSlot) {
                if ((this.pageableItems.size() - 1) >= (itemSlots.length * page + i))
                    return true;
                break;
            }
        }

        return super.containsItem(slot);
    }

    @Override
    public void requireUpdate(Integer slot) {
        if(fatherMenu == null) mirrorMenus.forEach(menu -> menu.requireUpdate(slot));

        if(this.inventory != null) {
            if(hasViewers())
                if(slot == null) update();
                else update(slot);
            else this.slotsRequiringUpdate.add(slot);
        }
    }

    @Override
    public MenuItem getItem(Integer slot) {
        if(nextPageItem != null && Objects.equals(slot, nextPageItem.getKey()) && getPageCount() > this.page + 1) return nextPageItem.getValue();
        else if(previousPageItem != null && Objects.equals(slot, previousPageItem.getKey()) && this.page > 0) return previousPageItem.getValue();

        for (int i = 0; i < itemSlots.length; i++) {
            int itemSlot = itemSlots[i];
            if (slot == itemSlot) {
                if ((this.pageableItems.size() - 1) >= (itemSlots.length * page + i))
                    return pageableItems.get(itemSlots.length * page + i);
                break;
            }
        }

        return super.getItem(slot);
    }

    @Override
    public void open(Player player) {
        open(player, 0);
    }

    public void open(Player player, int page) {
        if(this.inventory == null || !super.hasViewers()) {
            this.page = Math.min(getPageCount() - 1, page);
            requireUpdate(null);
            super.open(player);
        } else new PageableChestMenu(this).open(player, page);
    }

    @Override
    public boolean hasViewers() {
        return super.hasViewers() || (this.fatherMenu == null && mirrorMenus.stream().anyMatch(PageableChestMenu::hasViewers));
    }

    @Override
    public ChestMenu copy() {
        PageableChestMenu copy = new PageableChestMenu(this.getTitle(), this.getRows(), this.getItemSlots(), this.plugin);

        copy.setOnOpen(getOnOpen());
        copy.setOnClose(getOnClose());

        copy.setOnPrimary(getOnPrimary());
        copy.setOnSecondary(getOnSecondary());
        copy.setOnDrop(getOnDrop());
        copy.setOnDropAll(getOnDropAll());
        copy.setOnMiddle(getOnMiddle());
        copy.setOnNumber(getOnNumber());
        copy.setOnShiftPrimary(getOnShiftPrimary());
        copy.setOnShiftSecondary(getOnShiftSecondary());
        copy.setOnDouble(getOnDouble());

        if(nextPageItem != null) copy.setNextPageItem(nextPageItem.getValue(), nextPageItem.getKey());
        if(previousPageItem != null) copy.setPreviousPageItem(previousPageItem.getValue(), previousPageItem.getKey());

        getItems().forEach((slot, item) -> copy.addItem(item.copy(), slot));
        copy.setPageableItems(getPageableItems());
        return copy;
    }

    @Override
    protected BukkitTask getUpdateItemsTask() {
        if(fatherMenu == null) return super.getUpdateItemsTask();
        else return fatherMenu.getUpdateItemsTask();
    }

    @Override
    protected ChestMenu setUpdateItemsTask(BukkitTask updateItemsTask) {
        if(fatherMenu == null) return super.setUpdateItemsTask(updateItemsTask);
        else return fatherMenu.setUpdateItemsTask(updateItemsTask);
    }

    @Override
    protected void handlesUpdateItemsTask() {
        if(fatherMenu == null) super.handlesUpdateItemsTask();
        else fatherMenu.handlesUpdateItemsTask();
    }

    @Override
    protected void handleUpdateItems() {
        super.handleUpdateItems();

        getPageableItems().forEach(item -> {
            if (item.update()) {
                requireUpdate(getPageableItemSlot(item).getValue());
            }
        });
    }

    //Override global actions for mirrored menus
    @Override
    public Consumer<InventoryOpenEvent> getOnOpen() {
        return this.fatherMenu == null ? super.getOnOpen() : this.fatherMenu.getOnOpen();
    }

    @Override
    public Consumer<InventoryCloseEvent> getOnClose() {
        return this.fatherMenu == null ? super.getOnClose() : this.fatherMenu.getOnClose();
    }

    @Override
    public Consumer<InventoryClickEvent> getOnClick() {
        return this.fatherMenu == null ? super.getOnClick() : this.fatherMenu.getOnClick();
    }

    @Override
    public Consumer<InventoryClickEvent> getOnPrimary() {
        return this.fatherMenu == null ? super.getOnPrimary() : this.fatherMenu.getOnPrimary();
    }

    @Override
    public Consumer<InventoryClickEvent> getOnMiddle() {
        return this.fatherMenu == null ? super.getOnMiddle() : this.fatherMenu.getOnMiddle();
    }

    @Override
    public Consumer<InventoryClickEvent> getOnSecondary() {
        return this.fatherMenu == null ? super.getOnSecondary() : this.fatherMenu.getOnSecondary();
    }

    @Override
    public Consumer<InventoryClickEvent> getOnShiftPrimary() {
        return this.fatherMenu == null ? super.getOnShiftPrimary() : this.fatherMenu.getOnShiftPrimary();
    }

    @Override
    public Consumer<InventoryClickEvent> getOnDouble() {
        return this.fatherMenu == null ? super.getOnDouble() : this.fatherMenu.getOnDouble();
    }

    @Override
    public Consumer<InventoryClickEvent> getOnDrop() {
        return this.fatherMenu == null ? super.getOnDrop() : this.fatherMenu.getOnDrop();
    }

    @Override
    public Consumer<InventoryClickEvent> getOnShiftSecondary() {
        return this.fatherMenu == null ? super.getOnShiftSecondary() : this.fatherMenu.getOnShiftSecondary();
    }

    @Override
    public Consumer<InventoryClickEvent> getOnDropAll() {
        return this.fatherMenu == null ? super.getOnDropAll() : this.fatherMenu.getOnDropAll();
    }

    @Override
    public Consumer<InventoryClickEvent> getOnNumber() {
        return this.fatherMenu == null ? super.getOnNumber() : this.fatherMenu.getOnNumber();
    }

}
