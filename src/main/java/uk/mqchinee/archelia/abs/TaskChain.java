package uk.mqchinee.archelia.abs;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a chain of tasks to be executed with specified delays.
 */
@Getter
public class TaskChain {

    private final List<Task> tasks = new ArrayList<>();

    /**
     * Adds a task to the chain with a specified delay.
     *
     * @param ticks    The delay in ticks before the task should run.
     * @param runnable The task to be executed.
     */
    public void add(int ticks, Runnable runnable) {
        getTasks().add(new Task(ticks, runnable));
    }

    /**
     * Starts the task chain, executing each task with its specified delay.
     */
    public void start(Plugin plugin) {
        tasks.forEach(task -> {
            Bukkit.getScheduler().runTaskLater(plugin, task.runnable(), task.ticks());
        });
    }
}
