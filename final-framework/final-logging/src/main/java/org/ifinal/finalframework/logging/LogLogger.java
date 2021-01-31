package org.ifinal.finalframework.logging;

import java.io.Serializable;
import lombok.Data;

/**
 * LogLogger.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public class LogLogger implements Serializable {

    private String file;

    private String rollingFile;

}
