package uk.mqchinee.archelia.impl;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.List;

public interface SoundBuilderInterface {

    /**
     * Gets the type of sound to play.
     *
     * @return The sound to play.
     */
    Sound getSound();

    /**
     * Sets the type of sound to play.
     *
     * @param sound The sound to set.
     * @return The SoundBuilderInterface instance for method chaining.
     */
    SoundBuilderInterface sound(Sound sound);

    /**
     * Gets the volume of the sound.
     *
     * @return The volume of the sound.
     */
    double getVolume();

    /**
     * Sets the volume of the sound.
     *
     * @param volume The volume to set for the sound.
     * @return The SoundBuilderInterface instance for method chaining.
     */
    SoundBuilderInterface volume(float volume);

    /**
     * Gets the pitch of the sound.
     *
     * @return The pitch of the sound.
     */
    double getPitch();

    /**
     * Sets the pitch of the sound.
     *
     * @param pitch The pitch to set for the sound.
     * @return The SoundBuilderInterface instance for method chaining.
     */
    SoundBuilderInterface pitch(float pitch);

    /**
     * Gets the list of players to play the sound to.
     *
     * @return The list of players to play the sound to.
     */
    List<Player> getPlayTo();

    /**
     * Sets the list of players to play the sound to.
     *
     * @param playTo The list of players to play the sound to.
     * @return The SoundBuilderInterface instance for method chaining.
     */
    SoundBuilderInterface playTo(List<Player> playTo);

    /**
     * Gets the location at which to play the sound.
     *
     * @return The location to play the sound.
     */
    Location getLocation();

    /**
     * Sets the location at which to play the sound.
     *
     * @param location The location to set for playing the sound.
     * @return The SoundBuilderInterface instance for method chaining.
     */
    SoundBuilderInterface location(Location location);

    /**
     * Plays the sound with the specified configurations.
     */
    void play();
}
