package org.ifinal.finalframework.annotation.data;

import java.io.Serializable;
import lombok.Data;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public class Metadata implements Serializable {

    private String property;

    private String column;

    private String value;

    private Class<?> javaType;

    private Class<? extends org.apache.ibatis.type.TypeHandler> typeHandler;

}

