package com.ilikly.finalframework.mybatis.model;

import org.apache.ibatis.mapping.StatementType;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-22 16:34:00
 * @since 1.0
 */
public class SelectKey implements Serializable {
    private Class resultType;
    private StatementType statementType;
    private String keyProperty;
    private String keyColumn;
    private Boolean before;
}
