package uk.mqchinee.archelia.gui;

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
import uk.mqchinee.archelia.gui.item.ClickableItem;
import uk.mqchinee.archelia.gui.item.LoopableItem;
import uk.mqchinee.archelia.gui.item.MenuItem;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * The core class for creating and managing GUI menus in Bukkit/Spigot.
 * <p>
 * This class provides methods to add, remove, and update menu items, as well as handle events related to the GUI menu.
 * It represents a GUI menu that can be displayed to players and interacted with through inventory click events.
 * </p>
 * @since 1.0
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
@Accessors(chain = true)
public class ChestMenu {

    private static final MenuItem dummyItem = ClickableItem.create(new ItemStack(Material.AIR), false);

    // Base properties
    @Getter private final String title;
    @Getter private final int rows;
    @Getter final JavaPlugin plugin;

    // Global actions
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
    private final boolean isConcurrent;

    // Items
    protected Map<Integer, MenuItem> items;

    // Bukkit Inventory
    @Getter protected Inventory inventory;
    protected final Set<Integer> slotsRequiringUpdate = Sets.newHashSet();
    @Getter(AccessLevel.PROTECTED) @Setter(AccessLevel.PROTECTED) private BukkitTask updateItemsTask = null;

    /**
     * Constructs a ChestMenu with the given title, number of rows, and JavaPlugin instance.
     *
     * @param title       The title of the ChestMenu.
     * @param rows        The number of rows for the ChestMenu (must be >= 1 && <= 6).
     * @param plugin      The JavaPlugin instance associated with this ChestMenu.
     * @param isConcurrent Allows you to choose between HashMap (recommended) and ConcurrentHashMap.
     *                    If you want to get rid of ConcurrentModificationException (occurs when using MovableItem), use ConcurrentHashMap.
     */
    ChestMenu(String title, int rows, JavaPlugin plugin, boolean isConcurrent) {
        if (rows <= 0 || rows > 6)
            throw new IllegalArgumentException("The number of rows for a menu must be >= 1 && <= 6.");
        if (isConcurrent) {
            this.items = new ConcurrentHashMap<>();
        } else {
            this.items = new HashMap<>();
        }
        this.title = Objects.requireNonNull(title);
        this.rows = rows;
        this.plugin = Objects.requireNonNull(plugin);
        this.isConcurrent = isConcurrent;
    }

    /**
     * Adds a MenuItem to the specified slot in the ChestMenu.
     *
     * @param item The MenuItem to add.
     * @param slot The slot number (0-based) where the MenuItem will be placed.
     * @return The updated ChestMenu.
     * @throws IllegalArgumentException if the slot is less than zero or greater than or equal to the inventory size.
     */
    public ChestMenu addItem(MenuItem item, int slot) {
        if (slot < 0 || slot >= this.rows * 9)
            throw new IllegalArgumentException("The slot can't be less than zero or greater than the inventory size.");

        items.put(slot, item);
        requireUpdate(slot);
        return this;
    }

    /**
     * Removes the MenuItem from the specified slot in the ChestMenu.
     *
     * @param slot The slot number (0-based) from which the MenuItem will be removed.
     * @return The updated ChestMenu.
     */
    public ChestMenu removeItem(int slot) {
        this.items.remove(slot);
        requireUpdate(slot);
        return this;
    }

    /**
     * Sets the MenuItem for the specified slot in the ChestMenu.
     * If there was already a MenuItem in that slot, it will be replaced.
     *
     * @param item The MenuItem to set.
     * @param slot The slot number (0-based) where the MenuItem will be placed.
     * @return The updated ChestMenu.
     */
    public ChestMenu setItem(MenuItem item, int slot) {
        if (getItem(slot) != null) {
            removeItem(slot);
        }
        addItem(item, slot);
        return this;
    }

    /**
     * Fills the specified range of slots with the given LoopableItem.
     *
     * @param from The start slot number (0-based) of the range to fill.
     * @param till The end slot number (0-based) of the range to fill.
     * @param item The LoopableItem to fill the range with.
     */
    public void fill(int from, int till, LoopableItem item) {
        for (int i = from; i < (till + 1); i++) {
            addItem(item, i);
        }
    }

