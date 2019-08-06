package org.finalframework.data.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 通用枚举接口，为实现在程序中使用枚举常量，而存储时使用枚举码{@link IEnum#getCode()}。
 *
 * @author likly
 * @version 1.0
 * @date 2018-09-26 21:07
 * @since 1.0
 */
public interface IEnum<T> {
    @JsonValue
    T getCode();
}
