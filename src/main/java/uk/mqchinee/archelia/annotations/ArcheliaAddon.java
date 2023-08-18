package uk.mqchinee.archelia.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to mark a class as an Archelia addon.
 * Addons provide additional functionality or features to the Archelia.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ArcheliaAddon {

    /**
     * Provides a description of the Archelia addon.
     *
     * @return The description of the addon.
     */
    String description();
}
