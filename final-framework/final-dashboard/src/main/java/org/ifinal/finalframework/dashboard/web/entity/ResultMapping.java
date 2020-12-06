package org.ifinal.finalframework.dashboard.web.entity;


import lombok.Data;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public class ResultMapping implements Serializable {
    private String name;
    private Class<?> type;
    private boolean required = true;
}

