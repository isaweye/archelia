package uk.mqchinee.lanterncore.annotations;

import uk.mqchinee.lanterncore.enums.SenderFilter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandInfo {
    String permission() default "";
    String permission_message() default "";
    SenderFilter filter() default SenderFilter.BOTH;
    String filter_message() default "";
}
