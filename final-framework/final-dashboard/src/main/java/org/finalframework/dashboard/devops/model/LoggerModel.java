package org.finalframework.dashboard.devops.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/22 15:09:23
 * @since 1.0
 */
@Data
public class LoggerModel implements Serializable {
    private String name;
    private String level;
}
