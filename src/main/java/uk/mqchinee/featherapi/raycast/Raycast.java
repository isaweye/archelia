package uk.mqchinee.featherapi.raycast;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import uk.mqchinee.featherapi.enums.Time;
import uk.mqchinee.featherapi.utils.RunUtils;

import java.util.ArrayList;
import java.util.Collection;

//    @EventHandler
//    public void whenClick(PlayerInteractEvent e) {
//        Player player = e.getPlayer();
//
//        ItemStack i = e.getPlayer().getItemInHand();
//        if (i == null || i.getType() == Material.AIR)
//            return;
//
//        if (e.getAction() == Action.LEFT_CLICK_AIR && i.hasItemMeta() && i.getItemMeta().hasDisplayName() && i.getItemMeta().getDisplayName().equals("gun")) {
//            Raycast raycast = new Raycast(e.getPlayer().getEyeLocation(), 100);
//            raycast.addPassthroughMaterial(Material.LEAVES);
//            raycast.setOwner(player);
//            raycast.setShowRayCast(true);
//
//            if (raycast.compute(RaycastType.ENTITY_AND_BLOCK)) {
//                player.sendMessage("§aRaycast hurt something");
//
//                Location hurtLocation = raycast.getHurtLocation();
//
//                player.sendMessage("§cRaycast hurt "+((raycast.getHurtBlock() != null) ? "a block " : "an entity") + " at X:"+hurtLocation.getBlockX()+" Y: "+hurtLocation.getBlockY()+" Z:"+hurtLocation.getBlockZ());
//            } else {
//                player.sendMessage("§6Raycast hurt nothing");
//            }
//
//            e.setCancelled(true);
//        }
//    }

public class Raycast {

    private final double divider = 100.0D;

    private ArrayList<Material> passthroughMaterials = new ArrayList<>();

    private ArrayList<Location> testedLocations = new ArrayList<>();

    private World world;

    private double x;

    private double y;

    private double z;

    private double yaw;

    private double pitch;

    private double size;

    private RaycastType rayCastType;

    private Entity hurtEntity;

    private Block hurtBlock;

    private Location hurtLocation;

    private boolean showRayCast = false;

    private Entity owner;

    private Location rayCastLocation;

    private final RaycastMath math = new RaycastMath();
    private final RunUtils r = new RunUtils();

    public Raycast(Location loc, double size) {
        this(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch(), size);
    }

    public Raycast(World world, double x, double y, double z, double yaw, double pitch, double size) {
        addPassthroughMaterial(Material.AIR);
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.size = size;
    }

