

package org.finalframework.annotation.data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 被 {@link EnumValue} 标记的属性 {@link java.lang.reflect.Field} 在使用 {@literal json} 序列化时，会额外地序列化两个扩展属性 {@literal xxxName} 和 {@literal xxxDesc}。
 *
 * @author likly
 * @version 1.0
 * @date 2020-03-25 10:03:14
 * @since 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumValue {
    /**
     * 枚举类型
     */
    Class<? extends Enum<?>> value();

    /**
     * 获取枚举实例的静态方法名称，有且只有一个参数，参数类型为 {@link #valueType()}
     */
    String creator() default "valueOf";

    /**
     * 获取枚举实例的参数类型
     */
    Class<?> valueType() default Integer.class;

    /**
     * 序列化时增加的枚举描述对应的枚举属性
     */
    String desc() default "desc";
}
