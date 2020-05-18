package org.finalframework.document.api.entity;


import lombok.Data;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-18 17:57:58
 * @since 1.0
 */
@Data
public class EntityHolder {
    private String name;
    private Class<?> entity;
    private String desc;
}

