![banner](https://github.com/isaweye/lantern-core/assets/130868496/71769d39-6fc7-4b3f-a860-a5502810bb61)

**LanternCore is a mixture of good and bad, past and present, which is used by my plugins.**

## Installation
1. Install to local repo
```shell
   $ mvn install
```
2. Add as dependency
```xml
    <dependency>
        <groupId>uk.mqchinee</groupId>
        <artifactId>LanternCore</artifactId>
        <version>version-here</version>
    </dependency>
```

## Usage example

### Inventory-Based GUI creation
> extended version of [@focamacho](https://github.com/focamacho)'s [SealMenus](https://github.com/focamacho/SealMenus)

```java
public class MyGUI {

    private final ChestMenu menu;

    public MyGUI() {
    // title, rows, plugin. is concurrent
    // (Allows you to choose between HashMap (recommended) and ConcurrentHashMap.
    // If you want to get rid of ConcurrentModificationException (occurs when using MovableItem), use ConcurrentHashMap)

        this.menu = MenuManager.createChestMenu("Buttons", 5, MyPlugin.getInstance(), true);
    }

    public void prepare() {

        ClickableItem S = ClickableItem.create(new ItemBuilder(Material.OAK_SIGN).name("&emovable").build());
        ClickableItem I = ClickableItem.create(new ItemBuilder(Material.OAK_SIGN).name("&eloopable").build());
        ClickableItem G = ClickableItem.create(new ItemBuilder(Material.OAK_SIGN).name("&eclickable").build());
        ClickableItem N = ClickableItem.create(new ItemBuilder(Material.OAK_SIGN).name("&edynamic").build());
        ClickableItem K = ClickableItem.create(new ItemBuilder(Material.OAK_SIGN).name("&etoggleable").build());

        // item, speed, menu, reverse, structure
        MovableItem M = MovableItem.create(new ItemBuilder(Material.QUARTZ).name("&fitem")).build(), 4, menu, true,
                "# % % % % % % % %",
                "# # # # # # # # #",
                "# # # # # # # # #",
                "# # # # # # # # #",
                "# # # # # # # # #"
        );
        M.setOnClick(e -> e.getWhoClicked().sendMessage("test))"));

        LoopableItem L = LoopableItem.create(Arrays.asList(new ItemStack(Material.DIAMOND), new ItemStack(Material.GOLD_INGOT), new ItemStack(Material.IRON_INGOT)), 20);

        ClickableItem C = ClickableItem.create(new ItemStack(Material.OAK_BUTTON));
        C.setOnPrimary(e -> e.getWhoClicked().sendMessage("hi, "+e.getWhoClicked().getName()));

        DynamicItem D = DynamicItem.create(new ItemStack(Material.OAK_LEAVES));
        D.setOnClick(e -> D.replace(new ItemStack(Material.BARRIER), 1, Time.IN_SECONDS));

        ItemStack on = new ItemBuilder(Material.GREEN_STAINED_GLASS).name("&fSomething &aENABLED").build();
        ItemStack off = new ItemBuilder(Material.RED_STAINED_GLASS).name("&fSomething &cDISABLED").build();

         // enabled, disabled, toggle on click
        ToggleableItem T = ToggleableItem.create(on, off, true);

        Structure structure = new Structure(
                "S M # # # # # # #",
                "I # # # L # # # #",
                "G # # # C # # # #",
                "N # # # D # # # #",
                "K # # # T # # # #"
        )
                .set('S', S)
                .set('I', I)
                .set('G', G)
                .set('N', N)
                .set('K', K)
                .set('M', M)
                .set('L', L)
                .set('C', C)
                .set('D', D)
                .set('T', T);

        structure.parse(menu);
    }

    public void open(Player player) {
        prepare();
        menu.open(player);
    }

}
```

*More examples will be added soon...*
