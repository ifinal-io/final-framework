package org.finalframework.document.api.entity;


import lombok.Data;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-14 12:18:54
 * @since 1.0
 */
@Data
public class ResultMapping {
    private String name;
    private Class<?> type;
    private Type genericType;
    private List<ResultMapping> resultMappings;
    private boolean required = true;
    private List<Object> options;
    private Object value;
}