    public boolean compute(RaycastType rayCastType, Particle particle, int add, int wait, Time type) {
        this.testedLocations.clear();
        final int[] length = {0};
        computeLocation(new Vector(0.0D, 0.0D, length[0] + 50.0D));
        if (rayCastType == RaycastType.BLOCK) {
            this.rayCastType = RaycastType.BLOCK;
            final int[] finalLength = {length[0]};
            r.repeating(() -> {
                if (this.passthroughMaterials.contains(this.rayCastLocation.getBlock().getType()) && finalLength[0] <= this.size * 100.0D) {
                    this.testedLocations.add(this.rayCastLocation);
                    length[0] = length[0] + add;
                    computeLocation(new Vector(0.0D, 0.0D, length[0] + 50.0D));
                    if (this.showRayCast)
                        this.world.spawnParticle(particle, this.rayCastLocation.getX(), this.rayCastLocation.getY(), this.rayCastLocation.getZ(), 0, 0.0D, 0.0D, 0.0D);
                }
            }, wait, wait, type);
            if (!this.passthroughMaterials.contains(this.rayCastLocation.getBlock().getType())) {
                this.hurtBlock = this.rayCastLocation.getBlock();
                this.hurtLocation = this.rayCastLocation;
                return true;
            }
        } else if (rayCastType == RaycastType.ENTITY) {
            this.rayCastType = RaycastType.ENTITY;
            final Collection<Entity>[] entities = new Collection[]{this.world.getNearbyEntities(this.rayCastLocation, 0.01D, 0.01D, 0.01D)};
            r.repeating(() -> {
                if ((entities[0].size() <= 0 || entities[0].contains(this.owner)) && length[0] <= this.size * 100.0D) {
                    this.testedLocations.add(this.rayCastLocation);
                    length[0]++;
                    computeLocation(new Vector(0.0D, 0.0D, length[0] + 50.0D));
                    entities[0] = this.world.getNearbyEntities(this.rayCastLocation, 0.01D, 0.01D, 0.01D);
                    if (this.showRayCast)
                        this.world.spawnParticle(particle, this.rayCastLocation.getX(), this.rayCastLocation.getY(), this.rayCastLocation.getZ(), 0, 0.0D, 0.0D, 0.0D);
                }
            }, wait, wait, type);
            if (entities[0].size() > 0) {
                for (Entity entity : entities[0]) {
                    this.hurtEntity = entity;
                    this.hurtLocation = this.rayCastLocation;
                }
                return true;
            }
        } else if (rayCastType == RaycastType.ENTITY_AND_BLOCK) {
            final Collection<Entity>[] entities = new Collection[]{this.world.getNearbyEntities(this.rayCastLocation, 0.01D, 0.01D, 0.01D)};
            r.repeating(() -> {
                if (this.passthroughMaterials.contains(this.rayCastLocation.getBlock().getType()) && (entities[0].size() <= 0 || entities[0].contains(this.owner)) && length[0] <= this.size * 100.0D) {
                    this.testedLocations.add(this.rayCastLocation);
                    length[0]++;
                    computeLocation(new Vector(0.0D, 0.0D, length[0] + 50.0D));
                    entities[0] = this.world.getNearbyEntities(this.rayCastLocation, 0.01D, 0.01D, 0.01D);
                    if (this.showRayCast)
                        this.world.spawnParticle(particle, this.rayCastLocation.getX(), this.rayCastLocation.getY(), this.rayCastLocation.getZ(), 0, 0.0D, 0.0D, 0.0D);
                }
            }, wait, wait, type);
            if (!this.passthroughMaterials.contains(this.rayCastLocation.getBlock().getType())) {
                this.rayCastType = RaycastType.BLOCK;
                this.hurtBlock = this.rayCastLocation.getBlock();
                this.hurtLocation = this.rayCastLocation;
                return true;
            }
            if (entities[0].size() > 0) {
                this.rayCastType = RaycastType.ENTITY;
                for (Entity entity : entities[0]) {
                    this.hurtEntity = entity;
                    this.hurtLocation = this.rayCastLocation;
                }
                return true;
            }
        }
        return false;
    }

    private void computeLocation(Vector rayCastPos) {
        rayCastPos = math.rotate(rayCastPos, this.yaw, this.pitch);
        this.rayCastLocation = (new Location(this.world, this.x, this.y, this.z)).clone().add(rayCastPos.getX() / 100.0D, rayCastPos.getY() / 100.0D, rayCastPos.getZ() / 100.0D);
    }

    public ArrayList<Material> getPassthroughMaterials() {
        return this.passthroughMaterials;
    }

    public void setPassthroughMaterials(ArrayList<Material> passthroughMaterials) {
        this.passthroughMaterials = passthroughMaterials;
    }

    public void addPassthroughMaterial(Material mat) {
        if (!this.passthroughMaterials.contains(mat))
            this.passthroughMaterials.add(mat);
    }

    public ArrayList<Location> getTestedLocations() {
        return this.testedLocations;
    }

    public World getWorld() {
        return this.world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return this.z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getYaw() {
        return this.yaw;
    }

    public void setYaw(double yaw) {
        this.yaw = yaw;
    }

    public double getPitch() {
        return this.pitch;
    }

    public void setPitch(double pitch) {
        this.pitch = pitch;
    }

    public double getSize() {
        return this.size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public RaycastType getRayCastType() {
        return this.rayCastType;
    }

    public void setRayCastType(RaycastType rayCastType) {
        this.rayCastType = rayCastType;
    }

    public Entity getHurtEntity() {
        return this.hurtEntity;
    }

    public void setHurtEntity(Entity hurtEntity) {
        this.hurtEntity = hurtEntity;
    }

    public Block getHurtBlock() {
        return this.hurtBlock;
    }

    public void setHurtBlock(Block hurtBlock) {
        this.hurtBlock = hurtBlock;
    }

    public Location getHurtLocation() {
        return this.hurtLocation;
    }

    public void setHurtLocation(Location hurtLocation) {
        this.hurtLocation = hurtLocation;
    }

    public boolean isShowRayCast() {
        return this.showRayCast;
    }

    public void setShowRayCast(boolean showRayCast) {
        this.showRayCast = showRayCast;
    }

    public double getDivider() {
        return 100.0D;
    }

    public Entity getOwner() {
        return this.owner;
    }

    public void setOwner(Entity owner) {
        this.owner = owner;
    }

    public enum RaycastType {
        ENTITY_AND_BLOCK, ENTITY, BLOCK;
    }
}
