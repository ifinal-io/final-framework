

package org.finalframework.annotation;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 通用枚举接口，为实现在程序中使用枚举常量，而存储时使用枚举码{@link IEnum#getCode()}。
 * <p/>
 * `final-json`会将实现该接口的{@link Enum}转化为对应的{@link IEnum#getCode()}的值，并且在Json序列化JavaBean对象时，对{@link IEnum}
 * 类型的属性，新加一个`xxxName`的属性，值为{@link IEnum#getDesc()}。
 * <p/>
 * 详情查看：<a href="https://final.ilikly.com/json/#stronger-enum">JSON枚举增强</a>
 *
 * @author likly
 * @version 1.0
 * @date 2018-09-26 21:07
 * @since 1.0
 */
public interface IEnum<T> {

    /**
     * 返回表示该枚举常量的code
     *
     * @return 枚举码
     */
    @JsonValue
    T getCode();

    /**
     * 返回对该枚举常量的描述信息
     *
     * @return 描述
     */
    String getDesc();
}
