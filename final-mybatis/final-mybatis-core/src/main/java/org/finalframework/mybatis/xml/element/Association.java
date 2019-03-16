package org.finalframework.mybatis.xml.element;

import lombok.Getter;
import org.apache.ibatis.mapping.FetchType;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.finalframework.core.Assert;
import org.finalframework.core.Streamable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

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
@Getter
public class Association implements Element, Streamable<Element>, Iterable<Element>, Serializable {
    private static final String NAME = "association";
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
    private final List<Element> elements;

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
        this.results = builder.results.isEmpty() ? Collections.emptyList() : Collections.unmodifiableList(builder.results);
        this.elements = builder.elements.isEmpty() ? Collections.emptyList() : Collections.unmodifiableList(builder.elements);
    }

    public static Builder builder(String property) {
        return new Builder(property);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("<association");

        if (Assert.nonEmpty(property)) {
            builder.append(" property=\"").append(property).append("\"");
        }

        if (Assert.nonNull(javaType)) {
            builder.append(" javaType=\"").append(javaType.getCanonicalName()).append("\"");
        }

        if (Assert.nonNull(typeHandler)) {
            builder.append(" typeHandler=\"").append(typeHandler.getClass().getCanonicalName()).append("\"");
        }

        if (Assert.nonEmpty(select)) {
            builder.append(" select=\"").append(select).append("\"");
        }

        if (Assert.nonEmpty(column)) {
            builder.append(" column=\"").append(column).append("\"");
        }

        builder.append("></association>");
        return builder.toString();
    }

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public ElementType type() {
        return ElementType.ASSOCIATION;
    }

    @Override
    public Stream<Element> stream() {
        return elements.stream();
    }

    @Override
    public Iterator<Element> iterator() {
        return elements.iterator();
    }

    public static class Builder implements org.finalframework.core.Builder<Association> {
        private final String property;
        private final List<Result> results = new ArrayList<>();
        private final List<Element> elements = new ArrayList<>();

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
            this.elements.add(result);
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
