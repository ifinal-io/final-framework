package org.finalframework.annotation.data;

import org.finalframework.annotation.IEntity;
import org.springframework.lang.NonNull;

import java.lang.annotation.*;

/**
 * Annotate the {@linkplain IEntity entity} mapping a special table in datasource by {@link #value()}.
 *
 * <pre>
 *     <code>
 *         &#64;Table("t_person")
 *         public class Person extends AbsEntity{
 *
 *         }
 *     </code>
 * </pre>
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-15 15:14
 * @since 1.0
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
    String value();
}
