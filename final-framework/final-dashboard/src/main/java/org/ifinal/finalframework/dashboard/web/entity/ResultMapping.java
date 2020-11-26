package org.ifinal.finalframework.dashboard.web.entity;


import lombok.Data;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
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

