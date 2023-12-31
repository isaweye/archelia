package uk.mqchinee.archelia.gui.tests;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import uk.mqchinee.archelia.builders.ItemBuilder;
import uk.mqchinee.archelia.gui.MenuManager;
import uk.mqchinee.archelia.gui.PageableChestMenu;
import uk.mqchinee.archelia.gui.item.ClickableItem;
import uk.mqchinee.archelia.gui.utils.Structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputMenu {

    @Getter private final PageableChestMenu menu;
    @Getter @Setter private List<String> symbols = new ArrayList<>();
    @Getter private final ClickableItem sign = ClickableItem.create(new ItemBuilder(Material.OAK_SIGN).name("").build(), true);

    public InputMenu(String title, JavaPlugin plugin) {
        this.menu = MenuManager.createPageableChestMenu(title, 6, plugin, false);
    }

    private void prepare() {
        ClickableItem A = ClickableItem.create(new ItemBuilder(Material.OAK_BUTTON).name("&e&lSPACE").build(), false);
        A.setOnClick(e -> InputMenuUtils.add(sign, " "));

        ClickableItem B = ClickableItem.create(new ItemBuilder(Material.OAK_BUTTON).name("&e&lBACKSPACE").build(), false);
        B.setOnClick(e -> InputMenuUtils.back(sign));

        ClickableItem W = ClickableItem.create(new ItemBuilder(Material.OAK_BUTTON).name("&c&lCLEAR").build(), false);
        W.setOnClick(e -> InputMenuUtils.clear(sign));

        Structure s = new Structure(
            "C C C C C C C C C",
                  "C # # # S # # # C",
                  "C C C C C C C C C",
                  "C C < A B W > C C",
                  "% % % % % % % % %",
                  "% % % % % % % % %"
        ).set('<', ClickableItem.create(new ItemBuilder(Material.MAP).name("&f").build(), false))
         .set('>', ClickableItem.create(new ItemBuilder(Material.MAP).name("&f").build(), false))
         .set('C', ClickableItem.create(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).name("&f").build(), false))
         .set('S', sign)
         .set('A', A)
         .set('B', B)
         .set('W', W);

        s.process(menu);
        getSymbols().forEach((str -> {
            ClickableItem c = ClickableItem.create(new ItemBuilder(Material.OAK_SIGN).name(str).build(), false);
            c.setOnClick(e -> InputMenuUtils.add(sign, str));
            menu.addPageableItem(c);
        }));
    }

    public void defaultLatin(boolean lower, boolean upper) {
        List<String> alphabet = Arrays.asList("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z");
        if (lower) {
            alphabet.forEach((str) -> {
                ClickableItem c = ClickableItem.create(new ItemBuilder(Material.ENDER_EYE).name(str).build(), false);
                c.setOnClick(e -> InputMenuUtils.add(sign, str));
                menu.addPageableItem(c);
            });
        }
        if (upper) {
            alphabet.forEach((str) -> {
                ClickableItem c = ClickableItem.create(new ItemBuilder(Material.ENDER_PEARL).name(str).build(), false);
                c.setOnClick(e -> InputMenuUtils.add(sign, str.toUpperCase()));
                menu.addPageableItem(c);
            });
        }
    }

    public void open(Player p) {
        prepare();
        menu.open(p);
    }


}
