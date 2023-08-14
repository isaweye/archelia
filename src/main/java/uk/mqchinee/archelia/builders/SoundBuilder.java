package uk.mqchinee.archelia.builders;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import uk.mqchinee.archelia.impl.Builder;

import java.util.List;

/**
 * A builder class for creating and customizing sounds in a Bukkit environment.
 * @since 1.0
 */
public class SoundBuilder implements Builder<Integer> {
    private Sound sound;
    private float volume = 3;
    private float pitch = 0;
    private List<Player> playTo = null;
    private Location location;

    public Sound getSound() {
        return sound;
    }

    public SoundBuilder sound(Sound sound) {
        this.sound = sound;
        return this;
    }

    public double getVolume() {
        return volume;
    }

    public SoundBuilder volume(float volume) {
        this.volume = volume;
        return this;
    }

    public double getPitch() {
        return pitch;
    }

    public SoundBuilder pitch(float pitch) {
        this.pitch = pitch;
        return this;
    }

    public List<Player> getPlayTo() {
        return playTo;
    }

    public SoundBuilder playTo(List<Player> playTo) {
        this.playTo = playTo;
        return this;
    }

    public Location getLocation() {
        return location;
    }

    public SoundBuilder location(Location location) {
        this.location = location;
        return this;
    }

    /**
     * Plays the sound based on the configured properties.
     */
    @Override
    public Integer build() {
        if (playTo != null) {
            for (Player p : playTo) {
                p.playSound(location, sound, volume, pitch);
            }
        }
        else {
            location.getWorld().playSound(location, sound, volume, pitch);
        }
        return 0;
    }

}
