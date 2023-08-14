package uk.mqchinee.archelia.abs;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Represents a cooldown utility for tracking and managing cooldowns.
 */
public class Cooldown {

    private static final Map<String, Cooldown> cooldowns = new HashMap<>();
    private long start;
    private final int timeInSeconds;
    private final UUID id;
    private final String cooldownName;

    /**
     * Constructs a new Cooldown instance.
     *
     * @param id            The UUID associated with the cooldown.
     * @param cooldownName  The name of the cooldown.
     * @param timeInSeconds The duration of the cooldown in seconds.
     */
    public Cooldown(UUID id, String cooldownName, int timeInSeconds) {
        this.id = id;
        this.cooldownName = cooldownName;
        this.timeInSeconds = timeInSeconds;
    }

    /**
     * Checks if the given UUID is currently in cooldown for the specified cooldown name.
     *
     * @param id           The UUID to check for cooldown.
     * @param cooldownName The name of the cooldown to check.
     * @return True if the UUID is in cooldown, false otherwise.
     */
    public static boolean isInCooldown(UUID id, String cooldownName) {
        if (getTimeLeft(id, cooldownName) >= 1) {
            return true;
        } else {
            stop(id, cooldownName);
            return false;
        }
    }

    private static void stop(UUID id, String cooldownName) {
        cooldowns.remove(id.toString() + cooldownName);
    }

    private static Cooldown getCooldown(UUID id, String cooldownName) {
        return cooldowns.get(id.toString() + cooldownName);
    }

    /**
     * Retrieves the time left in seconds for the given UUID's cooldown on the specified cooldown name.
     *
     * @param id           The UUID for which to retrieve the cooldown time left.
     * @param cooldownName The name of the cooldown to retrieve.
     * @return The time left in seconds for the cooldown, or -1 if not in cooldown.
     */
    public static int getTimeLeft(UUID id, String cooldownName) {
        Cooldown cooldown = getCooldown(id, cooldownName);
        int f = -1;
        if (cooldown != null) {
            long now = System.currentTimeMillis();
            long cooldownTime = cooldown.start;
            int totalTime = cooldown.timeInSeconds;
            int r = (int) ((now - cooldownTime) / 1000);
            f = (r - totalTime) * (-1);
        }
        return f;
    }

    /**
     * Starts the cooldown for the associated UUID and cooldown name.
     */
    public void start() {
        this.start = System.currentTimeMillis();
        cooldowns.put(this.id.toString() + this.cooldownName, this);
    }
}
