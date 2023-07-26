package uk.mqchinee.archelia.builders;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import uk.mqchinee.archelia.enums.DataType;
import uk.mqchinee.archelia.impl.ParticleBuilderInterface;

import java.util.List;

/**
 * A builder class for creating and customizing particles in a Bukkit environment.
 * @since 1.0
 */
public class ParticleBuilder implements ParticleBuilderInterface {

    private double offset_y = 0.1;
    private double offset_z = 0.1;
    private Material materialData = new ItemStack(Material.STONE).getType();
    private Particle.DustOptions dustOptions;
    private double speed = 0;
    private List<Player> showTo = null;
    private Location location;
    private int count = 3;
    private Particle particle;
    private double offset_x = 0.1;
    private final DataType type;

    public ParticleBuilder(DataType type) {
        this.type = type;
    }

    @Override
    public double getOffsetX() {
        return offset_x;
    }

    @Override
    public ParticleBuilder offsetX(double offset_x) {
        this.offset_x = offset_x;
        return this;
    }

    @Override
    public double getOffsetY() {
        return offset_y;
    }

    @Override
    public ParticleBuilder offsetY(double offset_y) {
        this.offset_y = offset_y;
        return this;
    }

    @Override
    public double getOffsetZ() {
        return offset_z;
    }

    @Override
    public ParticleBuilder offsetZ(double offset_z) {
        this.offset_z = offset_z;
        return this;
    }

    @Override
    public Material getMaterialData() {
        return materialData;
    }

    @Override
    public ParticleBuilder materialData(Material materialData) {
        this.materialData = materialData;
        return this;
    }

    @Override
    public Particle.DustOptions getDustOptions() {
        return dustOptions;
    }

    @Override
    public ParticleBuilder dustOptions(Particle.DustOptions dustOptions) {
        this.dustOptions = dustOptions;
        return this;
    }

    @Override
    public double getSpeed() {
        return speed;
    }

    @Override
    public ParticleBuilder speed(double speed) {
        this.speed = speed;
        return this;
    }

    @Override
    public List<Player> getShowTo() {
        return showTo;
    }

    @Override
    public ParticleBuilder showTo(List<Player> showTo) {
        this.showTo = showTo;
        return this;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public ParticleBuilder location(Location location) {
        this.location = location;
        return this;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Particle getParticle() {
        return particle;
    }

    @Override
    public ParticleBuilder particle(Particle particle) {
        this.particle = particle;
        return this;
    }

    @Override
    public ParticleBuilder count(int count) {
        this.count = count;
        return this;
    }

    public void block(Particle particle, Location location, int count, double x, double y, double z, double speed, BlockData data) {
        if (getShowTo() != null) {
            for (Player p : getShowTo()) {
                p.spawnParticle(particle, location, count, x, y, z, speed, data);
            }
        }
        else { location.getWorld().spawnParticle(particle, location, count, x, y, z, speed, data); }
    }

    public void dust(Particle particle, Location location, int count, double x, double y, double z, double speed, Particle.DustOptions data) {
        if (getShowTo() != null) {
            for (Player p : getShowTo()) {
                p.spawnParticle(particle, location, count, x, y, z, speed, data);
            }
        }
        else { location.getWorld().spawnParticle(particle, location, count, x, y, z, speed, data); }
    }

    public void item(Particle particle, Location location, int count, double x, double y, double z, double speed, ItemStack data) {
        if (getShowTo() != null) {
            for (Player p : getShowTo()) {
                p.spawnParticle(particle, location, count, x, y, z, speed, data);
            }
        }
        else { location.getWorld().spawnParticle(particle, location, count, x, y, z, speed, data); }
    }

    public void spawn(Particle particle, Location location, int count, double x, double y, double z, double speed) {
        if (getShowTo() != null) {
            for (Player p : getShowTo()) {
                p.spawnParticle(particle, location, count, x, y, z, speed);
            }
        }
        else { location.getWorld().spawnParticle(particle, location, count, x, y, z, speed); }
    }

    @Override
    public void build() {
        switch (type) {
            case DUST:
                dust(particle, location, count, offset_x, offset_y, offset_z, speed, dustOptions);
            case ITEM:
                ItemStack itemData = new ItemStack(materialData);
                item(particle, location, count, offset_x, offset_y, offset_z, speed, itemData);
            case BLOCK:
                BlockData blockData = materialData.createBlockData();
                block(particle, location, count, offset_x, offset_y, offset_z, speed, blockData);
            case DEFAULT:
                spawn(particle, location, count, offset_x, offset_y, offset_z, speed);
        }
    }

}
