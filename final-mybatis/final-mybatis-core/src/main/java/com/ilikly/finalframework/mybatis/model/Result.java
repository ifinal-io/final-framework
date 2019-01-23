package com.ilikly.finalframework.mybatis.model;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-22 00:36:34
 * @since 1.0
 */
public class Result implements Serializable {
    private final String property;
    private final String column;
    private final Class javaType;
    private final JdbcType jdbcType;
    private final TypeHandler typeHandler;
    private final boolean idResult;

    private Result(Builder builder) {
        this.property = builder.property;
        this.column = builder.column;
        this.javaType = builder.javaType;
        this.jdbcType = builder.jdbcType;
        this.typeHandler = builder.typeHandler;
        this.idResult = builder.idResult;
    }

    public static Builder builder(String property, String column) {
        return new Builder(property, column);
    }

    public String getProperty() {
        return property;
    }

    public String getColumn() {
        return column;
    }

    public Class getJavaType() {
        return javaType;
    }

    public JdbcType getJdbcType() {
        return jdbcType;
    }

    public TypeHandler getTypeHandler() {
        return typeHandler;
    }

    public boolean isIdResult() {
        return idResult;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(idResult ? "<id" : "<result");
        if (property != null) {
            builder.append(" property=\"").append(property).append("\"");
        }
        if (column != null) {
            builder.append(" column=\"").append(column).append("\"");
        }
        if (javaType != null) {
            builder.append(" javaType=\"").append(javaType.getCanonicalName()).append("\"");
        }
        if (jdbcType != null) {
            builder.append(" jdbcType=\"").append(jdbcType.name()).append("\"");
        }
        if (typeHandler != null) {
            builder.append(" typeHandler=\"").append(typeHandler.getClass().getCanonicalName()).append("\"");
        }
        builder.append(">");
        return builder.toString();
    }

    public static class Builder implements com.ilikly.finalframework.core.Builder<Result> {
        private final String property;
        private final String column;
        private Class javaType;
        private JdbcType jdbcType;
        private TypeHandler typeHandler;
        private boolean idResult = false;

        private Builder(String property, String column) {
            this.property = property;
            this.column = column;
        }

        public Builder javaType(Class javaType) {
            this.javaType = javaType;
            return this;
        }

        public Builder jdbcType(JdbcType jdbcType) {
            this.jdbcType = jdbcType;
            return this;
        }

        public Builder typeHandler(TypeHandler typeHandler) {
            this.typeHandler = typeHandler;
            return this;
        }

        public Builder idResult(boolean idResult) {
            this.idResult = idResult;
            return this;
        }

        @Override
        public Result build() {
            return new Result(this);
        }
    }

}
