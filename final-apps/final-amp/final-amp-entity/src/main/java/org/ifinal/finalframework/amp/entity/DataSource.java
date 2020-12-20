package org.ifinal.finalframework.amp.entity;

import java.util.Properties;
import lombok.Getter;
import lombok.Setter;
import org.ifinal.finalframework.annotation.data.AbsRecord;

/**
 * DataSource.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@Setter
public class DataSource extends AbsRecord {

    private String project;

    private String application;

    private String name;

    private String host;

    private Integer port;

    private String username;

    private String password;

    private String schema;

    private String driver;

    private Properties props;

}
