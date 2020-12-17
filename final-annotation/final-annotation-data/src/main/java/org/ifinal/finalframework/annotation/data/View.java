package org.ifinal.finalframework.annotation.data;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 视图，根据视图生成SQL。 实现根据不同的实图查询不同的列的需求。 功能参考 {@link com.fasterxml.jackson.annotation.JsonView}
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface View {

    Class<?>[] value();

}
