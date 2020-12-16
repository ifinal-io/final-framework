package org.ifinal.finalframework.annotation.query;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface Function {

    String value();

    Attribute[] attributes() default {};

    Class<? extends FunctionHandler> handler() default FunctionHandler.class;

    /**
     * FunctionHandler.
     */
    @FunctionalInterface
    interface FunctionHandler {

        String handle(@NonNull Function function, @NonNull CriterionAttributes metadata);

    }

}
