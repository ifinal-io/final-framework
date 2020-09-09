

package org.finalframework.mybatis.sql.provider;

import org.finalframework.annotation.query.Criterion;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author likly
 * @version 1.0
 * @date 2020-07-20 14:36:23
 * @since 1.0
 */
@Criterion(value = {
        "<if test=\"${value} != null and ${value}.location != null and ${value}.distance != null\">",
        "   <![CDATA[ST_Distance(${column},ST_GeomFromText(#{${value}.location})) &lt; #{${value}.distance}]]>",
        "</if>"
})
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DistanceIn {
    @AliasFor(annotation = Criterion.class, attribute = "property")
    String property() default "";

}
