package org.ifinal.finalframework.annotation.data;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A marker annotation to annotated the {@link PrimaryKey} use auto incr.
 *
 * <pre class="code">
 *      public interface Mapper{
 *          &#64;Options(useGeneratedKeys=true,keyProperty="id",keyColumn="id")
 *          public int insert(IEntity entity)
 *      }
 * </pre>
 *
 * @author likly
 * @version 1.0.0
 * @see PrimaryKey
 * @see org.apache.ibatis.annotations.Options
 * @since 1.0.0
 */
@Documented
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoInc {

}