    /**
     * Fills the specified range of slots with the given ClickableItem.
     *
     * @param from The start slot number (0-based) of the range to fill.
     * @param till The end slot number (0-based) of the range to fill.
     * @param item The ClickableItem to fill the range with.
     */
    public void fill(int from, int till, ClickableItem item) {
        for (int i = from; i < (till + 1); i++) {
            addItem(item, i);
        }
    }

    /**
     * Retrieves the MenuItem at the specified slot in the ChestMenu.
     *
     * @param slot The slot number (0-based) to retrieve the MenuItem from.
     * @return The MenuItem at the specified slot, or null if there is no item at that slot.
     */
    public MenuItem getItem(Integer slot) {
        return items.get(slot);
    }

    /**
     * Retrieves all the items in the ChestMenu as an unmodifiable map of slot to MenuItem.
     *
     * @return A map of slot numbers (0-based) to their respective MenuItems.
     */
    public Map<Integer, MenuItem> getItems() {
        return Collections.unmodifiableMap(this.items);
    }

    /**
     * Retrieves the ItemStack at the specified slot in the ChestMenu.
     *
     * @param slot The slot number (0-based) to retrieve the ItemStack from.
     * @return The ItemStack at the specified slot, or ItemStack(Material.AIR) if there is no item at that slot.
     */
    public ItemStack getItemStack(Integer slot) {
        ItemStack item = this.inventory.getItem(slot);
        return item == null ? new ItemStack(Material.AIR) : item;
    }

    /**
     * Retrieves all the non-empty ItemStacks in the ChestMenu as an unmodifiable map of slot to ItemStack.
     *
     * @return A map of slot numbers (0-based) to their respective non-empty ItemStacks.
     */
    public Map<Integer, ItemStack> getItemStacks() {
        Map<Integer, ItemStack> items = new HashMap<>();

        for (int i = 0; i < this.inventory.getSize(); i++) {
            ItemStack item = this.inventory.getItem(i);
            if (item != null && item.getType() != Material.AIR) items.put(i, item);
        }

        return Collections.unmodifiableMap(items);
    }

    /**
     * Checks if the specified slot in the ChestMenu contains a MenuItem.
     *
     * @param slot The slot number (0-based) to check.
     * @return true if the slot contains a MenuItem, false otherwise.
     */
    public boolean containsItem(Integer slot) {
        return slot != null && items.containsKey(slot);
    }

    /**
     * Sets the items in the ChestMenu to the specified map of slot to MenuItem.
     * Existing items in the ChestMenu will be replaced.
     *
     * @param items The map of slot numbers (0-based) to their respective MenuItems.
     * @return The updated ChestMenu.
     */
    public ChestMenu setItems(Map<Integer, MenuItem> items) {
        this.items.clear();
        items.forEach((slot, item) -> addItem(item, slot));
        requireUpdate(null);
        return this;
    }

    /**
     * Clears all items in the ChestMenu.
     *
     * @return The updated ChestMenu.
     */
    public ChestMenu clearItems() {
        items.clear();
        requireUpdate(null);
        return this;
    }

    /**
     * Updates the entire ChestMenu by updating all the item stacks.
     */
    public void update() {
        if (this.inventory == null) {
            this.inventory = Bukkit.createInventory(null, this.rows * 9, this.title);
        }

        for (int i = 0; i < this.inventory.getSize(); i++) {
            updateSlotStack(i);
        }

        slotsRequiringUpdate.clear();
    }

    /**
     * Updates the item stack at the specified slot in the ChestMenu.
     *
     * @param slot The slot number (0-based) to update.
     */
    public void update(int slot) {
        if (this.inventory == null) update();
        updateSlotStack(slot);
        slotsRequiringUpdate.remove(slot);
    }

    /**
     * Marks the specified slot in the ChestMenu as requiring an update.
     *
     * @param slot The slot number (0-based) that requires an update.
     */
    public void requireUpdate(Integer slot) {
        if (this.inventory != null) {
            if (hasViewers()) {
                if (slot == null) update();
                else update(slot);
            } else this.slotsRequiringUpdate.add(slot);
        }
    }

