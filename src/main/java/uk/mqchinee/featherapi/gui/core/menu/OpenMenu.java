package uk.mqchinee.featherapi.gui.core.menu;

import org.bukkit.entity.Player;

public class OpenMenu {

    private final Menu gui;
    private final Player player;

    public OpenMenu(Menu gui, Player player) {
        this.gui = gui;
        this.player = player;
    }

    public Menu getMenu() {
        return this.gui;
    }

    public Player getPlayer() {
        return this.player;
    }

}
