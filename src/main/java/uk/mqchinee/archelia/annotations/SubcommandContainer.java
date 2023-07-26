package uk.mqchinee.archelia.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to group multiple subcommands within a command container.
 * This annotation is applied to a class representing the container of related subcommands.
 * It allows defining and organizing multiple subcommands under a common parent command.
 * @since 1.0.2
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SubcommandContainer {

    /**
     * An array of Subcommand annotations, representing the subcommands contained within this container.
     *
     * @return The array of Subcommand annotations that belong to this container.
     */
    Subcommand[] value();
}
