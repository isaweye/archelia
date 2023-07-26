package uk.mqchinee.archelia.annotations;

import uk.mqchinee.archelia.enums.SenderFilter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to provide additional information and configuration for a subcommand.
 * This annotation is applied to classes representing the functionality of a subcommand.
 * It allows setting various properties such as permissions, regex patterns, sender filters,
 * and custom messages for the subcommand.
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SubcommandInfo {

    /**
     * The required permission node for executing the subcommand.
     *
     * @return The permission node as a string.
     */
    String permission() default "";

    /**
     * The custom message to be displayed when a player lacks the required permission to execute the subcommand.
     *
     * @return The permission message as a string.
     */
    String permission_message() default "";

    /**
     * The regular expression pattern used to match arguments for the subcommand.
     *
     * @return The regex pattern as a string.
     */
    String regex() default "";

    /**
     * The custom message to be displayed when the provided arguments do not match the regex pattern.
     *
     * @return The regex error message as a string.
     */
    String regex_message() default "";

    /**
     * The sender filter specifying who can execute the subcommand (e.g., players, console, or both).
     *
     * @return The SenderFilter enum value representing the allowed sender types.
     */
    SenderFilter filter() default SenderFilter.BOTH;

    /**
     * The custom message to be displayed when a player or entity is restricted from executing the subcommand
     * based on the sender filter.
     *
     * @return The sender filter error message as a string.
     */
    String filter_message() default "";

    /**
     * The custom message to be displayed when the subcommand is executed without any arguments.
     *
     * @return The message for an empty subcommand execution as a string.
     */
    String no_args_message() default "";
}
