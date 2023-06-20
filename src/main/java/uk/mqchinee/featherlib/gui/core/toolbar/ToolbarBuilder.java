package uk.mqchinee.featherlib.gui.core.toolbar;

import uk.mqchinee.featherlib.gui.core.buttons.Button;
import uk.mqchinee.featherlib.gui.core.menu.Menu;

public interface ToolbarBuilder {

    Button buildToolbarButton(int slot, int page, ToolbarButtonType defaultType, Menu menu);

}
