package org.ifinal.finalframework.annotation.data;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.ifinal.finalframework.annotation.core.IEntity;
import org.springframework.lang.NonNull;

/**
 * Annotate the {@linkplain IEntity entity} mapping a special table in datasource by {@link #value()}.
 *
 * <p>Use example:</p>
 * <pre class="code">
 *     &#64;Table("t_person")
 *     public class Person extends AbsEntity{
 *
 *     }
 * </pre>
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Table {

    /**
     * return the special table name when the {@link Class#getSimpleName()} of {@linkplain IEntity entity} can not mapped the table.
     *
     * @return the special table name.
     */
    @NonNull
    String value() default "";

}
