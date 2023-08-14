package uk.mqchinee.archelia.impl;

import java.util.function.Consumer;

/**
 * Interface for implementing an update checker that can be used to check for updates
 * of a plugin.
 */
public interface UpdateChecker {

    /**
     * Checks for updates and invokes the appropriate callbacks based on the comparison result.
     */
    void check();

    /**
     * Gets the current version of the plugin.
     *
     * @return The current version as a string.
     */
    String getVersion();

    /**
     * Gets the consumer callback that will be executed when a newer version is found.
     *
     * @return The consumer callback that takes the latest version as a string parameter.
     */
    Consumer<String> getOnSuccess();

    /**
     * Gets the runnable callback that will be executed when an update check fails
     * or encounters an error.
     *
     * @return The runnable callback to be executed on failure.
     */
    Runnable getOnFailure();

    /**
     * Gets the runnable callback that will be executed when the plugin
     * is already up-to-date (i.e., the latest version matches the current version).
     *
     * @return The runnable callback to be executed when the plugin is up-to-date.
     */
    Runnable getOnLatest();

    /**
     * Sets the consumer callback that will be executed when a newer version is found.
     *
     * @param onSuccess The consumer callback that takes the latest version as a string parameter.
     */
    void setOnSuccess(Consumer<String> onSuccess);

    /**
     * Sets the runnable callback that will be executed when an update check fails
     * or encounters an error.
     *
     * @param onFailure The runnable callback to be executed on failure.
     */
    void setOnFailure(Runnable onFailure);

    /**
     * Sets the runnable callback that will be executed when the plugin
     * is already up-to-date (i.e., the latest version matches the current version).
     *
     * @param onLatest The runnable callback to be executed when the plugin is up-to-date.
     */
    void setOnLatest(Runnable onLatest);
}
