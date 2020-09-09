

package org.finalframework.annotation.query;

import org.springframework.lang.NonNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author likly
 * @version 1.0
 * @date 2020-07-20 13:25:06
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface Function {
    String value();

    Attribute[] attributes() default {};

    Class<? extends FunctionHandler> handler() default FunctionHandler.class;

    @FunctionalInterface
    interface FunctionHandler {
        String handle(@NonNull Function function, @NonNull Metadata metadata);
    }

}
