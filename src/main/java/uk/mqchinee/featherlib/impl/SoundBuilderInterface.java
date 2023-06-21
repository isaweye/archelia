package uk.mqchinee.featherlib.impl;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.List;

public interface SoundBuilderInterface {
    Sound getSound();

    SoundBuilderInterface sound(Sound sound);

    double getVolume();

    SoundBuilderInterface volume(float volume);

    double getPitch();

    SoundBuilderInterface pitch(float pitch);

    List<Player> getPlayTo();

    SoundBuilderInterface playTo(List<Player> playTo);

    Location getLocation();

    SoundBuilderInterface location(Location location);

    void play();
}
