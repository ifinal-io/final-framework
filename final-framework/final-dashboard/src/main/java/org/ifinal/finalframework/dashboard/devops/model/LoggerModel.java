package org.ifinal.finalframework.dashboard.devops.model;

import java.io.Serializable;
import lombok.Data;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public class LoggerModel implements Serializable {

    private String name;

    private String level;

}
