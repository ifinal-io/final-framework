package org.finalframework.dashboard.context.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/21 19:17:28
 * @since 1.0
 */
@Data
public class BeanDefinition implements Serializable {
    private String name;
    private Class<?> targetClass;
}
