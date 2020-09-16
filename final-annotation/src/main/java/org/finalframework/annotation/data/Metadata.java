package org.finalframework.annotation.data;


import lombok.Data;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2020-07-17 14:31:00
 * @since 1.0
 */
@Data
public class Metadata implements Serializable {
    private String property;
    private String column;
    private String value;
    private Class<?> javaType;
    private Class<? extends org.apache.ibatis.type.TypeHandler<?>> typeHandler;
}

