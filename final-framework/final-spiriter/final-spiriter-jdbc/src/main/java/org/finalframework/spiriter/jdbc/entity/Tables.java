package org.ifinal.finalframework.spiriter.jdbc.entity;


import lombok.Data;
import org.ifinal.finalframework.data.annotation.*;
import org.ifinal.finalframework.spiriter.jdbc.contants.SchemaConstants;

import java.util.Date;

/**
 * @author likly
 * @version 1.0.0
 *
 * @since 1.0.0
 */
@Data
@Schema(SchemaConstants.INFORMATION_SCHEMA)
@UpperCase
public class Tables implements IEntity<Long> {
    private static final long serialVersionUID = -3463727852894346634L;
    @PrimaryKey
    @ReadOnly
    @Deprecated
    private Long id;
    @Column("TABLE_CATALOG")
    private String catalog;
    @Column("TABLE_SCHEMA")
    private String schema;
    @Column("TABLE_NAME")
    private String name;
    @Column("TABLE_TYPE")
    private String type;
    @Column("ENGINE")
    private String engine;
    @Column("VERSION")
    private Integer version;
    @Column("ROW_FORMAT")
    private String rowFormat;
    @Column("TABLE_ROWS")
    private Long rows;
    @Column("AVG_ROW_LENGTH")
    private Long avgRowLength;
    @Column("DATA_LENGTH")
    private Long dataLength;
    @Column("MAX_DATA_LENGTH")
    private Long maxDataLength;
    @Column("INDEX_LENGTH")
    private Long indexLength;
    @Column("DATA_FREE")
    private Long dataFree;
    @Column("AUTO_INCREMENT")
    private Long autoIncrement;
    @Column("CREATE_TIME")
    private Date createTime;
    @Column("UPDATE_TIME")
    private Date updateTime;
    @Column("CHECK_TIME")
    private Date checkTime;
    @Column("TABLE_COLLATION")
    private String collation;
    @Column("CHECKSUM")
    private Long checkSum;
    @Column("CREATE_OPTIONS")
    private String options;
    @Column("TABLE_COMMENT")
    private String comment;
}

