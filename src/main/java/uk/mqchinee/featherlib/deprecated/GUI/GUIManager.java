package uk.mqchinee.featherlib.deprecated.GUI;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import uk.mqchinee.featherlib.builders.ItemBuilder;
import uk.mqchinee.featherlib.deprecated.GUI.buttons.Button;
import uk.mqchinee.featherlib.deprecated.GUI.menu.Menu;
import uk.mqchinee.featherlib.deprecated.GUI.menu.MenuListener;
import uk.mqchinee.featherlib.deprecated.GUI.menu.OpenMenu;
import uk.mqchinee.featherlib.deprecated.GUI.toolbar.ToolbarBuilder;

import java.util.ArrayList;
import java.util.List;


@Deprecated
public class GUIManager {

    private final JavaPlugin plugin;

    private boolean blockDefaultInteractions = true;

    private boolean enableAutomaticPagination = true;
    private String prevButton;
    private String nextButton;
    private String closeButton;
    private String closeButtonLore;

    public GUIManager(JavaPlugin plugin, String _prevButton, String _nextButton, String _closeButton, String _closeButtonLore) {
        this.plugin = plugin;
        prevButton = _prevButton; nextButton = _nextButton; closeButton = _closeButton; closeButtonLore = _closeButtonLore;

        plugin.getServer().getPluginManager().registerEvents(
                new MenuListener(plugin, this), plugin
        );
    }

    private ToolbarBuilder defaultToolbarBuilder = (slot, page, type, menu) -> {
        ItemStack pane = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                .amount(1)
                .name("&f")
                .build();
        switch (type) {
            case PREV_BUTTON:
                if (menu.getCurrentPage() > 0) return new Button(new ItemBuilder(Material.MAP)
                        .name(prevButton)
                        .build()
                ).withListener(event -> {
                    event.setCancelled(true);
                    menu.previousPage(event.getWhoClicked());
                });
                else return new Button(pane).withListener(event -> event.setCancelled(true));

            case CLOSE_BUTTON:
                return new Button(new ItemBuilder(Material.REDSTONE_BLOCK)
                        .name(closeButton)
                        .lore(closeButtonLore)
                        .amount(1)
                        .build()
                ).withListener(event -> event.getWhoClicked().closeInventory());

            case NEXT_BUTTON:
                if (menu.getCurrentPage() < menu.getMaxPage() - 1) return new Button(new ItemBuilder(Material.MAP)
                        .name(nextButton)
                        .build()
                ).withListener(event -> {
                    event.setCancelled(true);
                    menu.nextPage(event.getWhoClicked());
                });
                else return new Button(pane).withListener(event -> event.setCancelled(true));
            case PANE:
                return new Button(pane).withListener(event -> event.setCancelled(true));
            case UNASSIGNED:
            default:
                return null;
        }
    };

    public Menu create(String name, int rows, boolean needsPagination) {
        return new Menu(plugin, this, needsPagination, name, rows, null);
    }

    public Menu create(String name, int rows, boolean needsPagination, String tag) {
        return new Menu(plugin, this, needsPagination, name, rows, tag);
    }

    public void setBlockDefaultInteractions(boolean blockDefaultInteractions) {
        this.blockDefaultInteractions = blockDefaultInteractions;
    }

    public boolean areDefaultInteractionsBlocked() {
        return blockDefaultInteractions;
    }

    public void setEnableAutomaticPagination(boolean enableAutomaticPagination) {
        this.enableAutomaticPagination = enableAutomaticPagination;
    }

    public boolean isAutomaticPaginationEnabled() {
        return enableAutomaticPagination;
    }

    public void setDefaultToolbarBuilder(ToolbarBuilder defaultToolbarBuilder) {
        this.defaultToolbarBuilder = defaultToolbarBuilder;
    }

    public ToolbarBuilder getDefaultToolbarBuilder() {
        return defaultToolbarBuilder;
    }

    public List<OpenMenu> findOpenWithTag(String tag) {

        List<OpenMenu> foundInventories = new ArrayList<>();

        for (Player player : plugin.getServer().getOnlinePlayers()) {
            if (player.getOpenInventory().getTopInventory() != null) {
                Inventory topInventory = player.getOpenInventory().getTopInventory();

                if (topInventory.getHolder() != null && topInventory.getHolder() instanceof Menu) {
                    Menu inventory = (Menu) topInventory.getHolder();
                    if (inventory.getTag().equals(tag))
                        foundInventories.add(new OpenMenu(inventory, player));
                }
            }
        }

        return foundInventories;

    }

}
