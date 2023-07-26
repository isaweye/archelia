package uk.mqchinee.archelia.annotations;

import uk.mqchinee.archelia.enums.SenderFilter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SubcommandInfo {

    String permission() default "";

    String permission_message() default "";

    String regex() default "";

    String regex_message() default "";

    SenderFilter filter() default SenderFilter.BOTH;

    String filter_message() default "";

    String no_args_message() default "";
}
