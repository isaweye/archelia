package uk.mqchinee.featherlib.builders;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import uk.mqchinee.featherlib.enums.DataType;

import java.util.List;

public class ParticleBuilder {

    private double offset_y = 0.1;
    private double offset_z = 0.1;
    private Material materialData;
    private Particle.DustOptions dustOptions;
    private double speed = 0;
    private List<Player> showTo = null;
    private Location location;
    private int count = 3;
    private Particle particle;
    private double offset_x = 0.1;
    private DataType type;

    public ParticleBuilder(DataType type) {
        this.type = type;
    }

    public double getOffsetX() {
        return offset_x;
    }

    public ParticleBuilder offsetX(double offset_x) {
        this.offset_x = offset_x;
        return this;
    }

    public double getOffsetY() {
        return offset_y;
    }

    public ParticleBuilder offsetY(double offset_y) {
        this.offset_y = offset_y;
        return this;
    }

    public double getOffsetZ() {
        return offset_z;
    }

    public ParticleBuilder offsetZ(double offset_z) {
        this.offset_z = offset_z;
        return this;
    }

    public Material getMaterialData() {
        return materialData;
    }

    public ParticleBuilder materialData(Material materialData) {
        this.materialData = materialData;
        return this;
    }

    public Particle.DustOptions getDustOptions() {
        return dustOptions;
    }

    public ParticleBuilder dustOptions(Particle.DustOptions dustOptions) {
        this.dustOptions = dustOptions;
        return this;
    }

    public double getSpeed() {
        return speed;
    }

    public ParticleBuilder speed(double speed) {
        this.speed = speed;
        return this;
    }

    public List<Player> getShowTo() {
        return showTo;
    }

    public ParticleBuilder showTo(List<Player> showTo) {
        this.showTo = showTo;
        return this;
    }

    public Location getLocation() {
        return location;
    }

    public ParticleBuilder location(Location location) {
        this.location = location;
        return this;
    }

    public int getCount() {
        return count;
    }

    public Particle getParticle() {
        return particle;
    }

    public ParticleBuilder particle(Particle particle) {
        this.particle = particle;
        return this;
    }

    public ParticleBuilder count(int count) {
        this.count = count;
        return this;
    }

    private void spawn(Particle particle, Location location, int count, double x, double y, double z, double speed, BlockData data) {
        if (getShowTo() != null) {
            for (Player p : getShowTo()) {
                p.spawnParticle(particle, location, count, x, y, z, speed, data);
            }
        }
        else { location.getWorld().spawnParticle(particle, location, count, x, y, z, speed, data); }
    }

    private void spawn(Particle particle, Location location, int count, double x, double y, double z, double speed, Particle.DustOptions data) {
        if (getShowTo() != null) {
            for (Player p : getShowTo()) {
                p.spawnParticle(particle, location, count, x, y, z, speed, data);
            }
        }
        else { location.getWorld().spawnParticle(particle, location, count, x, y, z, speed, data); }
    }

    private void spawn(Particle particle, Location location, int count, double x, double y, double z, double speed, ItemStack data) {
        if (getShowTo() != null) {
            for (Player p : getShowTo()) {
                p.spawnParticle(particle, location, count, x, y, z, speed, data);
            }
        }
        else { location.getWorld().spawnParticle(particle, location, count, x, y, z, speed, data); }
    }

    public void display() {
        switch (type) {
            case DUST:
                spawn(particle, location, count, offset_x, offset_y, offset_z, speed, dustOptions);
            case ITEM:
                ItemStack itemData = new ItemStack(materialData);
                spawn(particle, location, count, offset_x, offset_y, offset_z, speed, itemData);
            case BLOCK:
                BlockData blockData = materialData.createBlockData();
                spawn(particle, location, count, offset_x, offset_y, offset_z, speed, blockData);
        }
    }

}
