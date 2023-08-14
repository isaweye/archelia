package uk.mqchinee.archelia.abs;

import lombok.Getter;
import uk.mqchinee.archelia.enums.Time;
import uk.mqchinee.archelia.utils.RunUtils;

import java.util.ArrayList;
import java.util.List;

public class TaskChain {

    @Getter private final List<Task> tasks = new ArrayList<>();

    public void add(int ticks, Runnable runnable) {
        getTasks().add(new Task(ticks, runnable));
    }

    public void start() {
        tasks.forEach((task -> RunUtils.runLater(task.runnable(), task.ticks(), Time.IN_TICKS)
        ));
    }

}
