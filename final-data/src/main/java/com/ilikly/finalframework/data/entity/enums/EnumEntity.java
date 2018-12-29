package com.ilikly.finalframework.data.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 21:07
 * @since 1.0
 */
public interface EnumEntity<T> {
    @JsonValue
    T getCode();
}
