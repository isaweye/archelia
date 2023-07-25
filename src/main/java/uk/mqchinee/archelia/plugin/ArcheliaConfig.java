package uk.mqchinee.archelia.plugin;

import uk.mqchinee.archelia.Archelia;
import uk.mqchinee.archelia.abs.Config;

public class ArcheliaConfig extends Config {
    public ArcheliaConfig() {
        super(Archelia.getInstance().getConfig());
    }

    public boolean metrics() {
        return super.getConfig().getBoolean("metrics");
    }

}
