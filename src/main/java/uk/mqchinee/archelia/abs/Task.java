package uk.mqchinee.archelia.abs;

/**
 * Represents a task to be executed after a specified number of ticks.
 */
public record Task(int ticks, Runnable runnable) {
}
