package uk.mqchinee.archelia.annotations;

import uk.mqchinee.archelia.enums.SenderFilter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to provide additional information related to commands.
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandInfo {

    /**
     * The permission required to execute the annotated command.*
     *
     * @return The permission string.
     */
    String permission() default "";

    /**
     * The error message to display if the command execution is denied due to lacking permission.
     *
     * @return The permission error message.
     */
    String permission_message() default "";

    /**
     * The sender filter type for the annotated command.
     *
     * @return The sender filter type.
     */
    SenderFilter filter() default SenderFilter.BOTH;

    /**
     * The error message to display if the command execution is denied due to sender filter.
     *
     * @return The sender filter error message.
     */
    String filter_message() default "";
}
