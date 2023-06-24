package uk.mqchinee.lanterncore.gui;

import com.google.common.collect.Sets;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import uk.mqchinee.lanterncore.gui.item.ClickableItem;
import uk.mqchinee.lanterncore.gui.item.MenuItem;

import java.util.*;
import java.util.function.Consumer;

@SuppressWarnings({"unused", "UnusedReturnValue"})
@Accessors(chain = true)
public class ChestMenu {

    private static final MenuItem dummyItem = ClickableItem.create(new ItemStack(Material.AIR));

    //Base properties
    @Getter private final String title;
    @Getter private final int rows;
    protected final JavaPlugin plugin;

    //Global actions
    @Getter @Setter private Consumer<InventoryOpenEvent> onOpen = (interact) -> {};
    @Getter @Setter private Consumer<InventoryCloseEvent> onClose = (interact) -> {};
    @Getter @Setter private Consumer<InventoryClickEvent> onClick = (click) -> {};
    @Getter @Setter private Consumer<InventoryClickEvent> onPrimary = (click) -> {};
    @Getter @Setter private Consumer<InventoryClickEvent> onMiddle = (click) -> {};
    @Getter @Setter private Consumer<InventoryClickEvent> onSecondary = (click) -> {};
    @Getter @Setter private Consumer<InventoryClickEvent> onShiftPrimary = (click) -> {};
    @Getter @Setter private Consumer<InventoryClickEvent> onDouble = (click) -> {};
    @Getter @Setter private Consumer<InventoryClickEvent> onDrop = (click) -> {};
    @Getter @Setter private Consumer<InventoryClickEvent> onShiftSecondary = (click) -> {};
    @Getter @Setter private Consumer<InventoryClickEvent> onDropAll = (click) -> {};
    @Getter @Setter private Consumer<InventoryClickEvent> onNumber = (click) -> {};

    //Items
    protected Map<Integer, MenuItem> items = new HashMap<>();

    //Bukkit Inventory
    @Getter protected Inventory inventory;
    protected final Set<Integer> slotsRequiringUpdate = Sets.newHashSet();
    @Getter(AccessLevel.PROTECTED) @Setter(AccessLevel.PROTECTED) private BukkitTask updateItemsTask = null;

    ChestMenu(String title, int rows, JavaPlugin plugin) {
        if(rows <= 0 || rows > 6) throw new IllegalArgumentException("The number of rows for a menu must be >= 1 && <= 6.");

        this.title = Objects.requireNonNull(title);
        this.rows = rows;
        this.plugin = Objects.requireNonNull(plugin);
    }

    public ChestMenu addItem(MenuItem item, int slot) {
        if(slot < 0 || slot >= this.rows * 9) throw new IllegalArgumentException("The slot can't be less than zero or greater than the inventory size.");

        items.put(slot, item);
        requireUpdate(slot);
        return this;
    }

    public ChestMenu removeItem(int slot) {
        this.items.remove(slot);
        requireUpdate(slot);
        return this;
    }

    public void fillRow(int row, ClickableItem item) {
        Bukkit.getScheduler().runTask(plugin, () -> {
            for (int i = 0; i < row*9; i++) {
                addItem(item, i);
            }
        });
    }

    public MenuItem getItem(Integer slot) {
        return items.get(slot);
    }

    public Map<Integer, MenuItem> getItems() {
        return Collections.unmodifiableMap(this.items);
    }

    public ItemStack getItemStack(Integer slot) {
        ItemStack item = this.inventory.getItem(slot);
        return item == null ? new ItemStack(Material.AIR) : item;
    }

    public Map<Integer, ItemStack> getItemStacks() {
        Map<Integer, ItemStack> items = new HashMap<>();

        for(int i = 0; i < this.inventory.getSize(); i++) {
            ItemStack item = this.inventory.getItem(i);
            if(item != null && item.getType() != Material.AIR) items.put(i, item);
        }

        return Collections.unmodifiableMap(items);
    }

    public boolean containsItem(Integer slot) {
        return slot != null && items.containsKey(slot);
    }

    public ChestMenu setItems(Map<Integer, MenuItem> items) {
        this.items.clear();
        items.forEach((slot, item) -> addItem(item, slot));
        requireUpdate(null);
        return this;
    }

    public ChestMenu clearItems() {
        items.clear();
        requireUpdate(null);
        return this;
    }

    public void update() {
        if(this.inventory == null) {
            this.inventory = Bukkit.createInventory(null, this.rows * 9, this.title);
        }

        for (int i = 0; i < this.inventory.getSize(); i++) {
            updateSlotStack(i);
        }

        slotsRequiringUpdate.clear();
    }

    public void update(int slot) {
        if(this.inventory == null) update();
        updateSlotStack(slot);
        slotsRequiringUpdate.remove(slot);
    }

    public void requireUpdate(Integer slot) {
        if(this.inventory != null) {
            if(hasViewers())
                if(slot == null) update();
                else update(slot);
            else this.slotsRequiringUpdate.add(slot);
        }
    }

    public void open(Player player) {
        if(this.inventory == null) update();
        Bukkit.getScheduler().runTask(this.plugin, () -> {
            if(slotsRequiringUpdate.size() > 0) {
                if (slotsRequiringUpdate.contains(null)) update();
                else slotsRequiringUpdate.forEach(this::update);
            }

            Listener listener = MenuManager.registeredListeners.get(this.plugin);
            if(listener != null) listener.chestMenus.add(this);

            player.closeInventory();
            player.openInventory(this.inventory);
        });
    }

    public boolean hasViewers() {
        return this.inventory.getViewers().size() > 0;
    }

