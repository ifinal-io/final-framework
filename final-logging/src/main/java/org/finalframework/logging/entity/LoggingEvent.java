package org.finalframework.logging.entity;

import lombok.Data;
import org.finalframework.data.annotation.Entity;
import org.finalframework.data.annotation.PrimaryKey;
import org.finalframework.data.entity.IEntity;

import java.util.Date;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-16 20:55:40
 * @since 1.0
 */
@Data
@Entity
public class LoggingEvent implements IEntity<Long> {
    @PrimaryKey
    private Long id;
    private String host;
    private Integer port;
    private Integer pid;
    private String thread;
    private String trace;
    private Date timestamp;
    private String level;
    private String logger;
    private String method;
    private Integer lineNumber;
    private String message;
    private String exception;
}
