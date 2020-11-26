package org.ifinal.finalframework.dashboard.context.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public class BeanDefinition implements Serializable {
    private String name;
    private Class<?> targetClass;
}
