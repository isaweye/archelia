package uk.mqchinee.archelia.annotations;

import uk.mqchinee.archelia.enums.SenderFilter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to provide additional information related to sub-commands.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SubCommandInfo {

    /**
     * The permission required to execute the annotated sub-command.
     *
     * @return The permission string.
     */
    String permission() default "";

    /**
     * The error message to display if the sub-command execution is denied due to lacking permission.
     *
     * @return The permission error message.
     */
    String permission_message() default "";

    /**
     * The regular expression used to match the sub-command name in the input.
     *
     * @return The regular expression string.
     */
    String regex() default "";

    /**
     * The error message to display if the sub-command name doesn't match the provided regular expression.
     *
     * @return The regular expression error message.
     */
    String regex_message() default "";

    /**
     * The sender filter type for the annotated sub-command.
     *
     * @return The sender filter type.
     */
    SenderFilter filter() default SenderFilter.BOTH;

    /**
     * The error message to display if the sub-command execution is denied due to sender filter.
     *
     * @return The sender filter error message.
     */
    String filter_message() default "";

    /**
     * The error message to display if the sub-command is called without any arguments when arguments are required.
     *
     * @return The no-args error message.
     */
    String no_args_message() default "";
}
