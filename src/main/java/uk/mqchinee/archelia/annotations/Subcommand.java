package uk.mqchinee.archelia.annotations;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Repeatable(SubcommandContainer.class)
public @interface Subcommand {
    String value();
}
