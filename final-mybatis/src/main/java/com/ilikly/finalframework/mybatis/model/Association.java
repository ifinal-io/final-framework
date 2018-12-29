package com.ilikly.finalframework.mybatis.model;

import org.apache.ibatis.mapping.FetchType;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-22 01:43:39
 * @since 1.0
 * <p>
 * <!ELEMENT association (constructor?,id*,result*,association*,collection*, discriminator?)>
 * <!ATTLIST association
 * property CDATA #REQUIRED
 * column CDATA #IMPLIED
 * javaType CDATA #IMPLIED
 * jdbcType CDATA #IMPLIED
 * select CDATA #IMPLIED
 * resultMap CDATA #IMPLIED
 * typeHandler CDATA #IMPLIED
 * notNullColumn CDATA #IMPLIED
 * columnPrefix CDATA #IMPLIED
 * resultSet CDATA #IMPLIED
 * foreignColumn CDATA #IMPLIED
 * autoMapping (true|false) #IMPLIED
 * fetchType (lazy|eager) #IMPLIED
 * >
 */
public class Association implements Serializable {
    private final String property;
    private final String column;
    private final Class javaType;
    private final JdbcType jdbcType;
    private final String select;
    private final ResultMap resultMap;
    private final TypeHandler typeHandler;
    private final String columnPrefix;
    private final String foreignColumn;
    private final Boolean autoMapping;
    private final FetchType fetchType;
    private final Result idResult;
    private final List<Result> results;

    private Association(Builder builder) {
        this.property = builder.property;
        this.column = builder.column;
        this.javaType = builder.javaType;
        this.jdbcType = builder.jdbcType;
        this.select = builder.select;
        this.resultMap = builder.resultMap;
        this.typeHandler = builder.typeHandler;
        this.columnPrefix = builder.columnPrefix;
        this.foreignColumn = builder.foreignColumn;
        this.autoMapping = builder.autoMapping;
        this.fetchType = builder.fetchType;
        this.idResult = builder.idResult;
        this.results = builder.results.isEmpty() ? null : Collections.unmodifiableList(builder.results);
    }

    public static Builder builder(String property) {
        return new Builder(property);
    }

    public static class Builder implements com.ilikly.finalframework.core.Builder<Association> {
        private final String property;
        private final List<Result> results = new ArrayList<>();
        private String column;
        private Class javaType;
        private JdbcType jdbcType;
        private String select;
        private ResultMap resultMap;
        private TypeHandler typeHandler;
        private String columnPrefix;
        private String foreignColumn;
        private Boolean autoMapping;
        private FetchType fetchType;
        private Result idResult;

        private Builder(String property) {
            this.property = property;
        }

        public Builder column(String column) {
            this.column = column;
            return this;
        }

        public Builder javaType(Class javaType) {
            this.javaType = javaType;
            return this;
        }

        public Builder addResult(Result result) {
            this.results.add(result);
            if (result.isIdResult()) {
                this.idResult = result;
            }
            return this;
        }

        @Override
        public Association build() {
            return new Association(this);
        }
    }

}
