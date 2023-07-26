package uk.mqchinee.archelia.impl;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import uk.mqchinee.archelia.builders.ParticleBuilder;

import java.util.List;

public interface ParticleBuilderInterface {

    /**
     * Gets the X offset of the particle.
     *
     * @return The X offset of the particle.
     */
    double getOffsetX();

    /**
     * Sets the X offset of the particle.
     *
     * @param offset_x The X offset to set for the particle.
     * @return The ParticleBuilderInterface instance for method chaining.
     */
    ParticleBuilder offsetX(double offset_x);

    /**
     * Gets the Y offset of the particle.
     *
     * @return The Y offset of the particle.
     */
    double getOffsetY();

    /**
     * Sets the Y offset of the particle.
     *
     * @param offset_y The Y offset to set for the particle.
     * @return The ParticleBuilderInterface instance for method chaining.
     */
    ParticleBuilder offsetY(double offset_y);

    /**
     * Gets the Z offset of the particle.
     *
     * @return The Z offset of the particle.
     */
    double getOffsetZ();

    /**
     * Sets the Z offset of the particle.
     *
     * @param offset_z The Z offset to set for the particle.
     * @return The ParticleBuilderInterface instance for method chaining.
     */
    ParticleBuilder offsetZ(double offset_z);

    /**
     * Gets the material data of the particle.
     *
     * @return The material data of the particle.
     */
    Material getMaterialData();

    /**
     * Sets the material data of the particle.
     *
     * @param materialData The material data to set for the particle.
     * @return The ParticleBuilderInterface instance for method chaining.
     */
    ParticleBuilder materialData(Material materialData);

    /**
     * Gets the dust options of the particle.
     *
     * @return The dust options of the particle.
     */
    Particle.DustOptions getDustOptions();

    /**
     * Sets the dust options of the particle.
     *
     * @param dustOptions The dust options to set for the particle.
     * @return The ParticleBuilderInterface instance for method chaining.
     */
    ParticleBuilder dustOptions(Particle.DustOptions dustOptions);

    /**
     * Gets the speed of the particle.
     *
     * @return The speed of the particle.
     */
    double getSpeed();

    /**
     * Sets the speed of the particle.
     *
     * @param speed The speed to set for the particle.
     * @return The ParticleBuilderInterface instance for method chaining.
     */
    ParticleBuilder speed(double speed);

    /**
     * Gets the list of players to show the particle to.
     *
     * @return The list of players to show the particle to.
     */
    List<Player> getShowTo();

    /**
     * Sets the list of players to show the particle to.
     *
     * @param showTo The list of players to show the particle to.
     * @return The ParticleBuilderInterface instance for method chaining.
     */
    ParticleBuilder showTo(List<Player> showTo);

    /**
     * Gets the location of the particle.
     *
     * @return The location of the particle.
     */
    Location getLocation();

    /**
     * Sets the location of the particle.
     *
     * @param location The location to set for the particle.
     * @return The ParticleBuilderInterface instance for method chaining.
     */
    ParticleBuilder location(Location location);

    /**
     * Gets the count of the particle.
     *
     * @return The count of the particle.
     */
    int getCount();

    /**
     * Gets the type of particle.
     *
     * @return The type of particle.
     */
    Particle getParticle();

    /**
     * Sets the type of particle.
     *
     * @param particle The particle type to set.
     * @return The ParticleBuilderInterface instance for method chaining.
     */
    ParticleBuilder particle(Particle particle);

    /**
     * Sets the count of the particle.
     *
     * @param count The count to set for the particle.
     * @return The ParticleBuilderInterface instance for method chaining.
     */
    ParticleBuilder count(int count);

    /**
     * Builds and displays the particle with the specified configurations.
     */
    void build();
}
