package uk.mqchinee.archelia.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to define the structure of an inventory.
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface InventoryStructure {

    /**
     * The array of strings representing the structure of the inventory.
     * @return The array of strings representing the inventory structure.
     */
    String[] value() default {"#########", "#########", "#########", "#########", "#########", "#########"};
}
