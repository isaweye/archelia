package uk.mqchinee.featherlib.deprecated.GUI.toolbar;

import uk.mqchinee.featherlib.deprecated.GUI.buttons.Button;
import uk.mqchinee.featherlib.deprecated.GUI.menu.Menu;

@Deprecated
public interface ToolbarBuilder {

    Button buildToolbarButton(int slot, int page, ToolbarButtonType defaultType, Menu menu);

}
