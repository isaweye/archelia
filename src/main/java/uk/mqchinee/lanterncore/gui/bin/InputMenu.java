package uk.mqchinee.lanterncore.gui.bin;

import org.bukkit.plugin.java.JavaPlugin;
import uk.mqchinee.lanterncore.gui.MenuManager;
import uk.mqchinee.lanterncore.gui.PageableChestMenu;

public class InputMenu {

    private final PageableChestMenu menu;

    public InputMenu(String title, JavaPlugin plugin) {
        this.menu = MenuManager.createPageableChestMenu(title, 6, plugin, false);
    }

    private void prepare() {
        menu.setOnClose(e -> e.getPlayer().sendMessage("tut budet result"));
    }


}
