package uk.mqchinee.archelia.annotations;

import java.lang.annotation.*;

/**
 * Annotation used to define a subcommand.
 * The subcommand can be used as part of a larger command structure.
 * @since 1.0.2
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Repeatable(SubcommandContainer.class)
public @interface Subcommand {

    /**
     * The name of the subcommand.
     *
     * @return The name of the subcommand as a string.
     */
    String value();
}
