package uk.mqchinee.lanterncore.gui.tests;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import uk.mqchinee.lanterncore.builders.ItemBuilder;
import uk.mqchinee.lanterncore.gui.MenuManager;
import uk.mqchinee.lanterncore.gui.PageableChestMenu;
import uk.mqchinee.lanterncore.gui.item.ClickableItem;
import uk.mqchinee.lanterncore.gui.utils.Structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputMenu {

    @Getter private final PageableChestMenu menu;
    @Getter @Setter private List<String> symbols = new ArrayList<>();
    @Getter private ClickableItem sign = ClickableItem.create(new ItemBuilder(Material.OAK_SIGN).name(" ").build(), true);

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

        s.parse(menu);
        getSymbols().forEach((str -> {
            ClickableItem c = ClickableItem.create(new ItemBuilder(Material.OAK_SIGN).name(str).build(), false);
            c.setOnClick(e -> InputMenuUtils.add(sign, str));
            menu.addPageableItem(c);
        }));
    }

    public void defaultLatin(boolean lower, boolean upper) {
        if (lower) {
            List<String> LowerCaseAlphabet = Arrays.asList("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z");
            LowerCaseAlphabet.forEach((str) -> {
                ClickableItem c = ClickableItem.create(new ItemBuilder(Material.OAK_SIGN).name(str).build(), false);
                c.setOnClick(e -> InputMenuUtils.add(sign, str));
                menu.addPageableItem(c);
            });
        }
        if (upper) {
            List<String> UpperCaseAlphabet = Arrays.asList("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z");
            UpperCaseAlphabet.forEach((str) -> {
                ClickableItem c = ClickableItem.create(new ItemBuilder(Material.OAK_SIGN).name(str).build(), false);
                c.setOnClick(e -> InputMenuUtils.add(sign, str));
                menu.addPageableItem(c);
            });
        }
    }

    public void open(Player p) {
        prepare();
        menu.open(p);
    }


}
