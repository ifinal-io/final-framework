/*
 * Copyright 2020-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.spiriter.jdbc.entity;


import lombok.Data;

import org.ifinalframework.data.annotation.*;
import org.ifinalframework.spiriter.jdbc.contants.SchemaConstants;

/**
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
@Schema(SchemaConstants.INFORMATION_SCHEMA)
@UpperCase
public class Columns implements IEntity<Long> {
    private static final long serialVersionUID = -8388951644802121940L;
    @PrimaryKey
    @WriteOnly
    @Deprecated
    private Long id;
    @Column("TABLE_CATALOG")
    private String catalog;
    @Column("TABLE_SCHEMA")
    private String schema;
    @Column("TABLE_NAME")
    private String table;
    @Column("COLUMN_NAME")
    private String name;
    @Column("ORDINAL_POSITION")
    private Integer ordinal;
    @Column("COLUMN_DEFAULT")
    private String defaultValue;
    @Column("IS_NULLABLE")
    private String nullable;
    @Column("DATA_TYPE")
    private String dataType;
    @Column("CHARACTER_MAXIMUM_LENGTH")
    private Long characterMaximumLength;
    @Column("CHARACTER_OCTET_LENGTH")
    private Long characterOctetLength;
    @Column("NUMERIC_PRECISION")
    private Long numericPrecision;
    @Column("NUMERIC_SCALE")
    private Long numericScale;
    @Column("DATETIME_PRECISION")
    private Long datetimePrecision;
    @Column("CHARACTER_SET_NAME")
    private String characterSetName;
    @Column("COLLATION_NAME")
    private String collation;
    @Column("COLUMN_TYPE")
    private String type;
    @Column("COLUMN_KEY")
    private String key;
    @Column("EXTRA")
    private String extra;
    @Column("COLUMN_COMMENT")
    private String comment;

}

