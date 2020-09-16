

package org.finalframework.annotation.data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate the bean {@linkplain java.lang.reflect.Field property} is a {@link Enum} which will be serialized some ext property in json,
 * such as {@literal xxxName} and {@literal xxxDesc}.
 *
 * @author likly
 * @version 1.0
 * @date 2020-03-25 10:03:14
 * @see org.finalframework.annotation.IEnum
 * @since 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumValue {
    /**
     * 枚举类型
     */
    @SuppressWarnings("rawtypes")
    Class<? extends Enum> value();

    /**
     * return the static {@linkplain java.lang.reflect.Method creator} name of target {@link Enum}.
     *
     * @return the static creator method name.
     * @see com.fasterxml.jackson.annotation.JsonCreator
     */
    String creator() default "valueOf";

    /**
     * return the {@linkplain Class parameter} type of the static {@linkplain java.lang.reflect.Method creator}.
     *
     * @return the parameter type.
     * @see Class#getMethod(String, Class[])
     */
    Class<?> valueType() default Integer.class;

    /**
     * 序列化时增加的枚举描述对应的枚举属性
     */
    String desc() default "desc";
}
