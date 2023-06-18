package uk.mqchinee.featherapi.gui.core.toolbar;

import uk.mqchinee.featherapi.gui.core.buttons.Button;
import uk.mqchinee.featherapi.gui.core.menu.Menu;

public interface ToolbarBuilder {

    Button buildToolbarButton(int slot, int page, ToolbarButtonType defaultType, Menu menu);

}
