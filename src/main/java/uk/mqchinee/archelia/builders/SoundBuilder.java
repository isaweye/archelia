package uk.mqchinee.archelia.builders;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import uk.mqchinee.archelia.impl.SoundBuilderInterface;

import java.util.List;

/**
 * A builder class for creating and customizing sounds in a Bukkit environment.
 */
public class SoundBuilder implements SoundBuilderInterface {

    private Sound sound;
    private float volume = 3;
    private float pitch = 0;
    private List<Player> playTo = null;
    private Location location;

    @Override
    public Sound getSound() {
        return sound;
    }

    @Override
    public SoundBuilder sound(Sound sound) {
        this.sound = sound;
        return this;
    }

    @Override
    public double getVolume() {
        return volume;
    }

    @Override
    public SoundBuilder volume(float volume) {
        this.volume = volume;
        return this;
    }

    @Override
    public double getPitch() {
        return pitch;
    }

    @Override
    public SoundBuilder pitch(float pitch) {
        this.pitch = pitch;
        return this;
    }

    @Override
    public List<Player> getPlayTo() {
        return playTo;
    }

    @Override
    public SoundBuilder playTo(List<Player> playTo) {
        this.playTo = playTo;
        return this;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public SoundBuilder location(Location location) {
        this.location = location;
        return this;
    }

    /**
     * Plays the sound based on the configured properties.
     */
    @Override
    public void play() {
        if (playTo != null) {
            for (Player p : playTo) {
                p.playSound(location, sound, volume, pitch);
            }
        }
        else {
            location.getWorld().playSound(location, sound, volume, pitch);
        }
    }

}