    private void updateSlotStack(int slot) {
        ItemStack inventoryStack = this.inventory.getItem(slot);
        if(inventoryStack == null) inventoryStack = new ItemStack(Material.AIR);

        if(containsItem(slot)) {
            ItemStack stack = getItem(slot).getItem();
            if (inventoryStack != stack)
                Bukkit.getScheduler().runTask(this.plugin, () -> this.inventory.setItem(slot, stack));
        } else if(inventoryStack.getType() != Material.AIR) Bukkit.getScheduler().runTask(this.plugin, () -> this.inventory.clear(slot));
    }

    public ChestMenu copy() {
        ChestMenu copy = new ChestMenu(this.title, this.rows, this.plugin);

        copy.setOnOpen(this.getOnOpen());
        copy.setOnClose(this.getOnClose());

        copy.setOnClick(this.getOnClick());
        copy.setOnPrimary(this.getOnPrimary());
        copy.setOnSecondary(this.getOnSecondary());
        copy.setOnDrop(this.getOnDrop());
        copy.setOnDropAll(this.getOnDropAll());
        copy.setOnMiddle(this.getOnMiddle());
        copy.setOnNumber(this.getOnNumber());
        copy.setOnShiftPrimary(this.getOnShiftPrimary());
        copy.setOnShiftSecondary(this.getOnShiftSecondary());
        copy.setOnDouble(this.getOnDouble());

        getItems().forEach((slot, item) -> copy.addItem(item.copy(), slot));
        return copy;
    }

    protected void handlesUpdateItemsTask() {
        if(getUpdateItemsTask() == null && hasViewers())
            setUpdateItemsTask(new BukkitRunnable() {
                @Override
                public void run() {
                    handleUpdateItems();

                    if(!hasViewers()) {
                        this.cancel();
                        updateItemsTask = null;
                    }
                }
            }.runTaskTimer(this.plugin, 1, 1));
    }

    protected void handleUpdateItems() {
        getItems().forEach((slot, item) -> {
            if (item.update()) {
                requireUpdate(slot);
            }
        });
    }

    @RequiredArgsConstructor
    static class Listener implements org.bukkit.event.Listener {

        private static final MenuItem dummyItem = ClickableItem.create(new ItemStack(Material.AIR));

        private final JavaPlugin plugin;
        private final Set<ChestMenu> chestMenus = Collections.synchronizedSet(Sets.newHashSet());

        @EventHandler
        public void onClick(InventoryClickEvent ce) {
            for (ChestMenu chestMenu : chestMenus) {
                if(chestMenu.getInventory().equals(ce.getInventory())) {
                    // Prevent inventory double clicks from stealing items from the menu
                    if(ce.getClick() == ClickType.DOUBLE_CLICK) ce.setCancelled(true);

                    // Prevent Shift + Click from placing items inside the menu
                    if(ce.getClick() == ClickType.SHIFT_LEFT || ce.getClick() == ClickType.SHIFT_RIGHT) ce.setCancelled(true);

                    int slot = ce.getSlot();
                    if (slot < 9 * chestMenu.getRows()) {
                        ce.setCancelled(true);

                        chestMenu.getOnClick().accept(ce);

                        MenuItem item = chestMenu.getItem(slot);
                        if (item == null) item = dummyItem;

                        switch (ce.getClick()) {
                            case DOUBLE_CLICK:
                                chestMenu.getOnDouble().accept(ce);
                                item.getOnDouble().accept(ce);
                                break;
                            case SHIFT_LEFT:
                                chestMenu.getOnShiftPrimary().accept(ce);
                                item.getOnShiftPrimary().accept(ce);
                                break;
                            case SHIFT_RIGHT:
                                chestMenu.getOnShiftSecondary().accept(ce);
                                item.getOnShiftSecondary().accept(ce);
                                break;
                            case LEFT:
                                chestMenu.getOnPrimary().accept(ce);
                                item.getOnPrimary().accept(ce);
                                break;
                            case MIDDLE:
                                chestMenu.getOnMiddle().accept(ce);
                                item.getOnMiddle().accept(ce);
                                break;
                            case RIGHT:
                                chestMenu.getOnSecondary().accept(ce);
                                item.getOnSecondary().accept(ce);
                                break;
                            case CONTROL_DROP:
                                chestMenu.getOnDropAll().accept(ce);
                                item.getOnDropAll().accept(ce);
                                break;
                            case DROP:
                                chestMenu.getOnDrop().accept(ce);
                                item.getOnDrop().accept(ce);
                                break;
                            case NUMBER_KEY:
                                chestMenu.getOnNumber().accept(ce);
                                item.getOnNumber().accept(ce);
                        }
                    }

                    break;
                }
            }
        }

        @EventHandler
        public void onOpen(InventoryOpenEvent ie) {
            for (ChestMenu menu : chestMenus) {
                if (menu.getInventory().equals(ie.getInventory())) {
                    menu.getOnOpen().accept(ie);
                    Bukkit.getScheduler().runTask(this.plugin, menu::handlesUpdateItemsTask);
                    break;
                }
            }
        }

        @EventHandler
        public void onClose(InventoryCloseEvent ie) {
            Iterator<ChestMenu> iterator = chestMenus.iterator();

            while (iterator.hasNext()) {
                ChestMenu menu = iterator.next();
                if (ie.getInventory().equals(menu.getInventory())) {
                    menu.getOnClose().accept(ie);
                    Bukkit.getScheduler().runTask(this.plugin, () -> {
                        if (!menu.hasViewers()) iterator.remove();
                    });
                    break;
                }
            }
        }

    }

}