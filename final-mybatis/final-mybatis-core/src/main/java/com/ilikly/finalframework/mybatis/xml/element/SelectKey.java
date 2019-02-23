package com.ilikly.finalframework.mybatis.xml.element;

import lombok.Getter;
import org.apache.ibatis.mapping.StatementType;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-22 16:34:00
 * @since 1.0
 */
@Getter
public class SelectKey implements Serializable {
    private final Class resultType;
    private final StatementType statementType;
    private final String keyProperty;
    private final String keyColumn;
    private final Boolean before;

    public SelectKey(Builder builder) {
        this.resultType = builder.resultType;
        this.statementType = builder.statementType;
        this.keyProperty = builder.keyProperty;
        this.keyColumn = builder.keyColumn;
        this.before = builder.before;
    }

    public static class Builder implements com.ilikly.finalframework.core.Builder<SelectKey> {

        private Class resultType;
        private StatementType statementType;
        private String keyProperty;
        private String keyColumn;
        private Boolean before;

        @Override
        public SelectKey build() {
            return new SelectKey(this);
        }
    }

}
