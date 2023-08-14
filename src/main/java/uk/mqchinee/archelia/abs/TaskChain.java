package uk.mqchinee.archelia.abs;

import lombok.Getter;
import uk.mqchinee.archelia.enums.Time;
import uk.mqchinee.archelia.utils.RunUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a chain of tasks to be executed with specified delays.
 */
public class TaskChain {

    @Getter
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
    public void start() {
        tasks.forEach(task ->
                RunUtils.runLater(task.runnable(), task.ticks(), Time.IN_TICKS)
        );
    }
}