    /**
     * Opens the ChestMenu for the specified player.
     * If the inventory has viewers, it will be closed and reopened for the player.
     *
     * @param player The player to open the ChestMenu for.
     */
    public void open(Player player) {
        if (this.inventory == null) update();
        Bukkit.getScheduler().runTask(this.plugin, () -> {
            if (slotsRequiringUpdate.size() > 0) {
                if (slotsRequiringUpdate.contains(null)) update();
                else slotsRequiringUpdate.forEach(this::update);
            }

            Listener listener = MenuManager.registeredListeners.get(this.plugin);
            if (listener != null) listener.chestMenus.add(this);

            player.closeInventory();
            player.openInventory(this.inventory);
        });
    }

    /**
     * Checks if the ChestMenu has any viewers.
     *
     * @return true if the ChestMenu has any viewers, false otherwise.
     */
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

    /**
     * Creates a copy of the ChestMenu with all the same properties and items.
     *
     * @return A new copy of the ChestMenu.
     */
    public ChestMenu copy() {
        ChestMenu copy = new ChestMenu(this.title, this.rows, this.plugin, this.isConcurrent);

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

    /**
     * Handles the update items task for the ChestMenu.
     */
    protected void handlesUpdateItemsTask() {
        if (getUpdateItemsTask() == null && hasViewers()) {
            setUpdateItemsTask(new BukkitRunnable() {
                @Override
                public void run() {
                    handleUpdateItems();

                    if (!hasViewers()) {
                        this.cancel();
                        updateItemsTask = null;
                    }
                }
            }.runTaskTimer(this.plugin, 1, 1));
        }
    }

    /**
     * Updates the items in the ChestMenu that require updates.
     */
    protected void handleUpdateItems() {
        getItems().forEach((slot, item) -> {
            if (item.update()) {
                requireUpdate(slot);
            }
        });
    }

    @RequiredArgsConstructor
    static class Listener implements org.bukkit.event.Listener {

        private static final MenuItem dummyItem = ClickableItem.create(new ItemStack(Material.AIR), false);

        private final JavaPlugin plugin;
        public final Set<ChestMenu> chestMenus = Collections.synchronizedSet(Sets.newHashSet());

        @EventHandler
        public void onClick(InventoryClickEvent ce) {
            for (ChestMenu chestMenu : chestMenus) {
                if (chestMenu.getInventory().equals(ce.getInventory())) {
                    if (ce.getClick() == ClickType.DOUBLE_CLICK) ce.setCancelled(true);

                    if (ce.getClick() == ClickType.SHIFT_LEFT || ce.getClick() == ClickType.SHIFT_RIGHT) ce.setCancelled(true);

                    int slot = ce.getSlot();
                    if (slot < 9 * chestMenu.getRows()) {
                        ce.setCancelled(true);

                        chestMenu.getOnClick().accept(ce);

                        MenuItem item = chestMenu.getItem(slot);
                        if (item == null) item = dummyItem;

                        item.getOnClick().accept(ce);

                        switch (ce.getClick()) {
                            case DOUBLE_CLICK -> {
                                chestMenu.getOnDouble().accept(ce);
                                item.getOnDouble().accept(ce);
                            }
                            case SHIFT_LEFT -> {
                                chestMenu.getOnShiftPrimary().accept(ce);
                                item.getOnShiftPrimary().accept(ce);
                            }
                            case SHIFT_RIGHT -> {
                                chestMenu.getOnShiftSecondary().accept(ce);
                                item.getOnShiftSecondary().accept(ce);
                            }
                            case LEFT -> {
                                chestMenu.getOnPrimary().accept(ce);
                                item.getOnPrimary().accept(ce);
                            }
                            case MIDDLE -> {
                                chestMenu.getOnMiddle().accept(ce);
                                item.getOnMiddle().accept(ce);
                            }
                            case RIGHT -> {
                                chestMenu.getOnSecondary().accept(ce);
                                item.getOnSecondary().accept(ce);
                            }
                            case CONTROL_DROP -> {
                                chestMenu.getOnDropAll().accept(ce);
                                item.getOnDropAll().accept(ce);
                            }
                            case DROP -> {
                                chestMenu.getOnDrop().accept(ce);
                                item.getOnDrop().accept(ce);
                            }
                            case NUMBER_KEY -> {
                                chestMenu.getOnNumber().accept(ce);
                                item.getOnNumber().accept(ce);
                            }
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