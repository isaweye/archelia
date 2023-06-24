package uk.mqchinee.lanterncore.impl;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import uk.mqchinee.lanterncore.builders.ParticleBuilder;

import java.util.List;

public interface ParticleBuilderInterface {
    double getOffsetX();

    ParticleBuilder offsetX(double offset_x);

    double getOffsetY();

    ParticleBuilder offsetY(double offset_y);

    double getOffsetZ();

    ParticleBuilder offsetZ(double offset_z);

    Material getMaterialData();

    ParticleBuilder materialData(Material materialData);

    Particle.DustOptions getDustOptions();

    ParticleBuilder dustOptions(Particle.DustOptions dustOptions);

    double getSpeed();

    ParticleBuilder speed(double speed);

    List<Player> getShowTo();

    ParticleBuilder showTo(List<Player> showTo);

    Location getLocation();

    ParticleBuilder location(Location location);

    int getCount();

    Particle getParticle();

    ParticleBuilder particle(Particle particle);

    ParticleBuilder count(int count);

    void build();
}
