package org.finalframework.coding.mapper.xml;

import org.apache.ibatis.mapping.FetchType;
import org.apache.ibatis.type.JdbcType;
import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.entity.Property;
import org.finalframework.coding.mapper.TypeHandlers;
import org.finalframework.core.Streamable;

import javax.lang.model.element.TypeElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-10 19:33:13
 * @since 1.0
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
public final class Association implements Element, Streamable<Element>, Iterable<Element> {

    private final String property;
    private final TypeElement javaType;
    private final String column;
    private final JdbcType jdbcType;
    private final String select;
    private final ResultMap resultMap;
    private final TypeElement typeHandler;
    private final String columnPrefix;
    private final String foreignColumn;
    private final Boolean autoMapping;
    private final FetchType fetchType;
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
        this.results = builder.results.isEmpty() ? Collections.emptyList() : Collections.unmodifiableList(builder.results);
        this.elements = builder.elements.isEmpty() ? Collections.emptyList() : Collections.unmodifiableList(builder.elements.stream().sorted().collect(Collectors.toList()));

    }

    public static Association from(Property referenceProperty, Entity entity, TypeHandlers typeHandlers) {
        Builder builder = new Builder(referenceProperty.getName());
        builder.javaType(entity.getElement());

        entity.stream()
                .filter(it -> !it.isVirtual() && !it.isWriteOnly())
                .forEach(property -> {
                    if (property.isReference()) {
//                        builder.addAssociation(Association.from(property,property.toEntity()));
                    } else {
                        builder.addResult(Result.from(referenceProperty, property, typeHandlers));
                    }
                });


        return builder.build();
    }


    @Override
    public final ElementType type() {
        return ElementType.ASSOCIATION;
    }

    public String getProperty() {
        return property;
    }

    public TypeElement getJavaType() {
        return javaType;
    }

    public String getColumn() {
        return column;
    }

    public JdbcType getJdbcType() {
        return jdbcType;
    }

    public String getSelect() {
        return select;
    }

    public ResultMap getResultMap() {
        return resultMap;
    }

    public TypeElement getTypeHandler() {
        return typeHandler;
    }

    public String getColumnPrefix() {
        return columnPrefix;
    }

    public String getForeignColumn() {
        return foreignColumn;
    }

    public Boolean getAutoMapping() {
        return autoMapping;
    }

    public FetchType getFetchType() {
        return fetchType;
    }

    public List<Result> getResults() {
        return results;
    }

    public List<Element> getElements() {
        return elements;
    }

    @Override
    public Iterator<Element> iterator() {
        return elements.iterator();
    }

    @Override
    public Stream<Element> stream() {
        return elements.stream();
    }

    private static class Builder implements org.finalframework.core.Builder<Association> {
        private final String property;
        private TypeElement javaType;
        private String column;
        private JdbcType jdbcType;
        private String select;
        private ResultMap resultMap;
        private TypeElement typeHandler;
        private String columnPrefix;
        private String foreignColumn;
        private Boolean autoMapping;
        private FetchType fetchType;
        private List<Result> results = new ArrayList<>();
        private List<Element> elements = new ArrayList<>();


        public Builder(String property) {
            this.property = property;
        }

        public Builder javaType(TypeElement javaType) {
            this.javaType = javaType;
            return this;
        }

        public Builder column(String column) {
            this.column = column;
            return this;
        }

        public Builder addResult(Result result) {
            this.results.add(result);
            this.elements.add(result);
            return this;
        }

        @Override
        public Association build() {
            return new Association(this);
        }


    }
}
