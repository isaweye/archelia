package uk.mqchinee.archelia.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Entity;
import uk.mqchinee.archelia.managers.utils.FloatingMessageFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FloatingMessage {
    private final static String floatingMessageTag = "floating_message_line_entity_tag";

    private final FloatingMessageFormatter messageFormatter;
    private final int minDuration;
    private final int maxDuration;
    private final int readSpeed;

    public FloatingMessage (
            FloatingMessageFormatter messageFormatter,
            int minDuration,
            int maxDuration,
            int readSpeed
    ) {
        this.messageFormatter = messageFormatter;
        this.minDuration = minDuration;
        this.maxDuration = maxDuration;
        this.readSpeed = readSpeed;
    }

    private Entity getCurrentMount(Entity entity) {
        return entity.getPassengers().stream()
                .filter(e -> e.getScoreboardTags().contains(floatingMessageTag))
                .findFirst().orElse(null);
    }

    private int computeDuration(String chatMessage) {
        int readingDuration = chatMessage.length() * readSpeed;
        return Math.max(minDuration, Math.min(maxDuration, readingDuration));
    }

    public void spawnOn(Entity entity, String chatMessage) {
            AreaEffectCloud currentLinesMount = (AreaEffectCloud) getCurrentMount(entity);
            int currentLinesMountDuration = currentLinesMount == null ? 0 :
                    currentLinesMount.getDuration() - currentLinesMount.getTicksLived();

            int computedDuration = computeDuration(chatMessage);
            int duration = Math.max(computedDuration, currentLinesMountDuration);



            List<String> messageLines = messageFormatter.format(chatMessage);
            Collections.reverse(messageLines);

            List<Entity> lines = new ArrayList<>();
            for (String messageLine : messageLines) {
                Location location = entity.getLocation().add(0, 1, 0);
                AreaEffectCloud particle = entity.getWorld().spawn(location, AreaEffectCloud.class);
                particle.setParticle(Particle.BLOCK_CRACK, Material.AIR.createBlockData());
                particle.setRadius(0);
                particle.setWaitTime(0);
                particle.setDuration(duration);
                particle.setCustomNameVisible(true);
                particle.setCustomName(messageLine);
                particle.addScoreboardTag(floatingMessageTag);
                lines.add(particle);
            }

            if (currentLinesMount != null) lines.add(currentLinesMount);

            Entity linesMount = entity;
            for (Entity line : lines) {
                linesMount.addPassenger(line);
                linesMount = line;
            }
    }

    public void despawnAll() {
        Bukkit.getWorlds()
                .forEach(w -> w.getEntities().stream()
                        .filter(e -> e.getScoreboardTags().contains(floatingMessageTag))
                        .forEach(Entity::remove));
    }
}
